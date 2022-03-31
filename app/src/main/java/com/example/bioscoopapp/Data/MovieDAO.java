package com.example.bioscoopapp.Data;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.bioscoopapp.Domain.Movie;

import java.util.List;

@Dao
public interface MovieDAO {

    @Query("Select * FROM Movie")
    List<Movie> getAll();

    @Query("select count(1) where exists (select * from Movie)")
    Boolean isTableEmpty();

}
