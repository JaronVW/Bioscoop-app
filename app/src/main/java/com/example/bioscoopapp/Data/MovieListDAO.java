package com.example.bioscoopapp.Data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Domain.MovieList;

import java.util.Collection;
import java.util.List;

@Dao
public interface MovieListDAO {

    @Insert
    void insertAll(MovieList... movieLists);


    @Query("SELECT * FROM MovieList")
    List<MovieList> getAll();
}
