package com.example.bioscoopapp.Data;

import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.MovieListCreator;
import com.example.bioscoopapp.Domain.MovieListResponse;
import com.example.bioscoopapp.Domain.RequestToken;
import com.example.bioscoopapp.Domain.MovieDetail;
import com.example.bioscoopapp.Domain.Page;
import com.example.bioscoopapp.Domain.SessionToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICalls  {

    @GET("movie/popular")
    Call<Page> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/{movieID}")
    Call<MovieDetail> getMovieDetails(@Path("movieID") String movieID, @Query("api_key") String api_key);

    @POST("list?api_key=634c150aa73459c1eeb332b65031708b&session_id=286e4443900240ae91445c6df58c187693bc1e70")
    Call<MovieListResponse> postMovieList(@Body MovieListCreator movieList);

    @GET("list/{list_id}")
    Call<MovieList> getListDetails(@Path("list_id") int list_id, @Query("api_key") String api_key);

}
