package com.example.bioscoopapp.Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Data.RecyclerViewInterface;
import com.example.bioscoopapp.Domain.MediaID;
import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Logic.LanguageManager;
import com.example.bioscoopapp.Logic.MovieListRepository;
import com.example.bioscoopapp.Logic.MovieRepository;
import com.example.bioscoopapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.IDN;
import java.util.ArrayList;
import java.util.Objects;

public class AddMovieToListActivity extends AppCompatActivity implements RecyclerViewInterface {

    private static final String LOG_TAG =
            MovieListsActivity.class.getSimpleName() + " DEBUG";

    private ArrayList<MovieList> MovieLists;
    private MovieListRepository repo;
    private int ID;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_movielists);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Movie passedMovie = getIntent().getExtras().getParcelable("selectedMovie");
        ID = passedMovie.getMovieID();


        //Creating a SharedPreferences object and getting the previous language...
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String langCode = sharedPreferences.getString("LanguageKey", "No previous language.");

        //Getting list of movies...
        this.repo = new MovieListRepository(getApplicationContext());
        this.MovieLists = (ArrayList<MovieList>) this.repo.getMovieListFromAccount(
                new APIConnection().getAccount().getId());


        //Retrieving language from previous session...
        Log.d(LOG_TAG, langCode);
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateResource(String.valueOf(langCode));

        //Storing list of movies inside recyclerview...
        RecyclerView recyclerView = findViewById(R.id.movies_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        MovieListAdapter adapter = new MovieListAdapter(this, MovieLists, this);
        recyclerView.setAdapter(adapter);

        Toast toast = Toast.makeText(this, this.MovieLists.size() + " list(s) found.", Toast.LENGTH_SHORT);
        toast.show();

        FloatingActionButton fab = findViewById(R.id.add_list_button);
        fab.setOnClickListener(view -> startActivity(new Intent(this,AddMovieListActivity.class)));

    }


    @Override
    public void onItemClick(int position) {
        repo.addMovieToMovieList(MovieLists.get(position).getId(),new MediaID(ID));
        Toast toast = Toast.makeText(this,"Movie added to list: "+ MovieLists.get(position).getName(), Toast.LENGTH_SHORT);
        toast.show();
        super.onBackPressed();
    }

}