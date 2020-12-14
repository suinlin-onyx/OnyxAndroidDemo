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

import java.util.List;

public  class FLBrightnessController extends BrightnessController {

    private CheckBox mFLSwitch;
    private ImageView mFLAddIcon, mFLMinusIcon;
    private View mFlSeekBarLayout;
    private SeekBar mFLSeekBar;
    private TextView mFLValueTextView;

    public FLBrightnessController(Context context, CheckBox flSwitch, View layout,SeekBar flControl,
                                  ImageView flAddIcon, ImageView flMinusIcon, TextView flTextView) {
        super(context);
        mContext = context;
        mFLSwitch = flSwitch;
        mFlSeekBarLayout = layout;
        mFLSeekBar = flControl;
        mFLAddIcon = flAddIcon;
        mFLMinusIcon = flMinusIcon;
        mFLValueTextView = flTextView;
        initView();
    }

    private void initView() {
        if (!Device.currentDevice().hasFLBrightness(mContext)) {
            return;
        }
        mFlSeekBarLayout.setOnKeyListener(this);
        mFLSeekBar.setOnKeyListener(this);

        mFLAddIcon.setOnClickListener(this);
        mFLMinusIcon.setOnClickListener(this);
        mFLSwitch.setOnClickListener(this);
        mFLSeekBar.setOnClickListener(this);
        mFLSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }
                FrontLightController.setBrightness(mContext, progress);
                BrightnessUtils.instance(mContext).setLightProgress(FrontLightController.LIGHT_TYPE_FL, progress);
                udpateStatue();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Integer[] lights = Device.currentDevice.getFLBrightnessValues(mContext);
        if (lights == null) {
            android.util.Log.d("arvin", "FLBrightnessController, lights == null");
        }
        int max = (lights == null || lights.length == 0) ? 0 : lights.length - 1;
        int min = FrontLightController.getMinFrontLightValue(mContext);
        mFLSeekBar.setMax(max < 0 ? 0 : max);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mFLSeekBar.setMin(min < 0 ? 0 : min);
        }
        udpateStatue();
    }

    @Override
    public void udpateStatue() {
        updateFLSeekBar();
        udpateFLValueTextView();
        updateSwitch();
    }

    private void updateFLSeekBar() {
        mFLSeekBar.setProgress(BrightnessUtils.instance(mContext).getLightProgress(FrontLightController.LIGHT_TYPE_FL));
    }

    private void udpateFLValueTextView() {
        int value = FrontLightController.getBrightness(mContext);
        mFLValueTextView.setText(mContext.getResources().getString(R.string.fl_light, value));
    }

    private void updateSwitch() {
        boolean value = BrightnessUtils.instance(mContext).isLightOn(FrontLightController.LIGHT_TYPE_FL);
        mFLSwitch.setChecked(value);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id  == mFLAddIcon.getId()) {
            increaseBrightness(FrontLightController.LIGHT_TYPE_FL);
        } else if (id  == mFLMinusIcon.getId()) {
            decreaseBrightness(FrontLightController.LIGHT_TYPE_FL);
        } else if (v.getId() == mFLSwitch.getId()) {
            tureOffOrOn(FrontLightController.LIGHT_TYPE_FL);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            return true;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (v.getId() == mFLSeekBar.getId()) {
                    mFlSeekBarLayout.requestFocus();
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if (v.getId() == mFlSeekBarLayout.getId()) {
                    mFLSeekBar.requestFocus();
                    mFLSeekBar.setSelected(true);
                    return true;
                } else if (v.getId() == mFLSeekBar.getId()) {
                    mFlSeekBarLayout.requestFocus();
                }
                break;
        }

        return false;
    }

}