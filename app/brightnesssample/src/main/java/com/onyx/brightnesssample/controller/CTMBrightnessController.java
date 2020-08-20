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
import com.onyx.android.sdk.device.Device;
import com.onyx.brightnesssample.R;
import com.onyx.brightnesssample.utils.BrightnessUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CTMBrightnessController extends BrightnessController {

    private CheckBox mColdSwitch, mWarmSwitch;
    private ImageView mColdAddIcon, mWarmAddIcon, mColdMinusIcon, mWarmMinusIcon;
    private SeekBar mColdControlSeekBar, mWarmControlSeekBar;
    private TextView mColdValueTextView, mWarmValueTextView;


    public CTMBrightnessController(Context context, CheckBox coldSwitch, CheckBox warmSwitch, SeekBar coldControl,
                                   SeekBar warmControl, ImageView coldAddIcon, ImageView coldMinusIcon,
                                   ImageView warmAddIcon, ImageView warmMinusIcon, TextView coldValueTextView, TextView warmValueTextView) {
        super(context);
        mContext = context;
        mColdSwitch = coldSwitch;
        mWarmSwitch = warmSwitch;
        mColdControlSeekBar = coldControl;
        mWarmControlSeekBar = warmControl;
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
        Integer[] colds = FrontLightController.getColdLightValues(mContext);
        Integer[] warms = FrontLightController.getWarmLightValues(mContext);
        int max = colds == null ? 0 : colds.length;
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
                decreaseBrightness(FrontLightController.LIGHT_TYPE_CTM_WARM);
            }
        });


        mColdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mWarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }



    @Override
    public void udpateStatue() {
        updateFLSeekBar();
        udpateCtmValueTextView();
        updateSwitch();
    }

    private void updateFLSeekBar() {
        mColdControlSeekBar.setProgress(BrightnessUtils.instance(mContext).getLightProgress(FrontLightController.LIGHT_TYPE_CTM_COLD));
        mWarmControlSeekBar.setProgress(BrightnessUtils.instance(mContext).getLightProgress(FrontLightController.LIGHT_TYPE_CTM_WARM));
    }

    private void udpateCtmValueTextView() {
        int coldValue = FrontLightController.getColdLightConfigValue(mContext);
        int wareValue = FrontLightController.getWarmLightConfigValue(mContext);
        mColdValueTextView.setText(mContext.getResources().getString(R.string.cold_light, coldValue));
        mWarmValueTextView.setText(mContext.getResources().getString(R.string.warm_light, wareValue));
    }

    private void updateSwitch() {
        boolean coldValue = FrontLightController.isLightOn(mContext);
        mColdSwitch.setChecked(coldValue);
        boolean warmValue = FrontLightController.isLightOn(mContext);
        mWarmSwitch.setChecked(warmValue);
    }


}
