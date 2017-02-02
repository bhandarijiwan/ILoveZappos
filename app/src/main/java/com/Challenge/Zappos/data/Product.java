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

    private final int productID;

    private final String brandName;

    private final String thumbnailImageUrl;

    private final int colorId;

    private final int styleId;

    private final String percentOff;

    public Product(@NonNull String productName , @NonNull String productUrl, String oiginalPrice,
                   int productID,String brandName,String thumbnailImageUrl,int colorId,int styleId,
                   String percentOff){
        this.productName = productName;
        this.productUrl= productUrl;
        this.originalPrice = oiginalPrice;
        this.productID = productID;
        this.brandName = brandName;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.colorId = colorId;
        this.styleId = colorId;
        this.percentOff = percentOff;

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

    public String getPercentOff() {
        return percentOff;
    }

    public int getStyleId() {
        return styleId;
    }

    public int getColorId() {
        return colorId;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public String getBrandName() {
        return brandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productID != product.productID) return false;
        if (colorId != product.colorId) return false;
        if (styleId != product.styleId) return false;
        if (!productName.equals(product.productName)) return false;
        if (!productUrl.equals(product.productUrl)) return false;
        if (originalPrice != null ? !originalPrice.equals(product.originalPrice) : product.originalPrice != null)
            return false;
        if (brandName != null ? !brandName.equals(product.brandName) : product.brandName != null)
            return false;
        if (thumbnailImageUrl != null ? !thumbnailImageUrl.equals(product.thumbnailImageUrl) : product.thumbnailImageUrl != null)
            return false;
        return percentOff != null ? percentOff.equals(product.percentOff) : product.percentOff == null;

    }

    @Override
    public int hashCode() {
        int result = productName.hashCode();
        result = 31 * result + productUrl.hashCode();
        result = 31 * result + (originalPrice != null ? originalPrice.hashCode() : 0);
        result = 31 * result + productID;
        result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
        result = 31 * result + (thumbnailImageUrl != null ? thumbnailImageUrl.hashCode() : 0);
        result = 31 * result + colorId;
        result = 31 * result + styleId;
        result = 31 * result + (percentOff != null ? percentOff.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productUrl='" + productUrl + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", productID=" + productID +
                ", brandName='" + brandName + '\'' +
                ", thumbnailImageUrl='" + thumbnailImageUrl + '\'' +
                ", colorId=" + colorId +
                ", styleId=" + styleId +
                ", percentOff='" + percentOff + '\'' +
                '}';
    }
}
