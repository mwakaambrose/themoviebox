package com.mwaka.themoviedb.contracts;

import com.mwaka.themoviedb.models.TopRatedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/top_rated")
    Call<TopRatedResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<TopRatedResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<TopRatedResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<TopRatedResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

}
