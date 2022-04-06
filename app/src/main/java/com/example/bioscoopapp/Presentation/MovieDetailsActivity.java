package com.example.bioscoopapp.Presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bioscoopapp.Domain.MovieDetail;
import com.example.bioscoopapp.Domain.Video;
import com.example.bioscoopapp.Domain.VideoResult;
import com.example.bioscoopapp.Logic.DataFormatter;
import com.example.bioscoopapp.Logic.MovieDetailRepository;
import com.example.bioscoopapp.Logic.MovieRepository;
import com.example.bioscoopapp.Logic.MovieVideosRepository;
import com.example.bioscoopapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName() + " DEBUG";

    private MovieDetailRepository detailsRepo;
    private MovieVideosRepository videosRepo;
    private MovieDetail movie;
    private Video video;
    private DataFormatter formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Getting the ID from the intent...
        int ID = getIntent().getExtras().getInt("MovieID");

        //Creating a MovieDetailRepository and using it to get a MovieDetails object with the ID...
        this.detailsRepo = new MovieDetailRepository();
        this.movie = this.detailsRepo.getMovieDetails(String.valueOf(ID));

        //Creating a MovieVideosRepository and using it to get a video object with the ID...
        this.videosRepo = new MovieVideosRepository();
        List<Video> videosList = videosRepo.getMovieVideos(String.valueOf(ID));
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

        //Logging the selected movie's title and ID...
        Log.d(LOG_TAG, "Getting information on " + this.movie.getTitle() + " with ID " + ID);

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
        runtime.setText(String.valueOf(this.formatter.getMinutesToText(this.movie.getRuntime())));

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

    }

}