package com.mwaka.themoviedb.contracts;

import com.mwaka.themoviedb.responses.TrendingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrendingApiService {
    @GET("trending/all/week")
    Call<TrendingResponse> getTrendingMedia(@Query("api_key") String apiKey);
}
