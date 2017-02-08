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
/**
 * This is a custom loader and it calls retrofit in the background thread
 * and delivers the result to the LoaderManger (ProductsPresenter) in the UI thread.
 */

public class ProductListLoader extends AsyncTaskLoader<List<Product>> {

    public static final String API_URL = "https://api.zappos.com";

    public static final String TAG="ProductListLoader";

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
    }

    public static ProductListLoader createListLoaderWithFilter(Context context, ProductsFilter filters){
        ProductListLoader loader = new ProductListLoader(context);
        loader.queryMap = filters.filterList;
        return loader;
    }


    @Override
    public List<Product> loadInBackground() {

        List<Product> entries=new ArrayList<Product>();

        Call<ProductSearchResponse> call = zappos.search(queryMap);

        try {
            // because we are already in the background thread,
            // retrofit can execute synchronously.
            ProductSearchResponse searchResponse = call.execute().body();
            if(searchResponse!=null)
                entries = searchResponse.results;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Done!
        return entries;
    }

    @Override
    public void deliverResult(List<Product> products){

        if(isReset())return;
        mProducts = products;
        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(mProducts);
        }
    }
    @Override
    protected void onStartLoading(){
        if(queryMap.size()<2) {

            return;
        }
        if (mProducts != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mProducts);
        }
       if (takeContentChanged() || mProducts == null ) {
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
    public void onCanceled(List<Product> products){
        super.onCanceled(products);
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
    }
}
