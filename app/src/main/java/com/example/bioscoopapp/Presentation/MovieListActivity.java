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

public class MovieListActivity extends AppCompatActivity implements RecyclerViewInterface {
    private static final String LOG_TAG =
            MovieListActivity.class.getSimpleName() + " DEBUG";

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

        int ID = getIntent().getExtras().getInt("listID");
        System.out.println(ID);


        //Creating a SharedPreferences object and getting the previous language...
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String langCode = sharedPreferences.getString("LanguageKey", "No previous language.");

        //Getting list of movies...
        this.repo = new MovieRepository(getApplicationContext());
        try {
            this.movies = (ArrayList<Movie>) this.repo.GetMovieListFromAPI(ID,langCode);
            ArrayList<Movie> finalMovies = new ArrayList<>(movies);


            //Retrieving language from previous session...
            Log.d(LOG_TAG, langCode);
            LanguageManager languageManager = new LanguageManager(this);
            languageManager.updateResource(String.valueOf(langCode));

            //Storing list of movies inside recyclerview...
            this.recyclerView = findViewById(R.id.movies_recyclerview);
            this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

            this.adapter = new MovieCustomListAdapter(this, movies, this,ID);
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
                    adapter = new MovieCustomListAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().searchFilter(movies, s.toString()), MovieListActivity.this,ID);
                    recyclerView.setAdapter(adapter);
                }
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
                        adapter = new MovieCustomListAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByDateASC(movies), MovieListActivity.this,ID);
                        recyclerView.setAdapter(adapter);
                        iscAsc = false;
                    } else {
                        adapter = new MovieCustomListAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByDateDESC(movies), MovieListActivity.this,ID);
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
                        adapter = new MovieCustomListAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByTitleASC(movies), MovieListActivity.this,ID);
                        recyclerView.setAdapter(adapter);
                        iscAsc = false;
                    } else {
                        adapter = new MovieCustomListAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByTitleDESC(movies), MovieListActivity.this,ID);
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
                        adapter = new MovieCustomListAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByRatingASC(movies), MovieListActivity.this,ID);
                        recyclerView.setAdapter(adapter);
                        iscAsc = false;
                    } else {
                        adapter = new MovieCustomListAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByRatingDESC(movies), MovieListActivity.this,ID);
                        recyclerView.setAdapter(adapter);
                        iscAsc = true;
                    }
                }
            });

            clearSortingOptionsButton.setOnClickListener(view -> {
                adapter = new MovieCustomListAdapter(getApplicationContext(), finalMovies, MovieListActivity.this,ID);
                recyclerView.setAdapter(adapter);
            });

        } catch (NullPointerException e) {
            System.out.println("Leeg!");
        }
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
        getMenuInflater().inflate(R.menu.custom_list_page_menu, menu);
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
}