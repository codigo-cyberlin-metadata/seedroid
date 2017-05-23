package id.codigo.seedroid.helper;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import id.codigo.seedroid.SeedroidApplication;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Lukma on 3/29/2016.
 */
public final class ImageHelper {
    private static final String TAG = ImageHelper.class.getSimpleName();

    /**
     * Load image after request to URL
     *
     * @param view     View to placed result
     * @param imageUrl URL of the image to load
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        try {
            LogHelper.d(TAG, "imageUrl :" + imageUrl);

            Glide.with(SeedroidApplication.getInstance())
                    .load(imageUrl)
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load image with circle transformation after request to URL
     *
     * @param view     View to placed result
     * @param imageUrl URL of the image to load
     */
    @BindingAdapter({"circleImageUrl"})
    public static void loadImageCircle(ImageView view, String imageUrl) {
        try {
            LogHelper.d(TAG, "imageUrl :" + imageUrl);

            Context context = SeedroidApplication.getInstance();

            Glide.with(context)
                    .load(imageUrl)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load image with rounded transformation after request to URL
     *
     * @param view     View to placed result
     * @param imageUrl URL of the image to load
     * @param radius   Number of radius view
     * @param margin   Number of margin view
     */
    @BindingAdapter({"roundedImageUrl", "roundedRadius", "roundedMargin"})
    public static void loadImageRounded(ImageView view, String imageUrl, int radius, int margin) {
        try {
            LogHelper.d(TAG, "imageUrl :" + imageUrl);

            Context context = SeedroidApplication.getInstance();

            Glide.with(context)
                    .load(imageUrl)
                    .bitmapTransform(new RoundedCornersTransformation(context, radius, margin))
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
