package com.onyx.brightnesssample.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.onyx.brightnesssample.R;
import com.onyx.brightnesssample.base.BaseSupportFragment;
import com.onyx.brightnesssample.databinding.DpadTestFragmentBinding;
import com.onyx.brightnesssample.event.BackToRootFragmentEvent;

import org.greenrobot.eventbus.EventBus;


@SuppressLint("ValidFragment")
public class DpadTestFragment extends BaseSupportFragment<DpadTestFragmentBinding> implements View.OnKeyListener {

    private View root;

    @Override
    protected int getLayoutId() {
        return R.layout.dpad_test_fragment;
    }

    public DpadTestFragment(Context context) {
        super(context);
    }

    public static DpadTestFragment newInstance(@Nullable Bundle args, Context context) {
        DpadTestFragment fragment = new DpadTestFragment(context);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dpad center").append("\n")
                .append("android:focusable=\"false\"").append("\n")
                .append("\n")
                .append("Press Center to get the focus").append("\n")
                .append("Press Center to get the focus").append("\n")
                .append("\n");
        binding.textCenter.setText(stringBuilder.toString());
        binding.textCenter.setOnKeyListener(this);
        binding.textUp.setOnKeyListener(this);
        binding.textBottom.setOnKeyListener(this);
        binding.textLeft.setOnKeyListener(this);
        binding.textRight.setOnKeyListener(this);
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
                    if (binding.textCenter.isFocusable()) {
                        binding.textCenter.setFocusable(false);
                    }
                    return true;
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    if (v.getId() == binding.textCenter.getId()) {

                    }else if (!binding.textCenter.isFocusable()) {
                        binding.textCenter.setFocusable(true);
                        binding.textCenter.setFocusableInTouchMode(true);
                        binding.textCenter.requestFocus();
                    }
                    return true;
            }
        }
        return false;
    }


}
