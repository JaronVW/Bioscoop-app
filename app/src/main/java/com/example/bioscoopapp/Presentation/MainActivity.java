package com.example.bioscoopapp.Presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bioscoopapp.Data.RecyclerViewInterface;
import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Logic.MovieRepository;
import com.example.bioscoopapp.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName() + " DEBUG";

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies;
    private MovieRepository repo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_movies);

        //Getting list of movies...
        this.repo = new MovieRepository(getApplicationContext());
        this.movies = (ArrayList<Movie>) repo.GetSynchronisedMovies();


//        getApplicationContext().deleteDatabase("App_database");

        //Storing list of movies inside recyclerview...
        this.recyclerView = findViewById(R.id.movies_recyclerview);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        this.adapter = new MovieAdapter(this, movies, this);
        this.recyclerView.setAdapter(this.adapter);

        Toast toast = Toast.makeText(getApplicationContext(), this.movies.size() + " movies loaded.", Toast.LENGTH_SHORT);
        toast.show();

        Log.d(LOG_TAG, "List of movies opened!");
    }


    @Override
    public void onItemClick(int position) {
        Log.d(LOG_TAG, "Movie clicked!");
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("MovieID", this.movies.get(position).getMovieID());
        startActivity(intent);
    }
}