package com.Challenge.Zappos.data.source.api;

import com.Challenge.Zappos.data.Product;

import java.util.List;

import static android.R.id.list;

/**
 * Created by jiwanbhandari on 1/26/17.
 */
/*Models the response from zappos api*/

public class ProductSearchResponse {


    public final List<Product>results;
    public final String originalTerm;
    public final int currentResultCount;
    public final int totalResultCount;
    public final String term;

    public ProductSearchResponse(List<Product>results,String originalTerm,int currentResultCount,
                                 int totalResultCount,String term){
        this.results = results;
        this.originalTerm= originalTerm;
        this.currentResultCount = currentResultCount;
        this.totalResultCount = totalResultCount;
        this.term=term;
    }


}
