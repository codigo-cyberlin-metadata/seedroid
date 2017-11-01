package id.codigo.seedroid_mediapicker.imageloader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import id.codigo.seedroid_mediapicker.R;

/**
 * Created by papahnakal on 19/10/17.
 */

public class MediaImageLoaderIml implements MediaImageLoader {

    public MediaImageLoaderIml(Context context) {
        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheSizePercentage(30)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .writeDebugLogs().threadPoolSize(3)
                .build();

        ImageLoader.getInstance().init(imageLoaderConfig);
    }

    @Override
    public void displayImage(Uri uri, ImageView imageView) {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .showImageOnLoading(R.color.picker_imageloading)
                .cacheOnDisk(false)
                .considerExifParams(true).resetViewBeforeLoading(true).build();

        ImageAware imageAware = new ImageViewAware(imageView, false);
        ImageLoader.getInstance().displayImage(uri.toString(), imageAware, displayImageOptions);
    }
}
