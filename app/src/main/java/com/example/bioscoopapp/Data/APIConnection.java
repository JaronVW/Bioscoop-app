package com.example.bioscoopapp.Data;


import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Domain.MovieDetail;
import com.example.bioscoopapp.Domain.Page;
import com.example.bioscoopapp.Domain.RequestToken;
import com.example.bioscoopapp.Domain.SessionToken;

import retrofit2.Call;
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

    public RequestToken GetRequestToken() {
        CountDownLatch latch = new CountDownLatch(1);
        RequestToken[] requestToken = {null};
        // array that contains the request token and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<RequestToken> callSync = apiCalls.getRequestToken(apiKey.getAPI_KEY());
                Response<RequestToken> response = callSync.execute();
                System.out.println(response);
                requestToken[0] = response.body();
                latch.countDown();

            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }
        }).start();
        // api fetch gets executed in new thread
        try {
            latch.await();
            return requestToken[0];
            // returns object after countdown has been called
        } catch (InterruptedException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }

    public SessionToken GetSessionToken(RequestToken requestToken) {
        CountDownLatch latch = new CountDownLatch(1);
        final SessionToken[] sessionToken = new SessionToken[1];
        // array that contains the sessionToken and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<SessionToken> callSync = apiCalls.getSessionToken(apiKey.getAPI_KEY(),requestToken);
                Response<SessionToken> response = callSync.execute();
                System.out.println(response);
                sessionToken[0] = response.body();
                latch.countDown();

            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }
        }).start();
        // api fetch gets executed in new thread
        try {
            latch.await();
            return sessionToken[0];
            // returns object after countdown has been called
        } catch (InterruptedException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }


}
