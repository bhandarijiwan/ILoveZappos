package com.Challenge.Zappos.products;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;


import com.Challenge.Zappos.data.Product;
import com.Challenge.Zappos.data.api.ProductListLoader;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jiwanbhandari on 1/31/17.
 */

/**
 * Implements the Presenter interface in the ProductsContract.Presenter.
 * Coordinate the View and also does model loading.
 */
public class ProductsPresenter implements LoaderManager.LoaderCallbacks<List<Product>>,ProductsContract.Presenter {

    private final LoaderManager mLoaderManger;
    private static final String TAG="ProductsPresenter";
    private Context mContext;
    private final ProductsContract.View mProductsView;
    private ProductsFilter productsFilter;

    private HashSet<Integer>mCart;

    public ProductsPresenter(LoaderManager loaderManager, ProductsContract.View productsView,
                             Context context){
        this.mLoaderManger=loaderManager;
        this.mContext = context;
        this.mProductsView = productsView;
        mProductsView.setPresenter(this);
        productsFilter=ProductsFilter.createNewFilter();
        mCart = new HashSet<>();

    }
    @Override
    public HashSet<Integer> getCart(){
        return mCart;
    }

    @Override
    public Loader<List<Product>> onCreateLoader(int id, Bundle args) {
        // kick off a new loader with default filters.
        return ProductListLoader.createListLoaderWithFilter(mContext,productsFilter);
    }

    @Override
    public void onLoadFinished(Loader<List<Product>> loader, List<Product> data) {
        // tell the view to show the returned result
        mProductsView.showProducts(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Product>> loader) {}

    /**
     * Check if an item is already in the cart.
     * @param prodcutID productID of the item
     * @return true if item is in the cart false otherwise
     */
    @Override
    public boolean isInCart(int prodcutID){
        return mCart.contains(prodcutID);
    }

    /**
     * Start the presenter. Called from the View.
     * This happens when the activity is recreate or resumed.
     */
    @Override
    public void start() {
        // kick off a new loader
        mLoaderManger.initLoader(0,null,this);
    }

    /** Called by the View to tell the presenter to
     *  search and fetch the products for the user supplied query.
     * @param query user entered/spoken query.
     */
    @Override
    public void loadProducts(String query) {
        productsFilter = ProductsFilter.createNewFilter("term",query);
        mLoaderManger.restartLoader(0,null,this);
    }

    /**
     * Either add or remove product from the cart
     * depending on whether it's in the cart.
     * @param view ViewHolder clicked int the RecyclerView
     * @param product product cliked
     */
    @Override
    public void addRemoveCart(View view,Product product){


        int i = (mCart.contains(product.hashCode()))?(1):(0);

        if(i==0)mCart.add(product.hashCode());

        else mCart.remove(product.hashCode());

        mProductsView.onAddRemoveCart(view,i);
    }

    @Override
    public void setCart(HashSet<Integer> e) {
       mCart = e;
    }

    /** Method bound to ViewHolder.onClick in the layout.
     *  Called when the user selects to view the details of a product.
     * @param view ViewHolder in the RecyclerView
     * @param product product selected
     */
    @Override
    public void loadProductDetail(View view,Product product) {
        mProductsView.showProductDetailUi(view,product);
    }

}
