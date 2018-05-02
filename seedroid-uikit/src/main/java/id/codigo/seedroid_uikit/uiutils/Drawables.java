package id.codigo.seedroid_uikit.uiutils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import id.codigo.seedroid_uikit.SEEDROIDUI;
import id.codigo.seedroid_uikit.sdkutils.Constant;
import id.codigo.seedroid_uikit.sdkutils.DensityUtil;
import id.codigo.seedroid_uikit.sdkutils.ResourceUtils;

/**
 * Created by papahnakal on 01/03/18.
 */

public class Drawables {
    private static Drawables instance = new Drawables();

    public static Drawables getInstance()
    {
        return instance;
    }

    /**
     * Decode the image to bitmap
     *
     * @param srcName
     * @return
     */
    private Bitmap decodeBitmap(String srcName)
    {
        Bitmap bitmap = null;
        String drawablePath = ResourceUtils.getResourcePath() + ResourceUtils.getImagePath() +
                File.separator + srcName + Constant.IMAGE_SUFFIX;
        File file = new File(drawablePath);
        if (file.exists())
        {
            bitmap = BitmapLruCacheUtils.getInstance().getBitmapFromMemCache(drawablePath);
            if (null == bitmap)
            {
                bitmap = BitmapFactory.decodeFile(drawablePath);
                BitmapLruCacheUtils.getInstance().addBitmapToMemCache(drawablePath, bitmap);
            }
        }
        return bitmap;
    }

    /**
     * Set image by state drawable
     *
     * @param view
     * @param normal
     * @param selected
     */
    private void setStateDrawable(View view, Drawable normal, Drawable selected)
    {
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{android.R.attr.state_selected}, selected);
        sd.addState(new int[]{android.R.attr.state_checked}, selected);
        sd.addState(new int[]{}, normal);

