package com.onyx.brightnesssample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;

import com.onyx.brightnesssample.base.BaseActivity;
import com.onyx.brightnesssample.base.BaseSupportFragment;
import com.onyx.brightnesssample.ui.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;


public class BrightnessActivity extends BaseActivity {


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brightness_activity);
        loadFragment();
    }


    private void loadFragment() {
        MainFragment mainFragment = new MainFragment(this);
        List<BaseSupportFragment> fragments = new ArrayList<>();
        fragments.add(mainFragment);
        loadRootFragment(R.id.content_layout, mainFragment);
    }

}


