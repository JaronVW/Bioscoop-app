package com.example.bioscoopapp.Presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Domain.MovieDetail;
import com.example.bioscoopapp.Domain.Video;
import com.example.bioscoopapp.Logic.DataFormatter;
import com.example.bioscoopapp.Logic.LanguageManager;
import com.example.bioscoopapp.Logic.MovieDetailRepository;
import com.example.bioscoopapp.Logic.MovieVideosRepository;
import com.example.bioscoopapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName() + " DEBUG";

    private MovieDetailRepository detailsRepo;
    private MovieVideosRepository videosRepo;
    private MovieDetail movie;
    private Video video;
    private DataFormatter formatter;
    int movieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Getting the movieID from the intent...
        Movie selectedMovie = getIntent().getExtras().getParcelable("selectedMovie");
        movieID = selectedMovie.getMovieID();

        //Creating a SharedPreferences object and getting the previous language...
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String langCode = sharedPreferences.getString("LanguageKey", "No previous language.");

        //Creating a MovieDetailRepository and using it to get a MovieDetails object with the movieID...
        this.detailsRepo = new MovieDetailRepository();
        this.movie = this.detailsRepo.getMovieDetails(String.valueOf(movieID), langCode);

        //Retrieving language from previous session...
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateResource(String.valueOf(langCode));

        //Creating a MovieVideosRepository and using it to get a video object with the movieID...
        this.videosRepo = new MovieVideosRepository();
        List<Video> videosList = videosRepo.getMovieVideos(String.valueOf(movieID));
        int count = -1;
        for (Video v : videosList) {
            count++;
            if (v.getName().contains("Trailer")) {
                this.video = videosList.get(count);
                break;
            }
        }

        //Getting the video player and assigning it to a variable...
        YouTubePlayerView youTubePlayerView = findViewById(R.id.movie_details_youtubePlayer);
        getLifecycle().addObserver(youTubePlayerView);

        //Overriding the player and feeding it the videoID of the movie trailer...
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youtubePlayer) {
                String videoId = video.getKey();
                Log.d(LOG_TAG, "Movie url: " + videoId);
                if (videoId != null) {
                    youtubePlayer.cueVideo(videoId, 0);
                }
            }
        });

        //Instantiating formatter...
        this.formatter = new DataFormatter();

        //Logging the selected movie's title and movieID...
        Log.d(LOG_TAG, "Getting information on " + this.movie.getTitle() + " with movieID " + movieID);

        //Setting poster image using Picasso...
        ImageView image = findViewById(R.id.movie_details_poster);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/" +
                this.movie.getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(image);

        //Putting the rest of the text into the corresponding TextViews...
        TextView title = findViewById(R.id.movie_details_title);
        title.setText(this.movie.getTitle());

        TextView languages = findViewById(R.id.movie_details_languages);
        languages.setText(this.formatter.getFormattedSpokenLanguages(this.movie.getSpokenLanguages()));

        TextView runtime = findViewById(R.id.movie_details_runtime);
        runtime.setText(String.valueOf(this.formatter.getMinutesToText(this.movie.getRuntime(), this)));

        TextView releaseDate = findViewById(R.id.movie_details_release_date);
        releaseDate.setText(this.movie.getReleaseDate());

        TextView tagline = findViewById(R.id.movie_details_tagline);
        tagline.setText(String.valueOf(this.movie.getTagline()));

        TextView description = findViewById(R.id.movie_details_description);
        description.setText(String.valueOf(this.movie.getOverview()));

        TextView budget = findViewById(R.id.movie_details_budget);
        budget.setText(String.format("$%s", this.movie.getBudget()));

        TextView popularity = findViewById(R.id.movie_details_popularity);
        popularity.setText(String.valueOf(this.movie.getPopularity()));

        TextView voteCount = findViewById(R.id.movie_details_voteCount);
        voteCount.setText(String.valueOf(this.movie.getVoteCount()));

        TextView voteAverage = findViewById(R.id.movie_details_voteAverage);
        voteAverage.setText(String.valueOf(this.movie.getVoteAverage()));

        Button addToList = findViewById(R.id.add_movie_to_list_button);
        addToList.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddMovieToListActivity.class);
            intent.putExtra("selectedMovie", selectedMovie);
            startActivity(intent);

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_detail_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         if(item.getItemId() == R.id.share_item)
                startActivity(Intent.createChooser(shareMovie(), "List link"));
        return super.onOptionsItemSelected(item);
    }

    public Intent shareMovie(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/movie/"+movieID);
        intent.setType("text/plain");
        return intent;
    }

}