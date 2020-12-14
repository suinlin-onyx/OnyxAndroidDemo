package com.onyx.brightnesssample.utils;

import android.view.View;

import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.device.Device;

public class RefreshUtils {

    public static boolean supportRegal() {
        return EpdController.supportRegal();
    }

    public static void invalidate(View view, UpdateMode updateMode) {
        if (updateMode == UpdateMode.REGAL) {
            EpdController.enableRegal();
        }
        switch (Device.currentDeviceIndex()){
            case SDM:
                EpdController.setViewDefaultUpdateMode(view, updateMode);
                view.invalidate();
                break;
            case imx6:
            case Rk31xx:
            case Rk32xx:
            case Rk3026:
            default:
                EpdController.invalidate(view, updateMode);
                break;
        }
    }
}
