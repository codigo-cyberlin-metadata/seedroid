package id.codigo.seedroid_uikit.sdkutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by papahnakal on 01/03/18.
 */

public final class DensityUtil {
    private static final String TAG = DensityUtil.class.getSimpleName();

    private DensityUtil() {
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        int value = Math.round(dpValue * scale);
        if(value == 0) {
            value = 1;
        }

        return value;
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        int value = Math.round(pxValue / scale);
        if(value == 0) {
            value = 1;
        }

        return value;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenDensityDPI(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static float getScreenScaledDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getStatusHeight(Context context) {
        //int statusHeight = false;
        Rect localRect = new Rect();
        ((Activity)context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        int statusHeight = localRect.top;
        if(0 == statusHeight) {
            try {
                Class<?> localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException var6) {
                Log.e(TAG, var6.toString());
            } catch (InstantiationException var7) {
                Log.e(TAG, var7.toString());
            } catch (IllegalAccessException var8) {
                Log.e(TAG, var8.toString());
            } catch (NoSuchFieldException var9) {
                Log.e(TAG, var9.toString());
            }
        }

        return statusHeight;
    }
}
