package com.example.bioscoopapp.Domain;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MovieGenres {
    @Embedded
    public Movie movie;
    @Relation(
            parentColumn = "id",
            entityColumn = "movieID"
    )
    public List<Genre> genres;
}
