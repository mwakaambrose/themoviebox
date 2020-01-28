package com.mwaka.themoviedb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mwaka.themoviedb.R;
import com.mwaka.themoviedb.constants.URLs;
import com.mwaka.themoviedb.models.Movie;
import com.mwaka.themoviedb.models.TVShow;
import com.mwaka.themoviedb.screens.TV;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVViewHolder> {

    List<TVShow> movieList;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TVShow tvShow, View v);
    }

    public TVAdapter(List<TVShow> movieList, OnItemClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie_vertical, parent, false);
        return new TVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVViewHolder holder, int position) {
        String image_url = URLs.IMAGE_BASE + movieList.get(position).getPosterPath();
        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_movie_black_24dp)
                .error(R.drawable.ic_movie_black_24dp)
                .into(holder.poster_image);

        holder.bind(movieList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class TVViewHolder extends RecyclerView.ViewHolder {

        ImageView poster_image;

        TVViewHolder(@NonNull View itemView) {
            super(itemView);
            poster_image = itemView.findViewById(R.id.poster_image);
        }

        void bind(final TVShow item, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(item, v));
        }
    }
}
