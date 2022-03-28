package com.example.biocsoopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.biocsoopapp.API.APIConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APIConnection apiConnection = new APIConnection();
        apiConnection.getPopularMovies();
    }
}