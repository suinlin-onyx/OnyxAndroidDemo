package com.onyx.brightnesssample.controller;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.onyx.android.sdk.api.device.FrontLightController;
import com.onyx.brightnesssample.R;

public  class FLBrightnessController extends BrightnessController {

    private CheckBox mFLSwitch;
    private ImageView mFLAddIcon, mFLMinusIcon;
    private SeekBar mFLSeekBar;
    private TextView mFLValueTextView;

    public FLBrightnessController(Context context, CheckBox flSwitch, SeekBar flControl,
                                  ImageView flAddIcon, ImageView flMinusIcon, TextView flTextView) {
        super(context);
        mContext = context;
        mFLSwitch = flSwitch;
        mFLSeekBar = flControl;
        mFLAddIcon = flAddIcon;
        mFLMinusIcon = flMinusIcon;
        mFLValueTextView = flTextView;
        initView();
    }

    private void initView() {
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
                udpateStatue();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        int max = FrontLightController.getMaxFrontLightValue(mContext);
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
        int value = FrontLightController.getBrightness(mContext);
        mFLSeekBar.setProgress(value);
    }

    private void udpateFLValueTextView() {
        int value = FrontLightController.getBrightness(mContext);
        mFLValueTextView.setText(mContext.getResources().getString(R.string.fl_light, value));
    }

    private void updateSwitch() {
        boolean value = FrontLightController.isLightOn(mContext);
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

}