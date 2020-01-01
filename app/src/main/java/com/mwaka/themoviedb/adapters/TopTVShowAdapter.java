package com.mwaka.themoviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mwaka.themoviedb.R;
import com.mwaka.themoviedb.constants.URLs;
import com.mwaka.themoviedb.models.Movie;
import com.mwaka.themoviedb.models.TVShow;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TopTVShowAdapter extends RecyclerView.Adapter<TopTVShowAdapter.TVShowViewHolder>  {

    private List<TVShow> tvShows;
    private int rowLayout;
    private Context context;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TVShow item);
    }

    public TopTVShowAdapter(List<TVShow> tvShows, int rowLayout, Context context, OnItemClickListener listener) {
        this.tvShows = tvShows;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener = listener;
    }

    //A view holder inner class where we get reference to the views in the layout using their ID
    static class TVShowViewHolder extends RecyclerView.ViewHolder {

        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView date;
        TextView movieDescription;
        TextView rating;
        ImageView movieImage;
        RatingBar rating_bar;

        TVShowViewHolder(View v) {
            super(v);
            v.setTag(this);
            moviesLayout = v.findViewById(R.id.movies_layout);
            movieImage = v.findViewById(R.id.movie_image);
            movieTitle = v.findViewById(R.id.title);
            date = v.findViewById(R.id.date);
            movieDescription = v.findViewById(R.id.description);
            rating = v.findViewById(R.id.rating);
            rating_bar = v.findViewById(R.id.rating_bar);
        }

        void bind(final TVShow item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @NotNull
    @Override
    public TopTVShowAdapter.TVShowViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TopTVShowAdapter.TVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopTVShowAdapter.TVShowViewHolder holder, final int position) {
        String image_url = URLs.IMAGE_BASE + tvShows.get(position).getPosterPath();
        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_movie_black_24dp)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.movieImage);
        holder.movieTitle.setText(tvShows.get(position).getName());
        holder.date.setText(tvShows.get(position).getReleaseDate());
        holder.movieDescription.setText(tvShows.get(position).getOverview());
        holder.rating.setText(tvShows.get(position).getVoteAverage().toString());

        float rating = Float.parseFloat(tvShows.get(position).getVoteAverage().toString());

        holder.rating_bar.setRating(rating);
        holder.bind(tvShows.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }
}
