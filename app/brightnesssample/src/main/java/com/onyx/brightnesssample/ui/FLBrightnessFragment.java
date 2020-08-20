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
import com.onyx.brightnesssample.controller.FLBrightnessController;

@SuppressLint("ValidFragment")
public class FLBrightnessFragment extends Fragment {

    private View root;
    private SeekBar mFLightRatingBar;
    private ImageButton mFLightMinus, mFightAdd;
    private CheckBox mFLightSwitch ;
    private TextView mFLightValue;
    private FLBrightnessController flBrightnessController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fl_brightness_fragment, container, false);
        initView();
        return root;
    }

    private void initView() {
        mFLightSwitch = (CheckBox) root.findViewById(R.id.fl_light_switch);
        mFLightRatingBar = (SeekBar) root.findViewById(R.id.fl_brightness_slider);
        mFightAdd = (ImageButton) root.findViewById(R.id.fl_brightnes_up);
        mFLightMinus = (ImageButton) root.findViewById(R.id.fl_brightnes_down);
        mFLightValue = (TextView) root.findViewById(R.id.fl_light_value);
        flBrightnessController = new FLBrightnessController(getContext(), mFLightSwitch,
                mFLightRatingBar, mFightAdd, mFLightMinus, mFLightValue);
    }

}
