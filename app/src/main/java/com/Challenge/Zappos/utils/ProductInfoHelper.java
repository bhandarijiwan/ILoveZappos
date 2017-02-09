package com.Challenge.Zappos.utils;

import android.content.Context;
import android.content.Intent;

import com.Challenge.Zappos.data.Product;

import java.text.DecimalFormat;

/**
 * Created by jiwanbhandari on 2/9/17.
 */

/* This is a help class to get useful information from product.
 * Having this class helps because a lot of utility methods from
 * the Product class can be moved into this so that the product
 * class is purely a model class. The functions are supposed to accessed
 * as if they are non-member function but scoped/namespaced by the class.
  * * */
public class ProductInfoHelper {

    /**
     * Don't make an instance, just use the help functions
     */
    private ProductInfoHelper(){}

    /**
     * Given a product, generates a shareable intent
     * @param product product to be shared
     * @return intent to be fed to the ShareActionProvider
     */
    public static Intent getShareableIntent(Product product){

        if(product==null) return null;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(product.getProductName());
        stringBuilder.append(" ");
        stringBuilder.append(product.getProductUrl());
        intent.putExtra(Intent.EXTRA_TEXT,stringBuilder.toString());
        intent.setType("text/plain");
        return intent;
    }
    public static String getDiscountedPrice(Product product){
        float originalPrice = Float.parseFloat(product.getOriginalPrice().replaceAll("\\$|,|%",""));
        float discount = Float.parseFloat(product.getPercentOff().replaceAll("%|,|\\$",""));
        DecimalFormat df = new DecimalFormat("#.##");
        return "$"+df.format(originalPrice - originalPrice * (discount/100.0f));

    }

}
