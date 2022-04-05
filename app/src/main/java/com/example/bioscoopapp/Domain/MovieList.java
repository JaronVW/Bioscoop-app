package com.example.bioscoopapp.Domain;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Clock;
import java.time.LocalDate;

@Entity
public class MovieList {

    @PrimaryKey
    private transient int movieListID;

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    private transient String creationDate;

    private String name;

    private String description;

    private String language;

    public int getMovieListID() {
        return movieListID;
    }

    public void setMovieListID(int movieListID) {
        this.movieListID = movieListID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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
