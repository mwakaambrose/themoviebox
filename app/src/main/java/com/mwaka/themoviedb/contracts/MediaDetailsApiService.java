package com.mwaka.themoviedb.contracts;

import com.mwaka.themoviedb.responses.AMediaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MediaDetailsApiService {
    @GET("{type}/{id}")
    Call<AMediaResponse> getMediaDetails(@Path("type") String media_type, @Path("id") String media_id, @Query("api_key") String apiKey, @Query("append_to_response") String extras);
}
