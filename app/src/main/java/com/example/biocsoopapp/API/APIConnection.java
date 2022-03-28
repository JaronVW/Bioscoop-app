package com.example.biocsoopapp.API;


import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import com.example.biocsoopapp.Domain.Movie;
import com.example.biocsoopapp.Domain.Page;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIConnection {

    private final APIKey apiKey;
    private final APICalls apiCalls;

    public APIConnection() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        apiCalls = retrofit.create(APICalls.class);
        apiKey = new APIKey();
    }

    public List<Movie> getPopularMovies() {
        CountDownLatch latch = new CountDownLatch(1);
        List<Movie> movieList = new ArrayList<>();

        new Thread(() -> {
            try {
                Call<Page> callSync = apiCalls.getPopularMovies(apiKey.getAPI_KEY());
                Response<Page> response = callSync.execute();

                if (response.body() != null) {
                    for (Movie movie : response.body().getResults()) {
                        movieList.add(movie);
                    }
                }
                latch.countDown();
            } catch (IOException e) {
                Log.d("Exception", e.toString());
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            Log.d("Exception", e.toString());
        }
        return movieList;
    }


    public Movie GetMovieDetails(String movieID) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Movie> movie = null;

        new Thread(() -> {
            try {
                Call<Movie> callSync = apiCalls.getMovieDetails(movieID, apiKey.getAPI_KEY());
                Response<Movie> response = callSync.execute();
                movie.set(response.body());
                latch.countDown();
            } catch (IOException e) {
                Log.d("Exception", e.toString());
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            Log.d("Exception", e.toString());
        }
        return null;
    }
}
