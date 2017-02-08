package com.Challenge.Zappos.data.api;

import com.Challenge.Zappos.data.Product;

import java.util.List;

/**
 * Created by jiwanbhandari on 1/26/17.
 */

/*Models the response from zappos api.
* This class represents the entire search response.
* */

public final class ProductSearchResponse  {
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
