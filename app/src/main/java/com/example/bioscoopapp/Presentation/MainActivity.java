package com.example.bioscoopapp.Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName() + " DEBUG";

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
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
        finalMovies = movies;

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
                    adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByDateDESC(movies), MainActivity.this);
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

        TextView filter = (TextView) findViewById(R.id.movies_filter_icon);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonShowPopupWindowClick(view);

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
                    adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Movie>) new MovieManager().sortMoviesByRatingDESC(movies), MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    iscAsc = true;
                }
            }
        });

        clearSortingOptionsButton.setOnClickListener(view -> {
            try {
                adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Movie>) repo.GetPopularMoviesFromAPI(langCode), MainActivity.this);
                movies = finalMovies;
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Movie>) repo.GetPopularMoviesFromDB(), MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filterRecyclerView() {
        MoviePopUpActivity moviePopUpActivity = new MoviePopUpActivity(movies, MainActivity.this);
        ArrayList<Movie> list = (ArrayList<Movie>) new MovieManager().dateFilter(movies, "2021");
        movies = list;
        adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Movie>) list, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filterButtonOnClick(View view) {
//        String date = new MoviePopUpActivity(movies, this).getDate();
        filterRecyclerView();

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
        getMenuInflater().inflate(R.menu.menu, menu);
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

    public void filterRecyclerView(MovieAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        // show the popup window and choose the location
        popupWindow.showAtLocation(view, Gravity.TOP, -90, 200);
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        boolean isDark = sharedPreferences.getBoolean("IsDark", false);
        if (isDark) {
            popupView.setBackgroundColor(getResources().getColor(R.color.black));
        } else {
            popupView.setBackgroundColor(getResources().getColor(R.color.white));
        }
        Spinner spinner = findViewById(R.id.spinner_date);
        if (spinner != null) {
            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        }
        // Create an ArrayAdapter using the string array and default spinner
        // layout.

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.languages_array,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });


    }
}