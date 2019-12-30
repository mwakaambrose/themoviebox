package com.mwaka.themoviedb.responses;

import com.google.gson.annotations.SerializedName;
import com.mwaka.themoviedb.models.Cast;
import com.mwaka.themoviedb.models.Credit;
import com.mwaka.themoviedb.models.Crew;
import com.mwaka.themoviedb.models.Genre;
import com.mwaka.themoviedb.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class AMovieResponse {

    @SerializedName("overview")
    String overview;

    @SerializedName("genres")
    List<Genre> genres;

    @SerializedName("page")
    private int page;

    @SerializedName("credits")
    private Credit credit;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("vote_average")
    private Float vote_average;

    public AMovieResponse(String overview, List<Genre> genres, int page, Credit credit, int totalResults, int totalPages, Float vote_average) {
        this.overview = overview;
        this.genres = genres;
        this.page = page;
        this.credit = credit;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public int getPage() {
        return page;
    }

    public Credit getCredit() {
        return credit;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public Float getVote_average() {
        return vote_average;
    }
}
