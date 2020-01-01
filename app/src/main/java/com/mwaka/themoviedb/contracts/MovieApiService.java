package com.mwaka.themoviedb.contracts;

import com.mwaka.themoviedb.responses.AMediaResponse;
import com.mwaka.themoviedb.responses.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<AMediaResponse> getMovie(@Path("movie_id") String movieId, @Query("api_key") String apiKey, @Query("append_to_response") String credits);

}
