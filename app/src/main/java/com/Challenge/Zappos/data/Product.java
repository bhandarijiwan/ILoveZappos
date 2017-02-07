package com.Challenge.Zappos.data;

/**
 * Created by jiwanbhandari on 1/26/17.
 */

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Immutable model class for a Product (Search Result Item)
 */


public final class Product implements Serializable
{

    @NonNull
    private final String productName;

    @NonNull
    private final String productUrl;

    private final String originalPrice;

    private final int productId;

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
        this.productId = productID;
        this.brandName = brandName;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.colorId = colorId;
        this.styleId = colorId;
        this.percentOff = percentOff;

    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    @NonNull
    public String getProductUrl() {
        return productUrl;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public int getProductId() {
        return productId;
    }

    public String getPercentOff(){
        return  percentOff.replaceAll("%","");
    }

    public boolean hasDiscount(){
        return Float.parseFloat(percentOff.replaceAll("%",""))>0;
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

    public String getDiscountedPrice(){
        float originalPrice = Float.parseFloat(this.originalPrice.replaceAll("\\$|,|%",""));
        float discount = Float.parseFloat(percentOff.replaceAll("%|,|\\$",""));
        DecimalFormat df = new DecimalFormat("#.##");
        return "$"+df.format(originalPrice - originalPrice * (discount/100.0f));

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productId != product.productId) return false;
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
        result = 31 * result + productId;
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
                ", productID=" + productId +
                ", brandName='" + brandName + '\'' +
                ", thumbnailImageUrl='" + thumbnailImageUrl + '\'' +
                ", colorId=" + colorId +
                ", styleId=" + styleId +
                ", percentOff='" + percentOff + '\'' +
                '}';
    }
}
