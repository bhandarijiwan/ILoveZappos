package com.Challenge.Zappos.data.source;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.Challenge.Zappos.data.source.api.RESTConnectionHelper;

import java.util.HashMap;

/**
 * Created by jiwanbhandari on 1/27/17.
 */

public class ProductsProvider extends ContentProvider {


    // A projection map used to select columns from the datastore
    private final HashMap<String,String>mProductsProjectionMap;

    // Uri matcher to decode incoming URI's

    private final UriMatcher mUriMatcher;

    // All URIs just map to the same URI_IDENTIFIER
    private static final int URI_IDENTIFIER = 1;


    private RESTConnectionHelper mRESTConnectionHelper;

    public ProductsProvider(){

        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        /*Matching all the URIs to the same identifier*/
        mUriMatcher.addURI(ProductsContract.CONTENT_AUTHORITY,"*",URI_IDENTIFIER);
        mProductsProjectionMap = new HashMap<String,String>();
        mProductsProjectionMap.put("Key","");
    }

    @Override
    public boolean onCreate() {
        mRESTConnectionHelper= new RESTConnectionHelper();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // not implmenting this operation
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // not implementing this operation
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //not implementing this operation
        return 0;
    }
}
