package id.codigo.seedroid.helper;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import id.codigo.seedroid.ApplicationMain;

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
            Log.d(TAG, "imageUrl :" + imageUrl);

            Glide.with(ApplicationMain.getInstance())
                    .load(imageUrl)
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
