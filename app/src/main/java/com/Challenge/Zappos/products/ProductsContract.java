package com.Challenge.Zappos.products;



import com.Challenge.Zappos.data.Product;

import java.util.List;

/**
 * Created by jiwanbhandari on 1/31/17.
 */


/* This interface specifies the interface between the view and the presenter.*/
public interface ProductsContract {

    interface View{
        void showProducts(List<Product> products);
        void setPresenter(Presenter presenter);
    }

    interface Presenter{

        void start();
        void loadProducts(String query);
        void loadProduct(Product product);

    }
}
