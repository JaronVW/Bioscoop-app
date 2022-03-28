package com.example.biocsoopapp.API;

import com.example.Domain.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APICalls  {
    @GET("movie/popular")
    Call<Page> popularMovies(@Query("api_key") String api_key);
}
