package com.mwaka.themoviedb.models;

import com.google.gson.annotations.SerializedName;

public class Cast {
    @SerializedName("character")
    String character;

    @SerializedName("name")
    String name;

    @SerializedName("profile_path")
    String profilePath;

    public Cast(String character, String name, String profilePath) {
        this.character = character;
        this.name = name;
        this.profilePath = profilePath;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
