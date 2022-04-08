package com.example.bioscoopapp.Presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioscoopapp.Data.RecyclerViewInterface;
import com.example.bioscoopapp.Domain.MediaID;
import com.example.bioscoopapp.Domain.MovieList;
import com.example.bioscoopapp.Logic.MovieListRepository;
import com.example.bioscoopapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieListAdapter extends
        RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {
    private final ArrayList<MovieList> movieLists;
    private final LayoutInflater layoutInflater;
    private final Context context;
    private final RecyclerViewInterface recyclerViewInterface;

    public MovieListAdapter(Context context, ArrayList<MovieList> movieLists, RecyclerViewInterface
            recyclerViewInterface) {
        this.movieLists = movieLists;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = layoutInflater.inflate(R.layout.movie_list, parent, false);
        return new MovieListViewHolder(mItemView, this.recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        holder.listTitle.setText(movieLists.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    private void removeAt(int position) {
        movieLists.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, movieLists.size());
        Toast toast = Toast.makeText(context,"list successfully deleted", Toast.LENGTH_SHORT);
        toast.show();
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder {
        public TextView listTitle;

        public MovieListViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.listTitle = itemView.findViewById(R.id.movie_list_title);

            itemView.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            });

            itemView.findViewById(R.id.remove_list_button)
                    .setOnClickListener(view -> {
                        int position = getBindingAdapterPosition();
                        int id = movieLists.get(getBindingAdapterPosition()).getId();
                        MovieListRepository movieListRepository = new MovieListRepository(context);
                        movieListRepository.deleteMovieList(id);
                        removeAt(getBindingAdapterPosition());
                    });
        }

    }

}