        if (view instanceof ImageView)
        {
            ((ImageView) view).setImageDrawable(sd);
        }
        else
        {
            // The version of SDK lower than android4.0 has no setBackground() method,
            // so use setBackgroundDrawable()
            view.setBackgroundDrawable(sd);
        }
    }

    private void setEnableDrawable(View view, Drawable enable, Drawable unable)
    {
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{android.R.attr.state_enabled}, enable);
        sd.addState(new int[]{}, unable);
        if (view instanceof ImageView)
        {
            ((ImageView) view).setImageDrawable(sd);
        }
        else
        {
            // The version of SDK lower than android4.0 has no setBackground() method,
            // so use setBackgroundDrawable()
            view.setBackgroundDrawable(sd);
        }
    }

    private void setPressedDrawable(View view,Drawable pressedDrawable,Drawable normalDrawable){
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        sd.addState(new int[]{}, normalDrawable);
        if (view instanceof ImageView)
        {
            ((ImageView) view).setImageDrawable(sd);
        }
        else
        {
            // The version of SDK lower than android4.0 has no setBackground() method,
            // so use setBackgroundDrawable()
            view.setBackgroundDrawable(sd);
        }
    }

    public void setPressedSelectorDrawable(View view, Resources resources, int normalResId, int
            selectedResId, boolean isColor){
        Drawable normalDrawable = null;
        Drawable selectedDrawable = null;
        if (!view.isInEditMode())
        {
            if (isColor)
            {
                normalDrawable = getDrawableColor(resources, normalResId);
                selectedDrawable = getDrawableColor(resources, selectedResId);
            }
            else
            {
                normalDrawable = getDrawable(resources, normalResId);
                selectedDrawable = getDrawable(resources, selectedResId);
            }
        }

        setPressedDrawable(view, normalDrawable, selectedDrawable);
    }



    /**
     * Set selector drawable
     *
     * @param view
     * @param resources
     * @param normalResId
     * @param selectedResId
     * @param isColor
     */
    public void setSelectorDrawable(View view, Resources resources, int normalResId, int
            selectedResId, boolean isColor)
    {
        Drawable normalDrawable = null;
        Drawable selectedDrawable = null;
        if (!view.isInEditMode())
        {
            if (isColor)
            {
                normalDrawable = getDrawableColor(resources, normalResId);
                selectedDrawable = getDrawableColor(resources, selectedResId);
            }
            else
            {
                normalDrawable = getDrawable(resources, normalResId);
                selectedDrawable = getDrawable(resources, selectedResId);
            }
        }
        setStateDrawable(view, normalDrawable, selectedDrawable);
    }



    public void setEnableDrawable(View view, Resources resources, int enableResId, int
            unableResId, boolean isColor)
    {
        Drawable enableDrawable = null;
        Drawable unableDrawable = null;

        if (isColor)
        {
            enableDrawable = getDrawableColor(resources, enableResId);
            unableDrawable = getDrawableColor(resources, unableResId);
        }
        else
        {
            enableDrawable = getDrawable(resources, enableResId);
            unableDrawable = getDrawable(resources, unableResId);
        }

        setEnableDrawable(view, enableDrawable, unableDrawable);
    }

    /**
     * Set gradient selector drawable
     *
     * @param view
     * @param normalResId
     * @param pressedResId
     * @param disabledResId
     * @param radiusResId
     */
    public void setBGGradientDrawableSelector(View view, int normalResId, int pressedResId, int
            disabledResId, int radiusResId)
    {
        Resources res = view.getResources();
        Drawable normalDrawable = createGradientDrawable(res, radiusResId, normalResId);
        Drawable pressedDrawable = createGradientDrawable(res, radiusResId, pressedResId);
        Drawable disabledDrawable = createGradientDrawable(res, radiusResId, disabledResId);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr
                .state_pressed}, pressedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        stateListDrawable.addState(new int[]{}, disabledDrawable);

        if (view instanceof ImageView)
        {
            ((ImageView) view).setImageDrawable(stateListDrawable);
        }
        else
        {
            // The version of SDK lower than android4.0 has no setBackground() method,
            // so use setBackgroundDrawable()
            view.setBackgroundDrawable(stateListDrawable);
        }
    }


    /**
     * Create color state list
     * @param res
     * @param normalResId
     * @param disabledResId
     */
    public ColorStateList createColorStateList(Resources res, int normalResId, int disabledResId)
    {
        int normalColor = Colors.getInstance().getColor(res, normalResId);
        int disabledColor = Colors.getInstance().getColor(res, disabledResId);
        int[] colors = new int[]{normalColor, disabledColor};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_enabled};
        states[1] = new int[]{};
        return new ColorStateList(states, colors);
    }

    /**
     * Get drawable by color
     *
     * @param resources
     * @param resId
     * @return
     */
    public Drawable getDrawableColor(Resources resources, int resId)
    {
        int color = Colors.getInstance().getColor(resources, resId);
        return new ColorDrawable(color);
    }

    /**
     * Get drawable
     *
     * @param resources
     * @param resId
     * @return
     */
    public Drawable getDrawable(Resources resources, int resId)
    {
        Drawable drawable = resources.getDrawable(resId);
        if (!SEEDROIDUI.isIsInEditMode() && ResourceUtils.isNeedLoadResource())
        {
            String resName = resources.getResourceEntryName(resId);
            // Set the background by image
            Bitmap dBitmap = decodeBitmap(resName);
            if (null != dBitmap)
            {
                drawable = new BitmapDrawable(resources, dBitmap);
            }
        }
        return drawable;
    }

    /**
     * Set drawable
     *
     * @param view
     * @param resourceId
     * @param isBackground
     */
    public void setDrawable(View view, int resourceId, boolean isBackground)
    {
        Drawable drawable = null;
        if (!isBackground && view instanceof ImageView)
        {
            drawable = ((ImageView) view).getDrawable();
        }
        else
        {
            drawable = view.getBackground();
        }

        if (null != drawable)
        {
            if (drawable instanceof ColorDrawable)
            {
                if (isBackground)
                {
                    view.setBackgroundDrawable(Drawables.getInstance().getDrawableColor(view
                            .getResources(), resourceId));
                }
                else
                {
                    ((ImageView) view).setImageDrawable(Drawables.getInstance().getDrawableColor
                            (view.getResources(), resourceId));
                }
            }
            else if (drawable instanceof BitmapDrawable)
            {
                if (isBackground)
                {
                    view.setBackgroundDrawable(Drawables.getInstance().getDrawable(view
                            .getResources(), resourceId));
                }
                else
                {
                    ((ImageView) view).setImageDrawable(Drawables.getInstance().getDrawable(view
                            .getResources(), resourceId));
                }
            }
        }
    }

    /**
     * Create gradient drawable
     *
     * @param radiusId The radius resource id of the rectangle shape
     * @param colorId
     * @return
     */
    public GradientDrawable createGradientDrawable(Resources res, int radiusId, int colorId)
    {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(res.getDimension(radiusId));
        gradientDrawable.setColor(Colors.getInstance().getColor(res, colorId));

        return gradientDrawable;
    }

    /**
     * @param context
     * @param radius  The radius in dips of the corners of the rectangle shape
     * @param colorId
     * @return
     */
    public GradientDrawable createGradientDrawable(Context context, float radius, int colorId)
    {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(DensityUtil.dip2px(context, radius));
        gradientDrawable.setColor(Colors.getInstance().getColor(context.getResources(), colorId));
        return gradientDrawable;
    }

    /**
     * Create gradient drawable
     *
     * @param context
     * @param radius        4 pairs of X and Y radius for each corner, specified in pixels.
     *                      The length of this array must be >= 8.
     *                      Specify radii for each of the 4 corners. For each corner, the array
     *                      contains 2 values, <code>[X_radius, Y_radius]</code>. The corners are
     *                      ordered
     *                      top-left, top-right, bottom-right, bottom-left. This property
     *                      is honored only when the shape is of type RECTANGLE
     * @param solidColorId
     * @param strokeColorId
     * @param strokeWidth   Width of stroke specified in dips
     * @return
     */
    public GradientDrawable createGradientDrawable(Context context, float[] radius, int
            solidColorId, int strokeColorId, float strokeWidth)
    {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(radius);
        gradientDrawable.setColor(Colors.getInstance().getColor(context.getResources(),
                solidColorId));
        if (-1 != strokeColorId && -1 != strokeWidth)
        {
            gradientDrawable.setStroke(DensityUtil.dip2px(context, strokeWidth), Colors
                    .getInstance().getColor(context.getResources(), strokeColorId));
        }
        return gradientDrawable;
    }

    public GradientDrawable createGradientDrawable(Context context, float radius, int
            solidColorId, int strokeColorId, float strokeWidth)
    {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setColor(Colors.getInstance().getColor(context.getResources(),
                solidColorId));
        if (-1 != strokeColorId && -1 != strokeWidth)
        {
            gradientDrawable.setStroke(DensityUtil.dip2px(context, strokeWidth), Colors
                    .getInstance().getColor(context.getResources(), strokeColorId));
        }
        return gradientDrawable;
    }

    public GradientDrawable createGradientDrawable(Context context, float[] radius, int
            solidColorId, int strokeColorId, float strokeWidth, float dashWidth, float dashGap)
    {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(radius);
        gradientDrawable.setColor(Colors.getInstance().getColor(context.getResources(),
                solidColorId));
        if (-1 != strokeColorId && -1 != strokeWidth)
        {
            gradientDrawable.setStroke(DensityUtil.dip2px(context, strokeWidth), Colors
                    .getInstance().getColor(context.getResources(), strokeColorId), DensityUtil
                    .dip2px(context, dashWidth), DensityUtil.dip2px(context, dashGap));
        }
        return gradientDrawable;
    }
}
