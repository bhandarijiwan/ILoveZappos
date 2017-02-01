package com.Challenge.Zappos.data.api;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.Challenge.Zappos.data.Product;
import com.Challenge.Zappos.products.ProductsFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiwanbhandari on 1/31/17.
 */

public class ProductListLoader extends AsyncTaskLoader<List<Product>> {
    public static final String API_URL = "https://api.zappos.com";

    List<Product> mProducts;

    Retrofit retrofit;
    ZapposService zappos;
    private HashMap<String,String> queryMap;

    public ProductListLoader(Context context) {
        super(context);
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        zappos = retrofit.create(ZapposService.class);
        queryMap = ProductsFilter.createNewFilter().filterList;


    }

    public static ProductListLoader createListLoaderWithFilter(Context context, String...filters){
        ProductListLoader loader = new ProductListLoader(context);
        loader.queryMap = ProductsFilter.createNewFilter(filters).filterList;
        return loader;
    }


    @Override
    public List<Product> loadInBackground() {
        List<Product> entries=new ArrayList<Product>();
        queryMap.put("term","nike");
        Call<ProductSearchResponse> call = zappos.search(queryMap);

        Log.e("LoadInBackGround",call.request().url().toString());

        try {
            ProductSearchResponse searchResponse = call.execute().body();
            entries = searchResponse.results;
            //for(Product product:searchResponse.results)entries.add(new AppEntry(product.toString()));
            Log.e("LoaderTest","Search Original Term="+searchResponse.originalTerm);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Done!
        return entries;
    }

    @Override
    public void deliverResult(List<Product> apps){


        List<Product> oldApps = mProducts;
        mProducts = apps;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(apps);
        }
    }
    @Override
    protected void onStartLoading(){
        if (mProducts != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mProducts);
        }

        if (takeContentChanged() || mProducts == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }

    }
    @Override
    protected void onStopLoading(){
        cancelLoad();
    }

    @Override
    public void onCanceled(List<Product> apps){
        super.onCanceled(apps);
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
    }
}
