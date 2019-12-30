package com.mwaka.themoviedb.models;

import com.google.gson.annotations.SerializedName;

public class Crew {
    @SerializedName("name")
    String name;

    @SerializedName("deportment")
    String department;

    @SerializedName("job")
    String job;

    public Crew(String name, String department, String job) {
        this.name = name;
        this.department = department;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
