package com.Challenge.Zappos.data.source.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by jiwanbhandari on 1/26/17.
 */
/**
 * Models the zappos product search api
 */

public interface ZapposService {

    @GET("Search")
    Call<ProductSearchResponse> search(@QueryMap Map<String,String> searchfilter);

}
