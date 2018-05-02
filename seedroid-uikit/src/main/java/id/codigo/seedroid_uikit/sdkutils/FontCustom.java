package id.codigo.seedroid_uikit.sdkutils;

import android.content.Context;
import android.graphics.Typeface;

import id.codigo.seedroid_uikit.SEEDROIDUI;

/**
 * Created by papahnakal on 28/02/18.
 */

public class FontCustom {
    private static String fontlightUrl = "fonts/SFUIDisplay-Light.otf";
    private static String fontSemiboldUrl = "fonts/SFUIDisplay-Medium.otf";
    private static String fontboldUrl = "fonts/SFUIDisplay-Semibold.otf";

    private static Typeface lightTypeFace = Typeface.createFromAsset(SEEDROIDUI.getApplicationContext
            ().getAssets(), fontlightUrl);
    ;
    private static Typeface semiBoldTypeFace = Typeface.createFromAsset(SEEDROIDUI
            .getApplicationContext().getAssets(), fontSemiboldUrl);
    ;

    private static Typeface boldTypeFace = Typeface.createFromAsset(SEEDROIDUI.getApplicationContext
            ().getAssets(), fontboldUrl);
    ;
    private static final Object setSmallFontLock = new Object();
    private static final Object setSemiboldFontLock = new Object();
    private static final Object setBoldFontLock = new Object();


    /***
     * setting fonts
     *
     * @return
     */
    public static Typeface setSmallFont(Context context)
    {
        synchronized (setSmallFontLock)
        {
            if (lightTypeFace == null)
            {
                lightTypeFace = Typeface.createFromAsset(context.getAssets(), fontlightUrl);
            }
            return lightTypeFace;
        }
    }

    public static Typeface setSemiBoldFont(Context context)
    {
        synchronized (setSemiboldFontLock)
        {
            if (semiBoldTypeFace == null)
            {
                semiBoldTypeFace = Typeface.createFromAsset(context.getAssets(), fontSemiboldUrl);
            }
            return semiBoldTypeFace;
        }
    }

    public static Typeface setBoldFont(Context context)
    {
        synchronized (setBoldFontLock)
        {
            if (boldTypeFace == null)
            {
                boldTypeFace = Typeface.createFromAsset(context.getAssets(), fontboldUrl);
            }
            return boldTypeFace;
        }
    }
}
