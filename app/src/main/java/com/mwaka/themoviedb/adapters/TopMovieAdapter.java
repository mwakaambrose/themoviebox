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
import com.mwaka.themoviedb.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopMovieAdapter extends RecyclerView.Adapter<TopMovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";

    public TopMovieAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView movieImage;
        RatingBar rating_bar;

        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = v.findViewById(R.id.movies_layout);
            movieImage = v.findViewById(R.id.movie_image);
            movieTitle = v.findViewById(R.id.title);
            data = v.findViewById(R.id.date);
            movieDescription = v.findViewById(R.id.description);
            rating = v.findViewById(R.id.rating);
            rating_bar = v.findViewById(R.id.rating_bar);
        }
    }

    @Override
    public TopMovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        String image_url = IMAGE_URL_BASE_PATH + movies.get(position).getPosterPath();
        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_movie_black_24dp)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.movieImage);
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());

        float rating = Float.parseFloat(movies.get(position).getVoteAverage().toString());

        holder.rating_bar.setRating(rating);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
