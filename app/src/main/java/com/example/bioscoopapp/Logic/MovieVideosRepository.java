package com.example.bioscoopapp.Logic;

import com.example.bioscoopapp.Data.APIConnection;
import com.example.bioscoopapp.Domain.Video;
import com.example.bioscoopapp.Domain.VideoResult;

import java.util.List;

public class MovieVideosRepository {

    private final APIConnection apiConnection;

    public MovieVideosRepository() {
        this.apiConnection = new APIConnection();
    }

    public List<Video> getMovieVideos(String movieID){
        return apiConnection.getMovieVideos(movieID);
    }
}
