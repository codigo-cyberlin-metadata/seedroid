package id.codigo.seedroid_volley.helper;

import android.util.Log;

import id.codigo.seedroid_volley.configs.AppConfigs;

/**
 * Created by Low on 11/1/17.
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
