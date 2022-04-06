package com.example.bioscoopapp.Logic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Data.DatabaseClient;
import com.example.bioscoopapp.Data.MovieDAO;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.MovieListCreator;

import java.util.concurrent.CountDownLatch;

public class MovieListRepository {
    private final APIConnection apiConnection;
    private final MovieDAO movieDAO;
    private final Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo netWorkInfo;

    public MovieListRepository(Context context) {
        this.context = context;
        this.apiConnection = new APIConnection();
        movieDAO = DatabaseClient.getInstance(this.context).getAppDatabase().movieDAO();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        netWorkInfo= connectivityManager.getActiveNetworkInfo();
    }

    public int insertMovieList(MovieListCreator movieListCreator){
        CountDownLatch latch = new CountDownLatch(1);
        final int[] list_id = {0};
        new Thread(()->{

            list_id[0] = apiConnection.addList(movieListCreator);
            latch.countDown();
        }).start();
        try {
            latch.await();
            return list_id[0];
        } catch (InterruptedException e) {
            e.printStackTrace();
            return list_id[0];
        }

    }

    public MovieList getMovieListFromAPI(int list_id){
        return apiConnection.getMovieList()
    }


}
