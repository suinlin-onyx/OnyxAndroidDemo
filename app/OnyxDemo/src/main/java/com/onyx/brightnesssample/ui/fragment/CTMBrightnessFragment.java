package com.onyx.brightnesssample.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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
import com.onyx.brightnesssample.base.BaseSupportFragment;
import com.onyx.brightnesssample.controller.CTMBrightnessController;
import com.onyx.brightnesssample.databinding.CtmBrightnessFragmentBinding;
import com.onyx.brightnesssample.event.BackToRootFragmentEvent;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportFragment;

@SuppressLint("ValidFragment")
public class CTMBrightnessFragment extends BaseSupportFragment<CtmBrightnessFragmentBinding> {

    private View root;
    private SeekBar mCTMWarmLightSeekBar, mCTMColdLightSeekBar;
    private ImageButton mCTMWarnLightMinus, mCTMWarmLightAdd, mCTMColdLightMinus, mCTMColdLightAdd;
    private CheckBox mCTMWarnLightSwitch, mCTMColdLightSwitch;
    private TextView mCTMWarmLightValue, mCTMColdLightValue;
    private CTMBrightnessController ctmBrightnessController;

    @Override
    protected int getLayoutId() {
        return R.layout.ctm_brightness_fragment;
    }

    public CTMBrightnessFragment(Context context) {
        super(context);
    }

    public static CTMBrightnessFragment newInstance(@Nullable Bundle args, Context context) {
        CTMBrightnessFragment fragment = new CTMBrightnessFragment(context);
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
        mCTMWarnLightSwitch = binding.warmLightSwitch;
        mCTMColdLightSwitch = binding.coldLightSwitch;

        mCTMWarmLightSeekBar = binding.warmBrightnessSlider;
        mCTMColdLightSeekBar = binding.coldBrightnessSlider;

        mCTMColdLightAdd = binding.coldBrightnesUp;
        mCTMColdLightMinus = binding.coldBrightnesDown;

        mCTMWarmLightAdd = binding.warmBrightnesUp;
        mCTMWarnLightMinus = binding.warmBrightnesDown;

        mCTMWarmLightValue = binding.warmLightValue;
        mCTMColdLightValue = binding.coldLightValue;

       ctmBrightnessController = new CTMBrightnessController(getContext(),
                mCTMColdLightSwitch, mCTMWarnLightSwitch,
               binding.warmBrightnessSliderLayout, binding.coldBrightnessSliderLayout,
               mCTMColdLightSeekBar, mCTMWarmLightSeekBar,
                mCTMColdLightAdd, mCTMColdLightMinus,
                mCTMWarmLightAdd, mCTMWarnLightMinus,
                mCTMColdLightValue, mCTMWarmLightValue);
    }

}
