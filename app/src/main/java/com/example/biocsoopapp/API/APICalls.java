package com.example.biocsoopapp.API;

import java.util.List;

import Domain.Movie;
import Domain.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICalls  {
    @GET("movie/popular")
    Call<Page> popularMovies(@Query("api_key") String api_key);
}
