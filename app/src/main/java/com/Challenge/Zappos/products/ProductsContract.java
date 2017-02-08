package com.Challenge.Zappos.products;



import android.view.View;

import com.Challenge.Zappos.data.Product;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jiwanbhandari on 1/31/17.
 */


/* This interface specifies the interface between the view and the presenter.*/
public interface ProductsContract {

    interface View{
        void showProducts(List<Product> products);
        void setPresenter(Presenter presenter);
        void showProductDetailUi(android.view.View view,Product product);
        void onAddRemoveCart(android.view.View view,int n);
    }

    interface Presenter{

        void start();
        void loadProducts(String query);
        void loadProductDetail(android.view.View view, Product product);
        void addRemoveCart(android.view.View view,Product product);
        <T extends Serializable> T getCart();
        void setCart(HashSet<Integer> e);
        boolean isInCart(int productID);

    }
}
