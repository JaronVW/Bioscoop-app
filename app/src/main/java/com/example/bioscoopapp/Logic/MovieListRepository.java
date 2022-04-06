package com.example.bioscoopapp.Logic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Data.DatabaseClient;
import com.example.bioscoopapp.Data.MovieListDAO;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.MovieListCreator;

import java.util.concurrent.CountDownLatch;

public class MovieListRepository {
    private final APIConnection apiConnection;
    private final MovieListDAO movieListDAO;
    private final Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo netWorkInfo;

    public MovieListRepository( Context context) {
        this.context = context;
        this.apiConnection = new APIConnection();
        movieListDAO = DatabaseClient.getInstance(this.context).getAppDatabase().movieListDAO();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        netWorkInfo= connectivityManager.getActiveNetworkInfo();
    }

    public void insertMovieListToAPI(MovieListCreator movieListCreator){
        apiConnection.addList(movieListCreator);
    }

    public MovieList getMovieListFromAPI(int list_id){
        return apiConnection.getMovieList(list_id);
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
            System.out.println(e.toString());
            return false;
        }
    }



}
