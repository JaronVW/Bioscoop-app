package com.example.bioscoopapp.Logic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.bioscoopapp.Domain.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MovieManager {


    public List<Movie> sortMoviesByDateASC(List<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getReleaseDate));
        return movies;
    }


    public ArrayList<Movie> sortMoviesByDateDESC(ArrayList<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getReleaseDate));
        Collections.reverse(movies);
        return movies;
    }


    public List<Movie> sortMoviesByRatingASC(List<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getVoteAverage));
        return movies;
    }


    public ArrayList<Movie> sortMoviesByRatingDESC(ArrayList<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getVoteAverage));
        Collections.reverse(movies);
        return movies;
    }


    public List<Movie> sortMoviesByTitleASC(List<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle));
        return movies;
    }


    public List<Movie> sortMoviesByTitleDESC(ArrayList<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle));
        Collections.reverse(movies);
        return movies;
    }

    public List<Movie> searchFilter(List<Movie> movies, String searchQuery) {
        return movies.stream().filter(p -> p.getTitle().contains(searchQuery)).collect(Collectors.toList());
    }

}
