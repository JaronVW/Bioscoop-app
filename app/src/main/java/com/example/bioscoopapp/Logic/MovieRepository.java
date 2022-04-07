package com.example.bioscoopapp.Logic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Data.DatabaseClient;
import com.example.bioscoopapp.Data.MovieDAO;
import com.example.bioscoopapp.Domain.Movie;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MovieRepository {

    private final APIConnection apiConnection;
    private final MovieDAO movieDAO;
    private final Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo netWorkInfo;

    public MovieRepository(Context context) {
        this.context = context;
        this.apiConnection = new APIConnection();
        movieDAO = DatabaseClient.getInstance(this.context).getAppDatabase().movieDAO();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        netWorkInfo= connectivityManager.getActiveNetworkInfo();
    }

    public List<Movie> GetPopularMoviesFromAPI(String langCode) {
        if (netWorkInfo == null)
            return apiConnection.getPopularMovies(langCode);
        return null;
    }

    public List<Movie> GetPopularMoviesFromDB() {
        CountDownLatch latch = new CountDownLatch(1);
        List<Movie> list = new ArrayList<>();
        new Thread(() -> {
            list.addAll(movieDAO.getAll());
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

    public List<Movie> GetMovieListFromAPI(int list_id,String langcode) {
        if (netWorkInfo != null)
            return apiConnection.getMovieListDetails(list_id,langcode).getItems();
        return null;
    }

    public void insertAllMoviesInDB(List<Movie> movies) {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            for (Movie movie : movies) {
                movieDAO.insertAll(movie);
            }
            latch.countDown();
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void DeleteMovie(Movie movie) {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            movieDAO.delete(movie);
            latch.countDown();
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            Log.d("TEST", e.toString());
        }
    }

    private void CleanDB() {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            movieDAO.CleanDB();
            latch.countDown();
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            Log.d("TEST", e.toString());
        }
    }


    public List<Movie> GetSynchronisedMovies(String langCode) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netWorkInfo = connectivityManager.getActiveNetworkInfo();

        if (netWorkInfo == null)
            return this.GetPopularMoviesFromDB();
        else
            try {
                List<Movie> list = apiConnection.getPopularMovies(langCode);
                CleanDB();
                insertAllMoviesInDB(list);
                Toast toast = Toast.makeText(context,"Movies stored to local storage.", Toast.LENGTH_SHORT);
                toast.show();
                return list;
            } catch (Exception e) {
                Log.d("TEST", e.toString());
                return null;
            }
    }


}
