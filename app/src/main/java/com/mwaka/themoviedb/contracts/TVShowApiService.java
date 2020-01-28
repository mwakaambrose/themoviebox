package com.mwaka.themoviedb.contracts;

import com.mwaka.themoviedb.responses.MoviesResponse;
import com.mwaka.themoviedb.responses.TVResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TVShowApiService {
    @GET("tv/top_rated")
    Call<TVResponse> getTopRatedShows(@Query("api_key") String apiKey);

    @GET("tv/airing_today")
    Call<TVResponse> getShowsAiringToday(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TVResponse> getPopularShows(@Query("api_key") String apiKey);

    @GET("tv/on_the_air")
    Call<TVResponse> getShowsOnTheAir(@Query("api_key") String apiKey, @Query("page") int page);
}
