package com.example.bioscoopapp.Data;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.bioscoopapp.Domain.Genre;

import java.util.List;

@Dao
public interface GenreDAO {
    @Query("SELECT * FROM Genre")
    List<Genre> getAll();
}
