package com.onyx.brightnesssample.controller;

import android.content.Context;
import android.view.View;

import com.onyx.android.sdk.api.device.FrontLightController;
import com.onyx.brightnesssample.utils.BrightnessUtils;


public abstract class BrightnessController implements View.OnClickListener{

    public Context mContext;

    public BrightnessController(Context context) {
        mContext = context;
    }

    public void increaseBrightness(int type) {
        //FrontLightController.increaseBrightness(mContext, type);
        BrightnessUtils.instance(mContext).increaseBrightness(type);
        udpateStatue();
    }

    public void decreaseBrightness(int type) {
        //FrontLightController.decreaseBrightness(mContext, type);
        BrightnessUtils.instance(mContext).decreaseBrightness(type);
        udpateStatue();
    }

    public void tureOffOrOn(int type) {
        if (FrontLightController.isLightOn(mContext)) {
            FrontLightController.turnOff(mContext);
        } else {
            FrontLightController.turnOn(mContext);
        }
        udpateStatue();
    }

    public abstract void udpateStatue();
}
