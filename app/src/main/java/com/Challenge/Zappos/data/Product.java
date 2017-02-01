package com.Challenge.Zappos.data;

/**
 * Created by jiwanbhandari on 1/26/17.
 */

import android.support.annotation.NonNull;

/**
 * Immutable model class for a Product (Search Result Item)
 */


public final class Product
{

    @NonNull
    private final String productName;

    @NonNull
    private final String productUrl;

    private final String originalPrice;

    private int productID;

    public Product(@NonNull String productName , @NonNull String productUrl, String oiginalPrice,
                   int productID){
        this.productName = productName;
        this.productUrl= productUrl;
        this.originalPrice = oiginalPrice;
        this.productID = productID;
    }


    @NonNull
    public String getMproductName() {
        return productName;
    }

    @NonNull
    public String getMproductUrl() {
        return productUrl;
    }

    public String getMoriginalPrice() {
        return originalPrice;
    }

    public int getMproductID() {
        return productID;
    }

    public void setMproductID(int mproductID) {
        this.productID = mproductID;
    }

    @Override
    public String toString() {
        return "Product {" +
                "mproductName='" + productName + '\'' +
                ", mproductUrl='" + productUrl + '\'' +
                ", moriginalPrice=" + originalPrice +
                ", mproductID=" + productID +
                '}';
    }



    @Override
    public int hashCode() {
        int result = productID;
        result = 31 * result + productUrl.hashCode();
        result = 31 * result + originalPrice.hashCode();
        result = 31 * result + productID;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!originalPrice.equals(product.originalPrice)) return false;
        if (productID != product.productID) return false;
        if (!productName.equals(product.productName)) return false;
        return productUrl.equals(product.productUrl);

    }
}
