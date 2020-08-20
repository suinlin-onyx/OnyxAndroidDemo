package com.onyx.brightnesssample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.onyx.android.sdk.api.device.FrontLightController;
import com.onyx.brightnesssample.ui.CTMBrightnessFragment;
import com.onyx.brightnesssample.ui.FLBrightnessFragment;


public class BrightnessActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brightness_activity);
        initView();
    }

    private void initView() {
        if (FrontLightController.hasCTMBrightness(this)) {
            loadFragment(new CTMBrightnessFragment());
        } else if(FrontLightController.hasFLBrightness(this)) {
            loadFragment(new FLBrightnessFragment());
        }

    }

    private void loadFragment(final Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_layout, fragment)
                .commit();
    }
}


