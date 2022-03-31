package com.example.bioscoopapp.Logic;

import android.content.Context;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Data.DatabaseClient;
import com.example.bioscoopapp.Domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MovieRepository {
    private final APIConnection apiConnection;

    public MovieRepository() {
        this.apiConnection = new APIConnection();
    }

    public List<Movie> GetPopularMoviesFromAPI() {
        return apiConnection.getPopularMovies();
    }

    public List<Movie> GetPopularMoviesFromDB(Context context) {
        CountDownLatch latch = new CountDownLatch(1);
        List<Movie> list = new ArrayList<>();
        new Thread(() -> {
            list.addAll(DatabaseClient.getInstance(context).getAppDatabase().movieDAO().getAll());
            latch.countDown();
        }).start();
        return list;
    }
}
