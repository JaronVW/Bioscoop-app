package com.example.biocsoopapp.API;

import java.util.List;

import Domain.Movie;
import retrofit2.Call;

public class Retrofit implements APICalls {
    Retrofit retrofit = new Retrofit.Builder();

    @Override
    public Call<List<Movie>> popularMovies() {
        return null;
    }
}
