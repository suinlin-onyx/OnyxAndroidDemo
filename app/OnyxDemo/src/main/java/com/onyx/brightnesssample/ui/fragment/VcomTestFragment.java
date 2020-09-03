package com.onyx.brightnesssample.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.device.Device;
import com.onyx.brightnesssample.R;
import com.onyx.brightnesssample.base.BaseSupportFragment;
import com.onyx.brightnesssample.databinding.DpadTestFragmentBinding;
import com.onyx.brightnesssample.databinding.VcomTestFragmentBinding;
import com.onyx.brightnesssample.ui.dialog.OnyxAlertDialog;
import com.onyx.brightnesssample.utils.Constant;
import com.onyx.brightnesssample.utils.ThreadUtils;


@SuppressLint("ValidFragment")
public class VcomTestFragment extends BaseSupportFragment<VcomTestFragmentBinding> implements View.OnKeyListener {

    private static final String TAG = VcomTestFragment.class.getSimpleName();
    private View root;
    private String sVComFile;
    private int mVComResult = 0;
    private static Handler mHandler = new Handler();

    private EditText mVComEidText;
    private TextView mVComText;

    @Override
    protected int getLayoutId() {
        return R.layout.vcom_test_fragment;
    }

    public VcomTestFragment(Context context) {
        super(context);
    }

    public static VcomTestFragment newInstance(@Nullable Bundle args, Context context) {
        VcomTestFragment fragment = new VcomTestFragment(context);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initData() {
        sVComFile = Constant.getVComEndPoint();
        mVComResult = Device.currentDevice.getVCom(getContext(), sVComFile);


        Log.d(TAG, "sVComFile : " + sVComFile + " mVComResult: " + mVComResult);
    }

    private void initView() {
        mVComText = binding.devicesVcom;
        mVComEidText = binding.vcomText;

        mVComText.setText(Double.toString(mVComResult / 100) + " V");

        binding.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPromptDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateVCom();
                            }
                        }, 500);
                    }
                }).start();
            }
        });
    }

    private void updateVCom() {
        PowerManager.WakeLock wakeLock = null;
        wakeLock = Device.currentDevice.newWakeLock(getContext(), "VCOM");
        if (wakeLock != null) {
            wakeLock.acquire();
        }

        final String vcom = mVComEidText.getText().toString();

        if (vcom == null || vcom.equals("")) {
            toast(getResources().getString(R.string.enter_value));
        } else if (mVComResult == Integer.parseInt(vcom)) {
            toast(getResources().getString(R.string.select_different_value));
        } else {
            final int input = Integer.valueOf(vcom);
            int result = 0;
            ThreadUtils.mySleep(3000);
            for(int i = 0; i < 3; ++i) {
                Device.currentDevice.setVCom(getContext(), input, sVComFile);
                ThreadUtils.mySleep(2000);
                result = Device.currentDevice.getVCom(getContext(), sVComFile);
                if (result == input) {
                    break;
                }
            }
            if (input != result) {
                StringBuilder sb = new StringBuilder();
                sb.append(getResources().getString(R.string.update_vcom_failed));
                sb.append(": " + String.valueOf(input) + " : " + String.valueOf(result));
                toast(sb.toString());
            } else {
                mVComText.setText((Double.valueOf(vcom) / 100) + " V");
                mVComResult = Integer.parseInt(vcom);
                toast(getResources().getString(R.string.update_vcom_succeed));
                boolean isSave = Device.currentDevice.saveSystemConfig(getContext(), Constant.sSystemConfigFile, input + "");
                if (isSave) {
                    toast(getResources().getString(R.string.save_to_vendor_success));
                    Device.currentDevice().invalidate(getView(), UpdateMode.GC);
                } else {
                    toast(getResources().getString(R.string.save_to_vendor_faild));
                }
            }
        }
        if (wakeLock != null) {
            wakeLock.release();
        }
        dismmisPromptDialog();
    }

    private void toast(CharSequence hint) {
        Toast.makeText(getContext(), hint, Toast.LENGTH_LONG).show();
    }

    private void showPromptDialog() {
        OnyxAlertDialog.instance(getContext()).dismmis();
        AlertDialog.Builder builder = OnyxAlertDialog.instance(getContext()).builder;
        builder.setTitle("Set Vcom, please wait...");
        builder.setCancelable(false);
        OnyxAlertDialog.instance(getContext()).show();
    }

    private void dismmisPromptDialog() {
        OnyxAlertDialog.instance(getContext()).dismmis();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        android.util.Log.d("arvin", "onKey: keyCode: " + keyCode);
        if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (keyCode){
                case KeyEvent.KEYCODE_DPAD_UP:
                case KeyEvent.KEYCODE_DPAD_DOWN:
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    return true;
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    return true;
            }
        }
        return false;
    }


}
