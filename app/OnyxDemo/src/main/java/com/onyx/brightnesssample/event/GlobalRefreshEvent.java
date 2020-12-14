package com.onyx.brightnesssample.event;

import com.onyx.android.sdk.api.device.epd.UpdateMode;

public class GlobalRefreshEvent {
    public UpdateMode updateMode;
    public GlobalRefreshEvent(UpdateMode updateMode) {
        this.updateMode = updateMode;
    }
}
