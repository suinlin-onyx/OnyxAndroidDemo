package com.onyx.brightnesssample.utils;

import android.content.Context;

import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.device.IMX6Device;
import com.onyx.android.sdk.device.RK3026Device;
import com.onyx.android.sdk.device.RK31XXDevice;
import com.onyx.android.sdk.device.RK32XXDevice;

/**
 * Created by solskjaer49 on 15/4/23 14:47.
 */
public class Constant {
    static public final int KEYCODE_BT_PAIR = 225;

    static public final String RTC_LIBRARY = "onyx_aging_rtc_test";
    static public final String MEMORY_LIBRARY = "onyx_memory_test";
    static public final String KEY_MAP_MODE = "key_map_mode";
    static public final String AUTO_POWER_OFF_TIMEOUT = "auto_poweroff_timeout";
    static public final String TOUCH_TYPE_CAPACITIVE = "Capacitive";
    static public final String TOUCH_TYPE_IR = "IR";

    static public final String ONYX_WIFI_TEST_GZ_SSID = "Xiaomi_5DE2";
    static public final String ONYX_WIFI_TEST_GZ_PW = "onyx456.";

    static public final String ONYX_WIFI_TEST_SZ_SSID = "zeng";
    static public final String ONYX_WIFI_TEST_SZ_PW = "OnyxWpa2015";

    static public final String ONYX_WIFI_TEST_XIAOMI = "Xiaomi_D7CA";
    static public final String ONYX_WIFI_TEST_XIAOMI_PW = "onyx123.";

    static public final String ONYX_WIFI_TEST_XIAOMI_5G = "Xiaomi_D7CA_5G";
    static public final String ONYX_WIFI_TEST_XIAOMI_5G_PW = "onyx123.";

    static public final String ONYX_WIFI_TEST_FACTORY_SSID_1 = "ONYX_WIFI_TEST_SSID_1";
    static public final String ONYX_WIFI_TEST_FACTORY_PW_1 = "OnyxWpa2015A";
    static public final String ONYX_WIFI_TEST_FACTORY_SSID_2 = "ONYX_WIFI_TEST_SSID_2";
    static public final String ONYX_WIFI_TEST_FACTORY_PW_2 = "OnyxWpa2015B";
    static public final String ONYX_WIFI_TEST_FACTORY_SSID_3 = "ONYX_WIFI_TEST_SSID_3";
    static public final String ONYX_WIFI_TEST_FACTORY_PW_3 = "OnyxWpa2015C";
    static public final String RK_KEY_MAP_MODE_FILE = "/sys/devices/platform/rk29-keypad/key_switching";
    static public final String IMX6_KEY_MAP_MODE_FILE = "/sys/devices/platform/gpio-keys/key_switching";
    static public final String VCOM_ENDPOINT_RK = "/sys/devices/platform/onyx_misc.0/vcom_value";
    static public final String VCOM_ENDPOINT_FSL = "/sys/class/hwmon/hwmon0/device/vcom_value";
    static public final String VCOM_ENDPOINT_RK32XX = "/sys/class/hwmon/hwmon1/device/vcom_value";
    static public final String VCOM_ENDPOINT_RK31XX = "/sys/class/hwmon/hwmon0/device/vcom_value";

    public static final String sWaveformFile = " /vendor/firmware/imx/epdc.fw";
    public static final String sSystemConfigFile = "com.android.vcom";

    static public String getKeyMapModeFile(Context context) {
        if (Device.currentDevice() instanceof IMX6Device) {
            return IMX6_KEY_MAP_MODE_FILE;
        } else if (Device.currentDevice() instanceof RK3026Device) {
            return RK_KEY_MAP_MODE_FILE;
        } else {
            return "";
        }
    }

    static public String getVComEndPoint() {
        if (Device.currentDevice() instanceof RK3026Device) {
            return Constant.VCOM_ENDPOINT_RK;
        } else if (Device.currentDevice() instanceof IMX6Device) {
            return Constant.VCOM_ENDPOINT_FSL;
        } else if (Device.currentDevice() instanceof RK32XXDevice) {
            return VCOM_ENDPOINT_RK32XX;
        } else if (Device.currentDevice() instanceof RK31XXDevice) {
            return VCOM_ENDPOINT_RK31XX;
        }
        return null;
    }
}
