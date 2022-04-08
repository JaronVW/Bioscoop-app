package com.example.bioscoopapp.Logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Data.DatabaseClient;
import com.example.bioscoopapp.Data.MovieListDAO;
import com.example.bioscoopapp.Domain.MediaID;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.MovieListCreator;
import com.example.bioscoopapp.Presentation.MovieListsActivity;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MovieListRepository {

    private static final String LOG_TAG = "MovieListRepository DEBUG";

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

    public List<MovieList> getMovieListFromAccount(int account_id){
        return apiConnection.getAccountLists(account_id);
    }



    @SuppressLint("LongLogTag")
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
            Log.d(LOG_TAG, e.toString());
            return false;
        }
    }

    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean addMovieToDB(List<MovieList> movieLists) {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            movieLists.forEach(movieListDAO::insertAll);
            latch.countDown();
        }).start();
        try {
            latch.await();
            return true;
        } catch (InterruptedException e) {
            Log.d(LOG_TAG, e.toString());
            return false;
        }
    }

    public  void addMovieToMovieList(int list_id, MediaID mediaID){
        apiConnection.addMovieToList(list_id,mediaID);
    }

    public  void deleteMovieFromList(int list_id, MediaID mediaID){
        apiConnection.deleteMovieFromList(list_id,mediaID);
    }

    public void deleteMovieList(int list_id){
        apiConnection.deleteMovieList(list_id);
    }



}
