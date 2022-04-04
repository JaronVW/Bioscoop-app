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
    private int movieListID;

    @ColumnInfo(defaultValue =  "CURRENT_TIMESTAMP" )
    private String creationDate;


    private String name;

    public int getMovieListID() {
        return movieListID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setMovieListID(int movieListID) {
        this.movieListID = movieListID;
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
}
