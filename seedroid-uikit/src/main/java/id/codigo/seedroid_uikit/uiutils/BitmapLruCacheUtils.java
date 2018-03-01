package id.codigo.seedroid_uikit.uiutils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by papahnakal on 01/03/18.
 */

public class BitmapLruCacheUtils {
    private static String TAG = BitmapLruCacheUtils.class.getSimpleName();

    private static int MAX_MEMORY = (int) (Runtime.getRuntime().maxMemory() / 1024 / 8);

    private LruCache<String, Bitmap> memoryCache = null;

    private static BitmapLruCacheUtils instance = new BitmapLruCacheUtils();

    private Object lock = new Object();

    public static BitmapLruCacheUtils getInstance()
    {
        return instance;
    }

    private BitmapLruCacheUtils()
    {
        memoryCache = new LruCache<String, Bitmap>(MAX_MEMORY)
        {
            @Override
            protected int sizeOf(String key, Bitmap bitmap)
            {
                // Override this method to measure the size of each image.
                // Default return is the number of pictures.
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap
                    newValue)
            {
                Log.i(TAG, "Hard cache is full, push to soft cache");
            }
        };
    }

    public void clearBitmapMemCache()
    {
        if (null != memoryCache)
        {
            if (memoryCache.size() > 0)
            {
                Log.d(TAG, "memoryCache.size()=" + memoryCache.size());
                memoryCache.evictAll();
                Log.d(TAG, "After evictAll, memoryCache.size()=" + memoryCache.size());
            }
        }
    }

    public void addBitmapToMemCache(String name, Bitmap bitmap)
    {
        synchronized (lock)
        {
            if (null == memoryCache.get(name))
            {
                if (!TextUtils.isEmpty(name) && null != bitmap)
                {
                    memoryCache.put(name, bitmap);
                }
                else
                {
                    Log.e(TAG, "name or bitmap is null"+ "name=" + name+ "bitmap=" + bitmap);
                }
            }
            else
            {
                Log.w(TAG, "The res is already exits");
            }
        }
    }

    public Bitmap getBitmapFromMemCache(String key)
    {
        return null != memoryCache ? memoryCache.get(key) : null;
    }

    /**
     * Remove image cache
     *
     * @param key
     */
    public void removeBitmapFromMemCache(String key)
    {
        synchronized (lock)
        {
            if (!TextUtils.isEmpty(key) && null != memoryCache)
            {
                Bitmap bm = memoryCache.remove(key);
                if (null != bm)
                {
                    bm.recycle();
                }
            }
        }
    }
}
