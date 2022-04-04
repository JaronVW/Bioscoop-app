package com.example.bioscoopapp.Domain;

import androidx.room.Entity;



@Entity(primaryKeys = {"movieListID", "movieID"})
public class MoviesInList {
    public int movieListID;
    public int movieID;
}
