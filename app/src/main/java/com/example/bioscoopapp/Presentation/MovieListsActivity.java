package com.example.bioscoopapp.Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Data.RecyclerViewInterface;
import com.example.bioscoopapp.Domain.Account;
import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Logic.LanguageManager;
import com.example.bioscoopapp.Logic.MovieListRepository;
import com.example.bioscoopapp.Logic.MovieRepository;
import com.example.bioscoopapp.R;

import java.util.ArrayList;


public class MovieListsActivity extends AppCompatActivity implements RecyclerViewInterface {

    private static final String LOG_TAG =
            MovieListsActivity.class.getSimpleName() + " DEBUG";

    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private ArrayList<MovieList> MovieLists;
    private MovieListRepository repo;
    private int count = 0;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_movielists);


        //Creating a SharedPreferences object and getting the previous language...
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String langCode = sharedPreferences.getString("LanguageKey", "No previous language.");

        //Getting list of movies...
        this.repo = new MovieListRepository(getApplicationContext());
        Account account = new APIConnection().getAccount();
        this.MovieLists = (ArrayList<MovieList>) this.repo.getMovieListFromAccount(account.getId());


        //Retrieving language from previous session...
        Log.d(LOG_TAG, langCode);
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateResource(String.valueOf(langCode));

        //Storing list of movies inside recyclerview...
        this.recyclerView = findViewById(R.id.movies_recyclerview);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        this.adapter = new MovieListAdapter(this, MovieLists, this);
        this.recyclerView.setAdapter(this.adapter);

        Toast toast = Toast.makeText(this, this.MovieLists.size() + " movies loaded.", Toast.LENGTH_SHORT);
        toast.show();

    }




    @Override
    public void onItemClick(int position) {
        Log.d(LOG_TAG, "Movie clicked!");
        Intent intent = new Intent(this, MovieListActivity.class);
        intent.putExtra("MovieID", this.MovieLists.get(position).getId());
        startActivity(intent);
    }


}