package com.example.bioscoopapp.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bioscoopapp.Domain.Genre;
import com.example.bioscoopapp.Domain.Movie;

@Database(entities = {Movie.class, Genre.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDAO movieDAO();
    public abstract GenreDAO genreDAO();

}
