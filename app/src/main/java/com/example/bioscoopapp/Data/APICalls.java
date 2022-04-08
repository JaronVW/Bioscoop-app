package com.example.bioscoopapp.Data;

import com.example.bioscoopapp.Domain.Genre;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.RequestToken;
import com.example.bioscoopapp.Domain.MovieDetail;
import com.example.bioscoopapp.Domain.Page;
import com.example.bioscoopapp.Domain.SessionToken;
import com.example.bioscoopapp.Domain.Video;
import com.example.bioscoopapp.Domain.VideoResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICalls  {

    @GET("movie/popular")
    Call<Page> getPopularMovies(@Query("api_key") String api_key, @Query("language") String language);

    @GET("movie/{movieID}")
    Call<MovieDetail> getMovieDetails(@Path("movieID") String movieID, @Query("api_key") String api_key,
                                      @Query("language") String language);
    @GET("genre/movie/list")
    Call<List<Genre>> getGenres(@Query("api_key") String api_key);


    @GET("movie/{movie_id}/videos")
    Call<VideoResult> getMovieVideo(@Path("movie_id") String movieID, @Query("api_key") String api_key);

    @HTTP(method = "POST", path = "https://api.themoviedb.org/3/list", hasBody = true)
    Call<Boolean> postMovieList(@Query("api_key") String api_key,@Query("session_id") String session_id, @Body MovieList movieList);

}
