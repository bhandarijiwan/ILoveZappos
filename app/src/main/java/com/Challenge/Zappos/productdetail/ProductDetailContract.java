package com.Challenge.Zappos.productdetail;

import com.Challenge.Zappos.data.Product;

/**
 * Created by jiwanbhandari on 2/2/17.
 */

public interface ProductDetailContract {

    interface View{
        void setPresenter(Presenter presenter);
        void showProduct(Product product);
    }
    interface Presenter{
        void start();
    }
}
