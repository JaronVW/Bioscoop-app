package com.example.bioscoopapp.Data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bioscoopapp.Domain.MovieList;

@Dao
public interface MovieListDAO {

    @Insert
    void insertAll(MovieList... movieLists);

    @Query("DELETE FROM MovieList")
    void cleanDB();
}
