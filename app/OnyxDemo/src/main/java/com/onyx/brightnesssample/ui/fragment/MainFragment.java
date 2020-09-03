package com.onyx.brightnesssample.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.onyx.brightnesssample.R;
import com.onyx.brightnesssample.base.BaseSupportFragment;
import com.onyx.brightnesssample.databinding.MainFragmentBinding;
import com.onyx.brightnesssample.event.BackToRootFragmentEvent;
import com.onyx.brightnesssample.event.DialogTestEvent;
import com.onyx.brightnesssample.event.FunctionChangeEvent;
import com.onyx.brightnesssample.ui.view.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;


@SuppressLint("ValidFragment")
public class MainFragment extends BaseSupportFragment<MainFragmentBinding>{

    private View root;
    private Map<Integer, BaseSupportFragment> fragments = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment;
    }

    public MainFragment(Context context) {
        super(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initFragment();
    }


    private void initFragment() {
        FunctionContainerFragment firstFragment = findChildFragment(FunctionContainerFragment.class);
        if (firstFragment == null) {
            fragments.put(Constants.FUNCTTION_CONTAINER, FunctionContainerFragment.newInstance(null, getContext()));
            fragments.put(Constants.FL_BRIGHTNESS, FLBrightnessFragment.newInstance(null, getContext()));
            fragments.put(Constants.CTM_BRIGHTNESS, CTMBrightnessFragment.newInstance(null, getContext()));
            fragments.put(Constants.DPAD_TEST, DpadTestFragment.newInstance(null, getContext()));
            fragments.put(Constants.VCOM_TEST, VcomTestFragment.newInstance(null, getContext()));
            loadMultipleRootFragment(R.id.content_layout, 0,
                    fragments.get(Constants.FUNCTTION_CONTAINER),
                    fragments.get(Constants.FL_BRIGHTNESS),
                    fragments.get(Constants.CTM_BRIGHTNESS),
                    fragments.get(Constants.DPAD_TEST),
                    fragments.get(Constants.VCOM_TEST)
                    );
        } else {
            fragments.put(Constants.FUNCTTION_CONTAINER, firstFragment);
            fragments.put(Constants.FL_BRIGHTNESS, findChildFragment(FLBrightnessFragment.class));
            fragments.put(Constants.CTM_BRIGHTNESS, findChildFragment(CTMBrightnessFragment.class));
            fragments.put(Constants.DPAD_TEST, findChildFragment(DpadTestFragment.class));
            fragments.put(Constants.VCOM_TEST, findChildFragment(VcomTestFragment.class));
            showHideFragment(findChildFragment(FunctionContainerFragment.class));
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        android.util.Log.d("arvin", "onBackPressedSupport");
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFunctionChangeEvent(FunctionChangeEvent event) {
        showHideFragment(fragments.get(event.targetType), findChildFragment(FunctionContainerFragment.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackToRootFragmentEvent(BackToRootFragmentEvent event) {
        android.util.Log.d("arvin", "onBackToRootFragmentEvent");
        showHideFragment(findChildFragment(FunctionContainerFragment.class));
    }


}
