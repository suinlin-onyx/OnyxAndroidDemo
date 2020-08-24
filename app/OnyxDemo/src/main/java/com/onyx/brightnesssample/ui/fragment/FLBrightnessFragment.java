package com.onyx.brightnesssample.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.onyx.brightnesssample.R;
import com.onyx.brightnesssample.base.BaseSupportFragment;
import com.onyx.brightnesssample.controller.FLBrightnessController;
import com.onyx.brightnesssample.databinding.FlBrightnessFragmentBinding;

import org.greenrobot.eventbus.EventBus;

@SuppressLint("ValidFragment")
public class FLBrightnessFragment extends BaseSupportFragment<FlBrightnessFragmentBinding> {

    private View root;
    private SeekBar mFLightRatingBar;
    private ImageButton mFLightMinus, mFightAdd;
    private CheckBox mFLightSwitch ;
    private TextView mFLightValue;
    private FLBrightnessController flBrightnessController;

    @Override
    protected int getLayoutId() {
        return R.layout.fl_brightness_fragment;
    }

    public FLBrightnessFragment(Context context) {
        super(context);
    }

    public static FLBrightnessFragment newInstance(@Nullable Bundle args, Context context) {
        FLBrightnessFragment fragment = new FLBrightnessFragment(context);
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
        mFLightSwitch = binding.flLightSwitch;
        mFLightRatingBar = binding.flBrightnessSlider;
        mFightAdd = binding.flBrightnesUp;
        mFLightMinus = binding.flBrightnesDown;
        mFLightValue = binding.flLightValue;
        flBrightnessController = new FLBrightnessController(getContext(),
                mFLightSwitch,
                binding.flBrightnessSliderLayout,
                mFLightRatingBar,
                mFightAdd,
                mFLightMinus,
                mFLightValue);
    }

}
