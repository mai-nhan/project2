package com.example.dell.cambien.entity;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 5/10/2018.
 */

public class FilmTypeEntity {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Data")
    @Expose
    private List<FilmEntity> data;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilmEntity> getData() {
        return data;
    }

    public void setData(List<FilmEntity> data) {
        this.data = data;
    }
}
