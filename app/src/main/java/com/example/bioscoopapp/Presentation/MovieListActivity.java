package com.example.bioscoopapp.Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioscoopapp.Data.RecyclerViewInterface;
import com.example.bioscoopapp.Domain.MediaID;
import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Logic.LanguageManager;
import com.example.bioscoopapp.Logic.MovieListRepository;
import com.example.bioscoopapp.Logic.MovieManager;
import com.example.bioscoopapp.Logic.MovieRepository;
import com.example.bioscoopapp.R;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity implements RecyclerViewInterface {
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName() + " DEBUG";

    private RecyclerView recyclerView;
    private MovieCustomListAdapter adapter;
    private ArrayList<Movie> movies;
    private ArrayList<Movie> finalMovies;
    private MovieRepository repo;
    private int count = 0;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_movies);


        //Creating a SharedPreferences object and getting the previous language...
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String langCode = sharedPreferences.getString("LanguageKey", "No previous language.");

        //Getting list of movies...
        this.repo = new MovieRepository(getApplicationContext());
        this.movies = (ArrayList<Movie>) this.repo.GetSynchronisedMovies(langCode);
        ArrayList<Movie> finalMovies = new ArrayList<>(movies);


        //Retrieving language from previous session...
        Log.d(LOG_TAG, langCode);
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateResource(String.valueOf(langCode));

        //Storing list of movies inside recyclerview...
        this.recyclerView = findViewById(R.id.movies_recyclerview);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        this.adapter = new MovieCustomListAdapter(this, movies, this);
        this.recyclerView.setAdapter(this.adapter);

        Toast toast = Toast.makeText(this, this.movies.size() + " movies loaded.", Toast.LENGTH_SHORT);
        toast.show();

        Log.d(LOG_TAG, "List of movies opened!");
        //Listen to changes in the edittext field
        EditText editText = findViewById(R.id.search_movies);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable s) {
                adapter = new MovieCustomListAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().searchFilter(movies, s.toString()), MovieListActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });

    }


    @Override
    public void onItemClick(int position) {
        Log.d(LOG_TAG, "Movie clicked!");
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("MovieID", this.movies.get(position).getMovieID());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}