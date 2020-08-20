package com.onyx.brightnesssample.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.onyx.brightnesssample.R;
import com.onyx.brightnesssample.controller.CTMBrightnessController;

@SuppressLint("ValidFragment")
public class CTMBrightnessFragment extends Fragment {

    private View root;
    private SeekBar mCTMWarmLightSeekBar, mCTMColdLightSeekBar;
    private ImageButton mCTMWarnLightMinus, mCTMWarmLightAdd, mCTMColdLightMinus, mCTMColdLightAdd;
    private CheckBox mCTMWarnLightSwitch, mCTMColdLightSwitch;
    private TextView mCTMWarmLightValue, mCTMColdLightValue;
    private CTMBrightnessController ctmBrightnessController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ctm_brightness_fragment, container, false);
        initView();
        return root;
    }

    private void initView() {
        mCTMWarnLightSwitch = (CheckBox) root.findViewById(R.id.warm_light_switch);
        mCTMColdLightSwitch = (CheckBox) root.findViewById(R.id.cold_light_switch);

        mCTMWarmLightSeekBar = (SeekBar) root.findViewById(R.id.warm_brightness_slider);
        mCTMColdLightSeekBar = (SeekBar) root.findViewById(R.id.cold_brightness_slider);

        mCTMColdLightAdd = (ImageButton) root.findViewById(R.id.cold_brightnes_up);
        mCTMColdLightMinus = (ImageButton) root.findViewById(R.id.cold_brightnes_down);

        mCTMWarmLightAdd = (ImageButton) root.findViewById(R.id.warm_brightnes_up);
        mCTMWarnLightMinus = (ImageButton) root.findViewById(R.id.warm_brightnes_down);

        mCTMWarmLightValue = (TextView) root.findViewById(R.id.warm_light_value);
        mCTMColdLightValue = (TextView) root.findViewById(R.id.cold_light_value);

       ctmBrightnessController = new CTMBrightnessController(getContext(),
                mCTMColdLightSwitch, mCTMWarnLightSwitch,
               mCTMColdLightSeekBar, mCTMWarmLightSeekBar,
                mCTMColdLightAdd, mCTMColdLightMinus,
                mCTMWarmLightAdd, mCTMWarnLightMinus,
                mCTMColdLightValue, mCTMWarmLightValue);
    }
}
