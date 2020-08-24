package com.onyx.brightnesssample.utils;

import android.content.Context;

import com.onyx.android.sdk.api.device.FrontLightController;
import com.onyx.android.sdk.device.Device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrightnessUtils {

    private Context mContext;
    private static BrightnessUtils brightnessUtils;
    private List<Integer> fLightSteps = new ArrayList<Integer>();
    private List<Integer> warmLightSteps = new ArrayList<Integer>();
    private List<Integer> coldLightSteps = new ArrayList<Integer>();

    public BrightnessUtils(Context context) {
        mContext = context;
    }

    public static BrightnessUtils instance(Context context) {
        return brightnessUtils == null ? brightnessUtils = new BrightnessUtils(context) : brightnessUtils;
    }

    public boolean isLightOn(int type) {
        return Device.currentDevice().isLightOn(mContext, type);
    }

    public void tureOffOrOn() {
        if (FrontLightController.isLightOn(mContext)) {
            FrontLightController.turnOff(mContext);
        } else {
            FrontLightController.turnOn(mContext);
        }
    }

    public void tureOffOrOn(int type) {
        if(Device.currentDevice.isLightOn(mContext,type)) {
            boolean value = Device.currentDevice.closeFrontLight(type);
        } else {
            Device.currentDevice.openFrontLight(type);
        }
    }

    public void increaseBrightness(final int lightType) {
        //FrontLightController.increaseBrightness(mContext, type);
        stepAdjustBrightness(lightType, true);
    }

    public void decreaseBrightness(final int lightType) {
        //sFrontLightController.decreaseBrightness(mContext, type);
        stepAdjustBrightness(lightType, false);
    }

    public void setLightProgress(final int lightType, final int progress) {
        setLightValue(lightType, getLightValue(lightType, progress));
    }

    public Integer getLightProgress(final int lightType) {
        int lightValue = getBrightnessCurrentValue(lightType);
        List<Integer> lightStepsList = getBrightnessSteps(lightType);
        if (isEmptyList(lightStepsList)) {
            return 0;
        }
        Integer[] lightSteps = lightStepsList.toArray(new Integer[lightStepsList.size()]);
        return getMostRelevantBrightnessSteps(lightValue, lightSteps);
    }

    private int getLightValue(final int lightType, final int progress) {
        int value = progress;
        List<Integer> steps = getBrightnessSteps(lightType);
        if (isEmptyList(steps)) {
            value = 0;
        }

        if (progress >= steps.size()) {
            value = steps.size() - 1;
        }
        return steps.get(value);
    }

    public void setLightValue(final int lightType, final int value) {
        int type = lightType;
        if (FrontLightController.hasFLBrightness(mContext)) {
            type = FrontLightController.LIGHT_TYPE_FL;
        }
        switch (type) {
            case FrontLightController.LIGHT_TYPE_FL:
                Device.currentDevice().setFrontLightDeviceValue(mContext, value);
                break;
            case FrontLightController.LIGHT_TYPE_CTM_WARM:
                FrontLightController.setWarmLightDeviceValue(mContext, value);
                break;
            case FrontLightController.LIGHT_TYPE_CTM_COLD:
                FrontLightController.setColdLightDeviceValue(mContext, value);
                break;
        }
    }

    private void stepAdjustBrightness(final int lightType, final boolean increase) {
        adjustBrightness(lightType, getBrightnessCurrentValue(lightType), getBrightnessSteps(lightType), increase);
    }

    private void adjustBrightness(final int lightType, final int currentValue, final List<Integer> values,
                                  final boolean increase) {
        Integer[] lightSteps = values.toArray(new Integer[values.size()]);
        int result =  getMostRelevantBrightnessSteps(currentValue, lightSteps);
        result = increase ? ++result : --result;
        if(result >= 0 && result < lightSteps.length) {
            int currentBrightness = lightSteps[result];
            setLightValue(lightType, currentBrightness);
        }
    }

    private int getMostRelevantBrightnessSteps(final int currentValue, final  Integer[] lightSteps) {
        int min = Math.abs(currentValue - lightSteps[0]);
        int result = 0;
        for (int i = 0; i < lightSteps.length; i++) {
            int diff =  Math.abs(currentValue - lightSteps[i]);
            if (min >= diff) {
                min = diff;
                result = i;
            }
        }
        return result;
    }

    private List<Integer> getBrightnessSteps(final int lightType) {
        int type = lightType;
        if (FrontLightController.hasFLBrightness(mContext)) {
            type = FrontLightController.LIGHT_TYPE_FL;
        }
        switch (type) {
            case FrontLightController.LIGHT_TYPE_FL:
                return isEmptyList(fLightSteps) ? fLightSteps = FrontLightController.getFrontLightValueList(mContext) : fLightSteps;

            case FrontLightController.LIGHT_TYPE_CTM_WARM:
                return isEmptyList(warmLightSteps) ? warmLightSteps = Arrays.asList(FrontLightController.getWarmLightValues(mContext)) : warmLightSteps;

            case FrontLightController.LIGHT_TYPE_CTM_COLD:
                return isEmptyList(coldLightSteps) ? coldLightSteps = Arrays.asList(FrontLightController.getColdLightValues(mContext)) : coldLightSteps;

        }
        return null;
    }

    private Integer getBrightnessCurrentValue(final int lightType) {
        int type = lightType;
        if (FrontLightController.hasFLBrightness(mContext)) {
            type = FrontLightController.LIGHT_TYPE_FL;
        }
        switch (type) {
            case FrontLightController.LIGHT_TYPE_FL:
                return Device.currentDevice().getFrontLightDeviceValue(mContext);

            case FrontLightController.LIGHT_TYPE_CTM_WARM:
                return FrontLightController.getWarmLightConfigValue(mContext);

            case FrontLightController.LIGHT_TYPE_CTM_COLD:
                return FrontLightController.getColdLightConfigValue(mContext);

        }
        return null;
    }

    private boolean isEmptyList(List<Integer> coldLightSteps) {
        return coldLightSteps == null || coldLightSteps.size()== 0;
    }

}
