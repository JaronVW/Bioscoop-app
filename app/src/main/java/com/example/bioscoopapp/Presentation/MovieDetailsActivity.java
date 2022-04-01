package com.example.bioscoopapp.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Domain.MovieDetail;
import com.example.bioscoopapp.Logic.MovieDetailRepository;
import com.example.bioscoopapp.R;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName() + " DEBUG";

    private MovieDetailRepository repo;
    private MovieDetail movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        int ID = getIntent().getExtras().getInt("MovieID");
        Log.d(LOG_TAG, "Getting information on movie with ID " + ID);

        this.repo = new MovieDetailRepository();
        this.movie = repo.getMovieDetails(String.valueOf(ID));

        Log.d(LOG_TAG, this.movie.getTitle());
    }
}