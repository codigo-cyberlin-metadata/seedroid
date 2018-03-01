package id.codigo.seedroid_uikit.sdkutils;

import android.util.DisplayMetrics;
import android.util.Log;

import id.codigo.seedroid_uikit.SEEDROIDUI;

/**
 * Created by papahnakal on 01/03/18.
 */

public class DeviceInfo {
    private static final String TAG = DeviceInfo.class.getSimpleName();
    private static boolean isPad = false;
    private static final String PAD = "Android Pad";
    private static final String PHONE = "Android Phone";

    public DeviceInfo() {
    }

    public static void setPad(boolean isPad) {
        String deviceType = isPad?"Android Pad":"Android Phone";
        Log.i(TAG, "Current device is " + deviceType);
        isPad = isPad;
    }

    public static boolean isPad() {
        return isPad;
    }

    public static String getDeviceInformation() {
        if(SEEDROIDUI.isIsInEditMode()) {
            return null;
        } else {
            new DisplayMetrics();
            DisplayMetrics metric = SEEDROIDUI.getApplicationContext().getResources().getDisplayMetrics();
            StringBuffer info = new StringBuffer();
            info.append("The information of device: heightPixels: " + metric.heightPixels);
            info.append(", widthPixels: " + metric.widthPixels);
            info.append(", density: " + metric.density);
            info.append(", densityDpi: " + metric.densityDpi);
            info.append(", scaledDensity: " + metric.scaledDensity);
            info.append(", xdpi: " + metric.xdpi);
            info.append(", ydpi: " + metric.ydpi);
            info.append(", is Pad: " + isPad());
            Log.i(TAG, "[DeviceInfo] : "+ info.toString());
            return info.toString();
        }
    }

    public static int getWidthPixels() {
        new DisplayMetrics();
        DisplayMetrics metric = SEEDROIDUI.getApplicationContext().getResources().getDisplayMetrics();
        return metric.widthPixels;
    }

    public static int getHeightPixels() {
        new DisplayMetrics();
        DisplayMetrics metric = SEEDROIDUI.getApplicationContext().getResources().getDisplayMetrics();
        return metric.heightPixels;
    }

    public static String getDeviceType() {
        return isPad?"Android Pad":"Android Phone";
    }

    static {
        getDeviceInformation();
    }
}
