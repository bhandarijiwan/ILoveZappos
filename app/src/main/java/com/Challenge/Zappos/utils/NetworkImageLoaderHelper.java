package com.Challenge.Zappos.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by jiwanbhandari on 2/2/17.
 */

public class NetworkImageLoaderHelper {

    private static NetworkImageLoaderHelper instance;
    private ImageLoader mbitMapLoader;
    private final LruCache<String,Bitmap>mbitMapCache;
    private NetworkImageLoaderHelper(Context appContenxt){
        ActivityManager am =(ActivityManager) appContenxt.getSystemService(Context.ACTIVITY_SERVICE);
        int availableMemory = am.getMemoryClass()*1024*1024;
        RequestQueue queue = Volley.newRequestQueue(appContenxt);
        mbitMapCache = new LruCache<String,Bitmap>(availableMemory/8);
        ImageLoader.ImageCache bitMapCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return mbitMapCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mbitMapCache.put(url,bitmap);
            }
        };

        mbitMapLoader = new ImageLoader(queue,bitMapCache);

    }

    public static NetworkImageLoaderHelper getInstance(Context context){
        if(instance==null)
            instance = new NetworkImageLoaderHelper(context.getApplicationContext());

        return instance;
    }

    public ImageLoader getMbitMapLoader(){
        return mbitMapLoader;
    }

    public Bitmap getImage(String url){

        return instance.mbitMapCache.get(url);

    }


}
