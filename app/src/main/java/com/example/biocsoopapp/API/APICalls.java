package com.example.biocsoopapp.API;

import com.example.biocsoopapp.Domain.Movie;
import com.example.biocsoopapp.Domain.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICalls  {
    @GET("movie/popular")
    Call<Page> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/{movieID}")
    Call<Movie> getMovieDetails(@Path("movieID") String movieID,@Query("api_key") String api_key);
}
