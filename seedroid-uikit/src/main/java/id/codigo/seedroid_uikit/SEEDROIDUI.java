package id.codigo.seedroid_uikit;

import android.content.Context;
import android.util.Log;

/**
 * Created by papahnakal on 28/02/18.
 */

public class SEEDROIDUI {
    private static final String TAG = SEEDROIDUI.class.getSimpleName();
    private static Context applicationContext;
    private static long appBootTime = System.currentTimeMillis();

    public SEEDROIDUI() {
    }

    public static void init(Context context) {
        applicationContext = context.getApplicationContext();
    }

    public static Context getApplicationContext() {
        if(null == applicationContext) {
            Log.e(TAG, "The OTT SDK application context is null, you need to init OTTSDK by OTTSDK.init(context) before you use it");
        }

        return applicationContext;
    }

    public static void setApplicationContext(Context context) {
        applicationContext = context;
    }

    public static long getAppBootTime() {
        return appBootTime;
    }

    public static boolean isIsInEditMode() {
        return getApplicationContext() == null;
    }
}
