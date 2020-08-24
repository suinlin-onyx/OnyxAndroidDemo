package com.onyx.brightnesssample.event;

public class FunctionChangeEvent {
    public int currentType;
    public int targetType;

    public FunctionChangeEvent(int currentType, int targetType) {
        this.currentType = currentType;
        this.targetType = targetType;
    }
}
