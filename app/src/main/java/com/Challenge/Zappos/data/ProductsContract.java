package com.Challenge.Zappos.data;

import android.net.Uri;

import com.Challenge.Zappos.BuildConfig;

/**
 * Created by jiwanbhandari on 1/26/17.
 */
public final class ProductsContract {

    private ProductsContract(){};

    /* The authority for out content provider.*/
    public static final String CONTENT_AUTHORITY= BuildConfig.APPLICATION_ID;

    /* The scheme of our provider.*/
    private static final String CONTENT_SCHEME="content://";

    /* The content:// style URL for our table*/
    public static final Uri BASE_CONTENT_URI=Uri.parse(CONTENT_SCHEME+CONTENT_AUTHORITY);

    /* The Key to be used*/
    public static final String KEY_VALUE= "b743e26728e16b81da139182bb2094357c31d331";

    /* The column (filter) to be used.*/

    public static final String PROJECTION_FIELD="term";

}
