package com.example.bioscoopapp.Data;


import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Domain.MovieDetail;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.MovieListCreator;
import com.example.bioscoopapp.Domain.MovieListResponse;
import com.example.bioscoopapp.Domain.Page;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIConnection {

    private final APIKey apiKey;
    private final APICalls apiCalls;
    private final String TAG;

    public APIConnection() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        apiCalls = retrofit.create(APICalls.class);
        apiKey = new APIKey();
        // build retrofit class and instantiate a new apikey class
        TAG = getClass().getSimpleName();

    }

    public List<Movie> getPopularMovies() {
        CountDownLatch latch = new CountDownLatch(1);
        ArrayList<Movie> movieList = new ArrayList<>();
        // list that contains the movies and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<Page> callSync = apiCalls.getPopularMovies(apiKey.getAPI_KEY());
                Response<Page> response = callSync.execute();

                if (response.body() != null) {
                    movieList.addAll(response.body().getResults());
                }
                latch.countDown();
            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }
        }).start();
        // api fetch gets executed in new thread
        try {
            latch.await();
            return movieList;
            // returns list after countdown has been called
        } catch (InterruptedException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }

    public MovieDetail GetMovieDetails(String movieID) {
        CountDownLatch latch = new CountDownLatch(1);
        final MovieDetail[] movie = new MovieDetail[1];
        // array that contains the movie and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<MovieDetail> callSync = apiCalls.getMovieDetails(movieID, apiKey.getAPI_KEY());
                Response<MovieDetail> response = callSync.execute();
                System.out.println(response);
                movie[0] = response.body();
                latch.countDown();

            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }
        }).start();
        // api fetch gets executed in new thread
        try {
            latch.await();
            return movie[0];
            // returns object after countdown has been called
        } catch (InterruptedException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }

    public int addList(MovieListCreator movieList) {
        Call<MovieListResponse> call = apiCalls.postMovieList(movieList);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
        return 1;
    }

    public MovieList GetMovieList(int list_id) {
        CountDownLatch latch = new CountDownLatch(1);
        final MovieList[] movie = new MovieList[1];
        // array that contains the movie and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<MovieList> callSync = apiCalls.getListDetails(list_id, apiKey.getAPI_KEY());
                Response<MovieList> response = callSync.execute();
                System.out.println(response);
                movie[0] = response.body();
                latch.countDown();

            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }
        }).start();
        // api fetch gets executed in new thread
        try {
            latch.await();
            return movie[0];
            // returns object after countdown has been called
        } catch (InterruptedException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }

}
