package com.mwaka.themoviedb.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Credit {

    public String name = "Mwaka";

    @SerializedName("cast")
    @Expose
    List<Cast> casts;

    @SerializedName("crew")
    @Expose
    List<Crew> crews;

    public Credit(List<Cast> casts, List<Crew> crews) {
        this.casts = casts;
        this.crews = crews;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    public List<Crew> getCrews() {
        return crews;
    }

    public void setCrews(List<Crew> crews) {
        this.crews = crews;
    }
}
