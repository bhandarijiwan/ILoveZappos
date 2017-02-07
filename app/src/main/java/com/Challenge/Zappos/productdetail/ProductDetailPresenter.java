package com.Challenge.Zappos.productdetail;

import com.Challenge.Zappos.data.Product;

/**
 * Created by jiwanbhandari on 2/2/17.
 */

public class ProductDetailPresenter implements ProductDetailContract.Presenter{


    private Product mProduct;
    private ProductDetailContract.View mView;

    ProductDetailPresenter(Product product, ProductDetailContract.View view){
        mProduct=product;
        mView=view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showProduct(mProduct);
    }





}
