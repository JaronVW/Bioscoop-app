package com.example.bioscoopapp.Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bioscoopapp.Domain.MovieListCreator;
import com.example.bioscoopapp.Logic.LanguageManager;
import com.example.bioscoopapp.Logic.MovieListRepository;
import com.example.bioscoopapp.R;

import java.util.Objects;

public class AddMovieListActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        String TAG = getClass().getSimpleName();


        //Creating a SharedPreferences object and getting the previous language...
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String langCode = sharedPreferences.getString("LanguageKey", "No previous language.");

        Log.d(TAG, langCode);
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateResource(String.valueOf(langCode));

        MovieListRepository movieListRepository = new MovieListRepository(getApplicationContext());
        EditText listName = findViewById(R.id.movie_list_title_field);
        EditText listDescription = findViewById(R.id.movie_list_description_field);
        Button button = findViewById(R.id.create_list_button);
        //Retrieving language from previous session...
        button.setOnClickListener(view -> {
            movieListRepository.insertMovieListToAPI(new MovieListCreator(
                    listName.getText().toString(),
                    listDescription.getText().toString(),
                    langCode

            ));
            Intent intent = new Intent(this, MovieListsActivity.class);
            startActivity(intent);
        });


    }
}
