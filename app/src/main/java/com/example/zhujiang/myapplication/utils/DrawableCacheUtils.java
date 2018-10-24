package com.example.zhujiang.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.WeakHashMap;

/**
 * @author zhuj 2018/10/24 下午3:56.
 */
public class DrawableCacheUtils {

    static HashMap<String, WeakReference<Bitmap>> mWeakHashMap = new HashMap<>();

    public static Bitmap getDrawable(String zip, String path) {
        if (mWeakHashMap.containsKey(path)) {
            return mWeakHashMap.get(path).get();
        }
        try {
            Bitmap bitmap = ZipResUtils.readGuidePic(zip, path);
            mWeakHashMap.put(path, new WeakReference<Bitmap>(bitmap));
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private LruCache<String, Bitmap> lruCache;

    public DrawableCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        int cacheSize = (int) (maxMemory / 8);
        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public Bitmap getBitmapFromMemCache(String zip, String key) {
        Bitmap bitmap = lruCache.get(key);
        if (bitmap == null) {
            try {
                Bitmap newBitMap = ZipResUtils.readGuidePic(zip, key);
                lruCache.put(key, newBitMap);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
