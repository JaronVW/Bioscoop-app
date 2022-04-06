package com.example.bioscoopapp.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieListCreator {



    private String name;


    private String description;

 
    private String language;

    public MovieListCreator(String name, String description, String language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}