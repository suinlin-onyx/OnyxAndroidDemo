package com.onyx.brightnesssample.controller;

import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.onyx.android.sdk.api.device.FrontLightController;
import com.onyx.android.sdk.device.Device;
import com.onyx.brightnesssample.R;
import com.onyx.brightnesssample.utils.BrightnessUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CTMBrightnessController extends BrightnessController {

    private CheckBox mColdSwitch, mWarmSwitch;
    private ImageView mColdAddIcon, mWarmAddIcon, mColdMinusIcon, mWarmMinusIcon;
    private View mColdLayout, mWarmLayout;
    private SeekBar mColdControlSeekBar, mWarmControlSeekBar;
    private TextView mColdValueTextView, mWarmValueTextView;


    public CTMBrightnessController(Context context, CheckBox coldSwitch, CheckBox warmSwitch, View warmLayout, View coldLayout, SeekBar coldControl,
                                   SeekBar warmControl, ImageView coldAddIcon, ImageView coldMinusIcon,
                                   ImageView warmAddIcon, ImageView warmMinusIcon, TextView coldValueTextView, TextView warmValueTextView) {
        super(context);
        mContext = context;
        mColdSwitch = coldSwitch;
        mWarmSwitch = warmSwitch;
        mColdControlSeekBar = coldControl;
        mWarmControlSeekBar = warmControl;
        mColdLayout =  coldLayout;
        mWarmLayout = warmLayout;
        mColdAddIcon = coldAddIcon;
        mColdMinusIcon = coldMinusIcon;
        mWarmAddIcon = warmAddIcon;
        mWarmMinusIcon = warmMinusIcon;
        mColdValueTextView = coldValueTextView;
        mWarmValueTextView = warmValueTextView;
        initView();
    }

    @Override
    public void onClick(View v) {

    }

    private void initView() {
        if (!Device.currentDevice().hasCTMBrightness(mContext)) {
            return;
        }
        Integer[] colds = FrontLightController.getColdLightValues(mContext);
        Integer[] warms = FrontLightController.getWarmLightValues(mContext);
        int max = colds == null ? 0 : colds.length -1;
        int min = 0;

        mColdControlSeekBar.setMax(max);
        mWarmControlSeekBar.setMax(max);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mColdControlSeekBar.setMin(min);
            mWarmControlSeekBar.setMin(min);
        }

        udpateCtmValueTextView();
        mColdControlSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }
                FrontLightController.setColdLightDeviceValue(mContext, progress);
                BrightnessUtils.instance(mContext).setLightProgress(FrontLightController.LIGHT_TYPE_CTM_COLD, progress);
                udpateCtmValueTextView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mWarmControlSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }
                BrightnessUtils.instance(mContext).setLightProgress(FrontLightController.LIGHT_TYPE_CTM_WARM, progress);
                udpateCtmValueTextView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mColdAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseBrightness(FrontLightController.LIGHT_TYPE_CTM_COLD);
            }
        });
        mColdMinusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseBrightness(FrontLightController.LIGHT_TYPE_CTM_COLD);
            }
        });
        mWarmAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseBrightness(FrontLightController.LIGHT_TYPE_CTM_WARM);
            }
        });
        mWarmMinusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseBrightness(Device.currentDevice().LIGHT_TYPE_CTM_WARM);
            }
        });


        mColdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tureOffOrOn(Device.currentDevice().LIGHT_TYPE_CTM_COLD);
            }
        });

        mWarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tureOffOrOn(Device.currentDevice().LIGHT_TYPE_CTM_WARM);
            }
        });

        mWarmLayout.setOnKeyListener(this);
        mColdLayout.setOnKeyListener(this);
        mWarmControlSeekBar.setOnKeyListener(this);
        mColdControlSeekBar.setOnKeyListener(this);
        udpateStatue();
    }



    @Override
    public void udpateStatue() {
        updateCTMSeekBar();
        udpateCtmValueTextView();
        updateSwitch();
    }

    private void updateCTMSeekBar() {
        mColdControlSeekBar.setProgress(BrightnessUtils.instance(mContext).getLightProgress(Device.currentDevice().LIGHT_TYPE_CTM_COLD));
        mWarmControlSeekBar.setProgress(BrightnessUtils.instance(mContext).getLightProgress(Device.currentDevice().LIGHT_TYPE_CTM_WARM));
    }

    private void udpateCtmValueTextView() {
        int coldValue = Device.currentDevice().getColdLightConfigValue(mContext);
        int wareValue = Device.currentDevice().getWarmLightConfigValue(mContext);
        mColdValueTextView.setText(mContext.getResources().getString(R.string.cold_light, coldValue));
        mWarmValueTextView.setText(mContext.getResources().getString(R.string.warm_light, wareValue));
    }

    private void updateSwitch() {
        boolean coldValue = BrightnessUtils.instance(mContext).isLightOn(Device.currentDevice().LIGHT_TYPE_CTM_COLD);
        mColdSwitch.setChecked(coldValue);
        boolean warmValue = BrightnessUtils.instance(mContext).isLightOn(Device.currentDevice().LIGHT_TYPE_CTM_WARM);
        mWarmSwitch.setChecked(warmValue);


    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            return false;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (v.getId() == mWarmControlSeekBar.getId()) {
                    mWarmLayout.requestFocus();
                    return true;
                } else if (v.getId() == mColdControlSeekBar.getId()) {
                    mColdLayout.requestFocus();
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if (v.getId() == mWarmLayout.getId()) {
                    mWarmControlSeekBar.requestFocus();
                    mWarmControlSeekBar.setSelected(true);
                    return true;
                } else if (v.getId() == mColdLayout.getId()) {
                    mColdControlSeekBar.requestFocus();
                    mColdControlSeekBar.setSelected(true);
                    return true;
                } else if (v.getId() == mColdControlSeekBar.getId()) {
                    mColdLayout.requestFocus();
                } else if (v.getId() == mWarmControlSeekBar.getId()) {
                    mWarmLayout.requestFocus();
                }
                break;
        }
        return false;
    }
}
