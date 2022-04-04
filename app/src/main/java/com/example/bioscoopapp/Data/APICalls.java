package com.example.bioscoopapp.Data;

import com.example.bioscoopapp.Domain.RequestToken;
import com.example.bioscoopapp.Domain.MovieDetail;
import com.example.bioscoopapp.Domain.Page;
import com.example.bioscoopapp.Domain.SessionToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICalls  {
    @GET("movie/popular")
    Call<Page> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/{movieID}")
    Call<MovieDetail> getMovieDetails(@Path("movieID") String movieID, @Query("api_key") String api_key);

    @GET("authentication/token/new")
    Call<RequestToken> getRequestToken(@Path("movieID")@Query("api_key") String api_key);

    @GET("authentication/token/new")
    Call<SessionToken> getSessionToken(@Query("api_key") String api_key,@Body RequestToken requestToken);

}
