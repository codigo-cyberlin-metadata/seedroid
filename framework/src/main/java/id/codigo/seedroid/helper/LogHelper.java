package id.codigo.seedroid.helper;

import android.util.Log;

import id.codigo.seedroid.configs.AppConfigs;

/**
 * Created by Gayo on 5/23/2017.
 */

public class LogHelper {
    public static void d(String tag, String message) {
        if (AppConfigs.isDebugMode) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (AppConfigs.isDebugMode) {
            Log.i(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (AppConfigs.isDebugMode) {
            Log.e(tag, message);
        }
    }
}
