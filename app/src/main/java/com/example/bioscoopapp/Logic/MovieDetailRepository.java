package com.example.bioscoopapp.Logic;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Domain.MovieDetail;

public class MovieDetailRepository {

    private final APIConnection apiConnection;

    public MovieDetailRepository() {
        this.apiConnection = new APIConnection();
    }

    public MovieDetail getMovieDetails(String movieID, String langCode){
        return apiConnection.GetMovieDetails(movieID, langCode);
    }
}
