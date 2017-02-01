package com.Challenge.Zappos.products;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;


import com.Challenge.Zappos.data.Product;
import com.Challenge.Zappos.data.api.ProductListLoader;

import java.util.List;

/**
 * Created by jiwanbhandari on 1/31/17.
 */

public class ProductsPresenter implements LoaderManager.LoaderCallbacks<List<Product>>,ProductsContract.Presenter {

    private final LoaderManager mLoaderManger;
    private Context mContext;
    private final ProductsContract.View mProductsView;
    public ProductsPresenter(LoaderManager loaderManager, ProductsContract.View productsView, Context context){
        this.mLoaderManger=loaderManager;
        this.mContext = context;
        this.mProductsView = productsView;
        mProductsView.setPresenter(this);
    }

    @Override
    public Loader<List<Product>> onCreateLoader(int id, Bundle args) {
        return ProductListLoader.createListLoaderWithFilter(mContext);
    }

    @Override
    public void onLoadFinished(Loader<List<Product>> loader, List<Product> data) {
        mProductsView.showProducts(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Product>> loader) {

    }

    @Override
    public void start() {
        mLoaderManger.initLoader(0,null,this);
    }

    @Override
    public void loadProducts() {

    }
}
