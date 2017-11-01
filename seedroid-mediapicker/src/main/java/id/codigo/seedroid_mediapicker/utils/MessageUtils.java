package id.codigo.seedroid_mediapicker.utils;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.codigo.seedroid_mediapicker.R;

/**
 * Created by papahnakal on 19/10/17.
 */

public class MessageUtils {
    /**
     * @param context
     * @param maxDuration in seconds.
     * @return message before record video.
     */
    public static String getWarningMessageVideoDuration(Context context,
                                                        int maxDuration) {
        return context.getResources().getQuantityString(
                R.plurals.picker_video_duration_warning, maxDuration, maxDuration);
    }

    /**
     * @param context
     * @param maxDuration
     * @return message when record and select video that has duration larger
     * than max options.
     * {@link MediaOption.Builder#setMaxVideoDuration(int)}
     */
    public static String getInvalidMessageMaxVideoDuration(Context context,
                                                           int maxDuration) {
        return context.getResources().getQuantityString(
                R.plurals.picker_video_duration_max, maxDuration, maxDuration);
    }

    /**
     * @param context
     * @param minDuration
     * @return message when record and select video that has duration smaller
     * than min options.
     * {@link MediaOption.Builder#setMinVideoDuration(int)}
     */
    public static String getInvalidMessageMinVideoDuration(Context context,
                                                           int minDuration) {
        return context.getResources().getQuantityString(
                R.plurals.picker_video_duration_min, minDuration, minDuration);
    }
}
