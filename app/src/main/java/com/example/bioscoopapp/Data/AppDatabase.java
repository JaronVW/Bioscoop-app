package com.example.bioscoopapp.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bioscoopapp.Domain.Genre;
import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Domain.MovieGenres;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.MoviesInList;

@Database(entities = {Movie.class, Genre.class, MovieList.class, MoviesInList.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDAO movieDAO();
    public abstract GenreDAO genreDAO();
    public abstract MovieListDAO movieListDAO();
    public abstract MoviesInListDAO moviesInListDAO();

}
