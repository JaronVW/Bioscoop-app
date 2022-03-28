package com.example.biocsoopapp.API;

import

import java.util.List;

import Domain.Movie;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APICalls  {
    @GET("get/popularMovies")
    Call<List<Movie>> popularMovies();
}
