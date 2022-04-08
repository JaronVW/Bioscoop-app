package com.example.bioscoopapp.Data;

import com.example.bioscoopapp.Domain.Account;
import com.example.bioscoopapp.Domain.MediaID;
import com.example.bioscoopapp.Domain.MovieDetail;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.MovieListCreator;
import com.example.bioscoopapp.Domain.MovieListPage;
import com.example.bioscoopapp.Domain.MovieListResponse;
import com.example.bioscoopapp.Domain.MoviePostToListPostResponse;
import com.example.bioscoopapp.Domain.Page;
import com.example.bioscoopapp.Domain.VideoResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICalls  {

    @GET("movie/popular")
    Call<Page> getPopularMovies(@Query("api_key") String api_key, @Query("language") String language);

    @GET("movie/{movieID}")
    Call<MovieDetail> getMovieDetails(@Path("movieID") String movieID, @Query("api_key") String api_key,
                                      @Query("language") String language);

    @GET("movie/{movie_id}/videos")
    Call<VideoResult> getMovieVideo(@Path("movie_id") String movieID, @Query("api_key") String api_key);

    @POST("list")
    Call<MovieListResponse> postMovieList(@Query("api_key") String api_key, @Query("session_id") String session_id, @Body MovieListCreator list);

    @GET("list/{list_id}")
    Call<MovieList> getMovieList(@Path("list_id") int list_id, @Query("api_key") String api_key);

    @GET("account")
    Call<Account> getAccount(@Query("api_key") String api_key, @Query("session_id") String session_id);

    @GET("account/{account_id}/lists")
    Call<MovieListPage> getAccountLists(@Path("account_id") int account_id, @Query("api_key") String api_key, @Query("session_id") String session_id);

    @GET("list/{list_id}")
    Call<MovieList> getMovieListDetails(@Path("list_id") int list_id, @Query("api_key") String api_key, @Query("language") String language);

    @POST("list/{list_id}/add_item")
    Call<MoviePostToListPostResponse> postToList(@Path("list_id") int list_id, @Query("api_key") String api_key, @Query("session_id") String session_id, @Body MediaID mediaID);

    @DELETE("list/{list_id}")
    Call<MoviePostToListPostResponse> deleteList(@Path("list_id") int list_id, @Query("api_key") String api_key, @Query("session_id") String session_id);

    @POST("list/{list_id}/remove_item")
    Call<MoviePostToListPostResponse> DeleteFromList(@Path("list_id") int list_id, @Query("api_key") String api_key, @Query("session_id") String session_id, @Body MediaID mediaID);

}
