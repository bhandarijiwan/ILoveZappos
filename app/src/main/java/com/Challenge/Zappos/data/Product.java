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
    private final String mproductName;

    @NonNull
    private final String mproductUrl;

    private final float moriginalPrice;

    private int mproductID;

    public Product(@NonNull String productName , @NonNull String productUrl, float oiginalPrice,
                   int productID){
        mproductName = productName;
        mproductUrl= productUrl;
        moriginalPrice = oiginalPrice;
        mproductID = productID;
    }


    @NonNull
    public String getMproductName() {
        return mproductName;
    }

    @NonNull
    public String getMproductUrl() {
        return mproductUrl;
    }

    public float getMoriginalPrice() {
        return moriginalPrice;
    }

    public int getMproductID() {
        return mproductID;
    }

    public void setMproductID(int mproductID) {
        this.mproductID = mproductID;
    }

    @Override
    public String toString() {
        return "Product {" +
                "mproductName='" + mproductName + '\'' +
                ", mproductUrl='" + mproductUrl + '\'' +
                ", moriginalPrice=" + moriginalPrice +
                ", mproductID=" + mproductID +
                '}';
    }



    @Override
    public int hashCode() {
        int result = mproductName.hashCode();
        result = 31 * result + mproductUrl.hashCode();
        result = 31 * result + (moriginalPrice != +0.0f ? Float.floatToIntBits(moriginalPrice) : 0);
        result = 31 * result + mproductID;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Float.compare(product.moriginalPrice, moriginalPrice) != 0) return false;
        if (mproductID != product.mproductID) return false;
        if (!mproductName.equals(product.mproductName)) return false;
        return mproductUrl.equals(product.mproductUrl);

    }
}
