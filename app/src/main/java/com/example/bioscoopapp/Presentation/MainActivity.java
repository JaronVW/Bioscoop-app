package com.example.bioscoopapp.Presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Data.RecyclerViewInterface;
import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Domain.RequestToken;
import com.example.bioscoopapp.Logic.MovieManager;
import com.example.bioscoopapp.Logic.MovieRepository;
import com.example.bioscoopapp.R;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


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
        this.movies = (ArrayList<Movie>) this.repo.GetSynchronisedMovies();



        //Storing list of movies inside recyclerview...
        this.recyclerView = findViewById(R.id.movies_recyclerview);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        this.adapter = new MovieAdapter(this, movies, this);
        this.recyclerView.setAdapter(this.adapter);

        Toast toast = Toast.makeText(this, this.movies.size() + " movies loaded.", Toast.LENGTH_SHORT);
        toast.show();

        Log.d(LOG_TAG, "List of movies opened!");
        APIConnection apiConnection = new APIConnection();
        MovieList movieList = new MovieList();
        movieList.setName("Test");
        movieList.setDescription("Test");
        movieList.setName("en");
        apiConnection.addList(movieList);
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