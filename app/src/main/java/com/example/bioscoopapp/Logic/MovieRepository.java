package com.example.bioscoopapp.Logic;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Domain.Movie;

import java.util.List;

public class MovieRepository {
    private final APIConnection apiConnection;

    public MovieRepository() {
        this.apiConnection = new APIConnection();
    }

    public List<Movie> GetPopularMovies(){
        return apiConnection.getPopularMovies();
    }
}
