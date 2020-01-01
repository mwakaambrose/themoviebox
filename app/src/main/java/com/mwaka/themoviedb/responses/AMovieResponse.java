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
    private
    String overview;

    @SerializedName("genres")
    private
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

    public AMovieResponse(String overview, String name, String firstAirDate, List<Genre> genres, int page, Credit credit, int totalResults, int totalPages, Float vote_average) {
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

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
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

    public Float getVote_average() {
        return vote_average;
    }

    public void setVote_average(Float vote_average) {
        this.vote_average = vote_average;
    }
}
