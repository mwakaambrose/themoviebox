package com.mwaka.themoviedb.models;

import android.graphics.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopRatedResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<TopRated> results;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TopRated> getResults() {
        return results;
    }

    public void setResults(List<TopRated> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
