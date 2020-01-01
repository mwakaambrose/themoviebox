package com.mwaka.themoviedb.models;

import com.google.gson.annotations.SerializedName;

public class Season {

    @SerializedName("name")
    private
    String name;

    @SerializedName("air_date")
    private
    String airDate;

    @SerializedName("episode_count")
    private
    int episodeCount;

    @SerializedName("overview")
    private
    String overview;

    @SerializedName("poster_path")
    private
    String posterPath;

    @SerializedName("season_number")
    private
    int seasonNumber;

    @SerializedName("id")
    private
    int id;

    public Season(String name, String airDate, int episodeCount, String overview, String posterPath, int seasonNumber, int id) {
        this.name = name;
        this.airDate = airDate;
        this.episodeCount = episodeCount;
        this.overview = overview;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
