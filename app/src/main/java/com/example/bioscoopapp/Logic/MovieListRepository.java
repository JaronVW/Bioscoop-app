package com.example.bioscoopapp.Logic;

import android.content.Context;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Data.DatabaseClient;
import com.example.bioscoopapp.Data.MovieDAO;
import com.example.bioscoopapp.Data.MovieListDAO;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.MovieListCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MovieListRepository {
    private final APIConnection apiConnection;
    private final MovieListDAO movieListDAO;
    private final Context context;

    public MovieListRepository(Context context) {
        this.context = context;
        this.apiConnection = new APIConnection();
        movieListDAO = DatabaseClient.getInstance(this.context).getAppDatabase().movieListDAO();
    }

    public int addMovieListToAPI(MovieListCreator movieList) {
        return apiConnection.addList(movieList);
    }

    public MovieList getMovieListFromAPI(int list_id) {
        return apiConnection.GetMovieList(list_id);
    }

    public boolean addMovieToDB(MovieList movieList) {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            movieListDAO.insertAll(movieList);
            latch.countDown();
        }).start();
        try {
            latch.await();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<MovieList> getMovieListsFromDB() {
        CountDownLatch latch = new CountDownLatch(1);
        List<MovieList> list = new ArrayList<>();
        new Thread(() -> {
            list.addAll(movieListDAO.getAll());
            latch.countDown();
        }).start();
        try {
            latch.await();
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void addMovieListToApp(MovieListCreator movieList) {
        this.addMovieToDB(
                this.getMovieListFromAPI(this.addMovieListToAPI(movieList))
        );
    }
}
