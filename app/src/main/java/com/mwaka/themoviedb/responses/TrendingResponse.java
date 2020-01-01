package com.mwaka.themoviedb.responses;

import com.google.gson.annotations.SerializedName;
import com.mwaka.themoviedb.models.Movie;
import com.mwaka.themoviedb.models.Trending;

import java.util.List;

public class TrendingResponse {

    @SerializedName("results")
    private List<Trending> results;

    public List<Trending> getResults() {
        return results;
    }
}
