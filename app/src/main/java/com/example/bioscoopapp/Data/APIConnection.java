package com.example.bioscoopapp.Data;


import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.example.bioscoopapp.Domain.Account;
import com.example.bioscoopapp.Domain.MediaID;
import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Domain.MovieDetail;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.MovieListCreator;
import com.example.bioscoopapp.Domain.MovieListPage;
import com.example.bioscoopapp.Domain.MovieListResponse;
import com.example.bioscoopapp.Domain.MoviePostToListPostResponse;
import com.example.bioscoopapp.Domain.Page;
import com.example.bioscoopapp.Domain.VideoResult;
import com.example.bioscoopapp.Presentation.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIConnection  {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName() + " DEBUG";

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

    public java.util.List getPopularMovies(String langCode) {
        CountDownLatch latch = new CountDownLatch(1);
        ArrayList<Movie> movieList = new ArrayList<>();
        // list that contains the movies and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<Page> callSync = apiCalls.getPopularMovies(apiKey.getAPI_KEY(), langCode);
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

    public MovieDetail GetMovieDetails(String movieID, String langCode) {
        CountDownLatch latch = new CountDownLatch(1);
        final MovieDetail[] movie = new MovieDetail[1];
        // array that contains the movie and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<MovieDetail> callSync = apiCalls.getMovieDetails(movieID, apiKey.getAPI_KEY(), langCode);
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

    public java.util.List getMovieVideos(String movieID) {
        CountDownLatch latch = new CountDownLatch(1);
        java.util.List videoList = new ArrayList<>();
        // array that contains the videos and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<VideoResult> callSync = apiCalls.getMovieVideo(movieID, apiKey.getAPI_KEY());
                Response<VideoResult> response = callSync.execute();
                System.out.println(response);
                if (response.body() != null) {
                    videoList.addAll(response.body().getResults());
                }

                latch.countDown();
            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }
        }).start();
        // api fetch gets executed in new thread
        try {
            latch.await();
            return videoList;
            // returns object after countdown has been called
        } catch (InterruptedException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }







    public MovieList getMovieList(int list_id) {
        CountDownLatch latch = new CountDownLatch(1);
        final MovieList[] movieLists = new MovieList[1];
        // array that contains the movieLists and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<MovieList> callSync = apiCalls.getMovieList(list_id,apiKey.getAPI_KEY());
                Response<MovieList> response = callSync.execute();
                System.out.println(response);
                movieLists[0] = response.body();
                latch.countDown();

            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }
        }).start();
        // api fetch gets executed in new thread
        try {
            latch.await();
            return movieLists[0];
            // returns object after countdown has been called
        } catch (InterruptedException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }

    public Account getAccount(){
        CountDownLatch latch = new CountDownLatch(1);
        final Account[] account = new Account[1];
        // array that contains the movie and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<Account> callSync = apiCalls.getAccount(apiKey.getAPI_KEY(),apiKey.getSession_ID());
                Response<Account> response = callSync.execute();
                account[0] = response.body();
                latch.countDown();

            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }
        }).start();
        // api fetch gets executed in new thread
        try {
            latch.await();
            return account[0];
            // returns object after countdown has been called
        } catch (InterruptedException e) {
            Log.d(TAG, e.toString());
            return null;
        }
    }

    public List<MovieList> getAccountLists(int account_id) {
        CountDownLatch latch = new CountDownLatch(1);
        List<MovieList> movielists = new ArrayList<>();
        // list that contains the movies and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<MovieListPage> callSync = apiCalls.getAccountLists( account_id,apiKey.getAPI_KEY(),apiKey.getSession_ID());
                Response<MovieListPage> response = callSync.execute();

                if (response.body() != null) {
                    movielists.addAll(response.body().getResults());
                }
                latch.countDown();
            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }
        }).start();
        // api fetch gets executed in new thread
        try {
            latch.await();
            return movielists;
            // returns list after countdown has been called
        } catch (InterruptedException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }


    public void addMovieToList(int list_id, MediaID mediaID) {
        Call<MoviePostToListPostResponse> call = apiCalls.postToList(list_id,apiKey.getAPI_KEY(),apiKey.getSession_ID(),mediaID);
        call.enqueue(new Callback<MoviePostToListPostResponse>() {
            @Override
            public void onResponse(Call<MoviePostToListPostResponse> call, Response<MoviePostToListPostResponse> response) {
                System.out.println(response.body().getStatusMessage());
            }

            @Override
            public void onFailure(Call<MoviePostToListPostResponse> call, Throwable t) {

            }
        });
    }

    public void deleteMovieFromList(int list_id, MediaID mediaID) {
        Call<MoviePostToListPostResponse> call = apiCalls.DeleteFromList(list_id,apiKey.getAPI_KEY(),apiKey.getSession_ID(),mediaID);
        call.enqueue(new Callback<MoviePostToListPostResponse>() {
            @Override
            public void onResponse(Call<MoviePostToListPostResponse> call, Response<MoviePostToListPostResponse> response) {
                System.out.println(response.body().getStatusMessage());
            }

            @Override
            public void onFailure(Call<MoviePostToListPostResponse> call, Throwable t) {

            }
        });
    }

    public void addList(MovieListCreator movieList) {
        Call<MovieListResponse> call = apiCalls.postMovieList(apiKey.getAPI_KEY(),apiKey.getSession_ID(),movieList);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                System.out.println(response.body().getListId());
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    public MovieList getMovieListDetails(int list_id, String langCode) {
        CountDownLatch latch = new CountDownLatch(1);
        final MovieList[] movieList = new MovieList[1];
        // array that contains the movieList and countdown latch used to wait for the thread to finish
        new Thread(() -> {
            try {
                Call<MovieList> callSync = apiCalls.getMovieListDetails(list_id, apiKey.getAPI_KEY(), langCode);
                Response<MovieList> response = callSync.execute();
                movieList[0] = response.body();
                latch.countDown();

            } catch (IOException e) {
                System.out.println(e.toString());
                Log.d(TAG, e.toString());
            }
        }).start();
        // api fetch gets executed in new thread
        try {
            latch.await();
            return movieList[0];
            // returns object after countdown has been called
        } catch (InterruptedException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }

    public void deleteMovieList(int list_id) {
        Call<MoviePostToListPostResponse> call = apiCalls.deleteList(list_id,apiKey.getAPI_KEY(),apiKey.getSession_ID());
        call.enqueue(new Callback<MoviePostToListPostResponse>() {
            @Override
            public void onResponse(Call<MoviePostToListPostResponse> call, Response<MoviePostToListPostResponse> response) {
                if (response.body() != null) {
                    System.out.println(response.body().getStatusMessage());
                }
            }
            @Override
            public void onFailure(Call<MoviePostToListPostResponse> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
    }
}
