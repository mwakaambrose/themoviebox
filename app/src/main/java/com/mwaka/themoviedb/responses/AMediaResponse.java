package com.mwaka.themoviedb.responses;

import com.google.gson.annotations.SerializedName;
import com.mwaka.themoviedb.models.Credit;
import com.mwaka.themoviedb.models.Genre;
import com.mwaka.themoviedb.models.Season;

import java.util.ArrayList;
import java.util.List;

public class AMediaResponse {

    @SerializedName("overview")
    private
    String overview;

    @SerializedName("number_of_episodes")
    private
    String numberOfEpisodes;

    @SerializedName("number_of_seasons")
    private
    String numberOfSeasons;

    @SerializedName("seasons")
    private
    List<Season> seasons;

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

    @SerializedName("title")
    private String title;

    @SerializedName("name")
    private String name;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("id")
    private int id;

    public AMediaResponse(String overview, String numberOfEpisodes, String numberOfSeasons, List<Season> seasons, List<Genre> genres, int page, Credit credit, int totalResults, int totalPages, Float vote_average, String title, String name, String posterPath, int id) {
        this.overview = overview;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.genres = genres;
        this.page = page;
        this.credit = credit;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.vote_average = vote_average;
        this.title = title;
        this.name = name;
        this.posterPath = posterPath;
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(String numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public String getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(String numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
