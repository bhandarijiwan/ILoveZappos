package com.Challenge.Zappos.products;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;


import com.Challenge.Zappos.data.Product;
import com.Challenge.Zappos.data.api.ProductListLoader;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jiwanbhandari on 1/31/17.
 */

public class ProductsPresenter implements LoaderManager.LoaderCallbacks<List<Product>>,ProductsContract.Presenter {

    private final LoaderManager mLoaderManger;
    private static final String TAG="ProductsPresenter";
    private Context mContext;
    private final ProductsContract.View mProductsView;
    private ProductsFilter productsFilter;
    private HashMap<Integer,Product> cart;
    public ProductsPresenter(LoaderManager loaderManager, ProductsContract.View productsView, Context context){
        this.mLoaderManger=loaderManager;
        this.mContext = context;
        this.mProductsView = productsView;
        mProductsView.setPresenter(this);
        cart = new HashMap<Integer, Product>();
        productsFilter=ProductsFilter.createNewFilter();
    }

    @Override
    public Loader<List<Product>> onCreateLoader(int id, Bundle args) {
        return ProductListLoader.createListLoaderWithFilter(mContext,productsFilter);
    }

    @Override
    public void onLoadFinished(Loader<List<Product>> loader, List<Product> data) {
        Log.e(TAG,"onLoad Finished called");
        mProductsView.showProducts(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Product>> loader) {

    }

    @Override
    public boolean isInCart(int prodcutID){
        return cart.get(prodcutID)!=null;
    }

    @Override
    public void start() {
        mLoaderManger.initLoader(0,null,this);
    }

    @Override
    public void loadProducts(String query) {
        productsFilter = ProductsFilter.createNewFilter("term",query);
        mLoaderManger.restartLoader(0,null,this);
    }

    @Override
    public void addRemoveCart(View view,Product product){
        Product incart=cart.get(product.getProductId());
        int i = (incart!=null)?(1):(0);
        if(incart==null)cart.put(product.getProductId(),product);
        else cart.remove(product.getProductId());
        mProductsView.onAddRemoveCart(view,i);
    }

    @Override
    public void loadProductDetail(View view,Product product) {
        mProductsView.showProductDetailUi(view,product);
    }

}
