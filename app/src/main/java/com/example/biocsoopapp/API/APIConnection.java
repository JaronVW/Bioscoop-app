package com.example.biocsoopapp.API;


import android.util.Log;

import java.util.List;

import com.example.Domain.Movie;
import com.example.Domain.Page;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIConnection {

    private final APIKey apiKey;
    private final APICalls apiCalls;

    public APIConnection(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        apiCalls = retrofit.create(APICalls.class);
        apiKey = new APIKey();
    }



    public List<Movie> getPopularMovies(){
        apiCalls.popularMovies(apiKey.getAPI_KEY()).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                System.out.println(response.body().getResults().get(1));
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Log.d("Debug",t.toString());
            }

        });
       return null;
    }
}
