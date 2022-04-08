package com.example.bioscoopapp.Presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioscoopapp.Data.RecyclerViewInterface;
import com.example.bioscoopapp.Domain.MediaID;
import com.example.bioscoopapp.Domain.Movie;
import com.example.bioscoopapp.Logic.MovieListRepository;
import com.example.bioscoopapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieCustomListAdapter extends
        RecyclerView.Adapter<MovieCustomListAdapter.MovieViewHolder> {
    private final ArrayList<Movie> movies;
    private final LayoutInflater layoutInflater;
    private final Context context;
    private final RecyclerViewInterface recyclerViewInterface;
    private final int list_id;

    public MovieCustomListAdapter(Context context, ArrayList<Movie> movies, RecyclerViewInterface
            recyclerViewInterface, int list_id) {
        this.movies = movies;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
        this.list_id = list_id;
    }

    @NonNull
    @Override
    public MovieCustomListAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = layoutInflater.inflate(R.layout.movielist_item_custom_list, parent, false);
        return new MovieViewHolder(mItemView, this.recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCustomListAdapter.MovieViewHolder holder, int position) {
        Picasso.with(context).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/" +
                this.movies.get(position).getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(holder.movieImage);
        holder.movieTitle.setText(movies.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private void removeAt(int position) {
        movies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, movies.size());
        Toast toast = Toast.makeText(context,"item deleted from list", Toast.LENGTH_SHORT);
        toast.show();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;
        public TextView movieTitle;

        public MovieViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.movieImage = itemView.findViewById(R.id.movie_list_item_image);
            this.movieTitle = itemView.findViewById(R.id.movie_list_item_title);

            itemView.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            });

            itemView.findViewById(R.id.remove_from_list_button)
                    .setOnClickListener(view -> {
                        int position = getBindingAdapterPosition();
                        new MovieListRepository(context.getApplicationContext())
                                .deleteMovieFromList(list_id,
                                        new MediaID(
                                                movies.get(position).getMovieID()
                                        )
                                );
                        removeAt(position);

                    });

        }

    }

}
