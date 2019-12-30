package com.mwaka.themoviedb.models;

import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("name")
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
