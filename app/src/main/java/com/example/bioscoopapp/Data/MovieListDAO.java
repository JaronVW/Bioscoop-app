package com.example.bioscoopapp.Data;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.bioscoopapp.Domain.Movie;

@Dao
public interface MovieListDAO {

    @Insert
    void insertAll(Movie... movies);
}
