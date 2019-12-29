package com.mwaka.themoviedb.contracts;

import com.mwaka.themoviedb.responses.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApiService {
    @GET("search/multi")
    Call<SearchResponse> multiSearch(@Query("api_key") String apiKey, @Query("query") String query);
}
