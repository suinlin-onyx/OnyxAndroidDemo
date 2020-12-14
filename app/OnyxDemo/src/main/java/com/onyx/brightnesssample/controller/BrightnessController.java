package com.onyx.brightnesssample.controller;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import com.onyx.android.sdk.api.device.FrontLightController;
import com.onyx.android.sdk.device.Device;
import com.onyx.brightnesssample.utils.BrightnessUtils;


public abstract class BrightnessController implements View.OnClickListener, View.OnKeyListener{

    public Context mContext;

    public abstract void udpateStatue();

    public BrightnessController(Context context) {
        mContext = context;
    }

    public void increaseBrightness(int type) {
        //We recommend using this function
        FrontLightController.increaseBrightness(mContext, type);

        //Of course, you can also do it yourself.
        //BrightnessUtils.instance(mContext).increaseBrightness(type);
        udpateStatue();
    }

    public void decreaseBrightness(int type) {
        //We recommend using this function
        FrontLightController.decreaseBrightness(mContext, type);

        //Of course, you can also do it yourself
        //BrightnessUtils.instance(mContext).decreaseBrightness(type);
        udpateStatue();
    }

    public void tureOffOrOn() {
        BrightnessUtils.instance(mContext).tureOffOrOn();
        udpateStatue();
    }

    public void tureOffOrOn(int type) {
        BrightnessUtils.instance(mContext).tureOffOrOn(type);
        udpateStatue();
    }

}
