package com.example.bioscoopapp.Presentation;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bioscoopapp.R;
import java.util.Objects;

public class AddMovieListActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


    }
}
