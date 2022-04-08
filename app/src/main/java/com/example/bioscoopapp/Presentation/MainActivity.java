package com.example.bioscoopapp.Presentation;

import android.annotation.SuppressLint;
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
import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Logic.LanguageManager;
import com.example.bioscoopapp.Logic.MovieManager;
import com.example.bioscoopapp.Logic.MovieRepository;
import com.example.bioscoopapp.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName() + " DEBUG";

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies;
    private ArrayList<Movie> finalMovies;
    private MovieRepository repo;
    private final int count = 0;
    private String langCode;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_movies);


        //Creating a SharedPreferences object and getting the previous language...
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        langCode = sharedPreferences.getString("LanguageKey", "No previous language.");

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

        this.adapter = new MovieAdapter(this, movies, this);
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
                adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().searchFilter(movies, s.toString()), MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });
        Button searchFilter = findViewById(R.id.search_button);
        searchFilter.setOnClickListener(view -> {
            EditText dateBox = findViewById(R.id.date_picker);
            ArrayList<Movie> list = new ArrayList<>();
            if (!dateBox.getText().toString().equals("")) {
                String date = dateBox.getText().toString();
                list = (ArrayList<Movie>) new MovieManager().dateFilter(movies, date);
            }

            EditText ratingBox = findViewById(R.id.rating_picker);
            if (!ratingBox.getText().toString().equals("")) {
                String rating = ratingBox.getText().toString();
                list = (ArrayList<Movie>) new MovieManager().ratingFilter(movies, rating);
            }

            movies = list;
            adapter = new MovieAdapter(getApplicationContext(), list, MainActivity.this);
            recyclerView.setAdapter(adapter);
        });


        Button dateSortButton = findViewById(R.id.sortDate);
        Button titleSortButton = findViewById(R.id.sortTitle);
        Button ratingSortButton = findViewById(R.id.sortRating);
        Button clearSortingOptionsButton = findViewById(R.id.clearSortingOptionsButton);

        dateSortButton.setOnClickListener(new View.OnClickListener() {
            private boolean iscAsc;

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if (iscAsc) {
                    adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByDateASC(movies), MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    iscAsc = false;
                } else {
                    adapter = new MovieAdapter(getApplicationContext(), new MovieManager().sortMoviesByDateDESC(movies), MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    iscAsc = true;
                }
            }
        });

        titleSortButton.setOnClickListener(new View.OnClickListener() {
            private boolean iscAsc;


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if (iscAsc) {
                    adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByTitleASC(movies), MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    iscAsc = false;
                } else {
                    adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByTitleDESC(movies), MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    iscAsc = true;
                }
            }
        });

        ratingSortButton.setOnClickListener(new View.OnClickListener() {
            private boolean iscAsc;

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if (iscAsc) {
                    adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByRatingASC(movies), MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    iscAsc = false;
                } else {
                    adapter = new MovieAdapter(getApplicationContext(), new MovieManager().sortMoviesByRatingDESC(movies), MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    iscAsc = true;
                }
            }
        });

        clearSortingOptionsButton.setOnClickListener(view -> {
            adapter = new MovieAdapter(getApplicationContext(), finalMovies, MainActivity.this);
            movies = finalMovies;
            recyclerView.setAdapter(adapter);

        });

    }


    @Override
    public void onItemClick(int position) {
        Log.d(LOG_TAG, "Movie clicked!");
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("selectedMovie", this.movies.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_page_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.settings:
                intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);
                return true;
            case R.id.movieLists:
                intent = new Intent(this, MovieListsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}