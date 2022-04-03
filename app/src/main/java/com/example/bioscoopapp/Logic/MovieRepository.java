package com.example.bioscoopapp.Logic;

import android.content.Context;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Data.DatabaseClient;
import com.example.bioscoopapp.Data.MovieDAO;
import com.example.bioscoopapp.Domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MovieRepository {

    private final APIConnection apiConnection;
    private final MovieDAO movieDAO;

    public MovieRepository(Context context) {
        this.apiConnection = new APIConnection();
        movieDAO = DatabaseClient.getInstance(context).getAppDatabase().movieDAO();
    }

    public List<Movie> GetPopularMoviesFromAPI() {
        return apiConnection.getPopularMovies();
    }

    public List<Movie> GetPopularMoviesFromDB() {
        CountDownLatch latch = new CountDownLatch(1);
        List<Movie> list = new ArrayList<>();
        new Thread(() -> {
            list.addAll(movieDAO.getAll());
            latch.countDown();
        }).start();
        return list;
    }

    public boolean isTableEmpty() {
        CountDownLatch latch = new CountDownLatch(1);
        boolean[] isEmpty = {true};
        new Thread(() -> {
            isEmpty[0] = !movieDAO.isTableEmpty();
            latch.countDown();
        }).start();
        return isEmpty[0];
    }

    public void insertAllMovies(List<Movie> movies) {
        new Thread(() ->{
            for (Movie movie : movies) {
                movieDAO.insertAll(movie);
            }
        }).start();
    }

    public void deleteAllMovies(List<Movie> movies) {
        new Thread(() ->{
            for (Movie movie : movies) {
                movieDAO.delete(movie);
            }
        }).start();
    }


}
