package com.mwaka.themoviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mwaka.themoviedb.R;
import com.mwaka.themoviedb.models.Trending;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder> {

    private List<Trending> trendings;
    private int rowLayout;
    private Context context;
    private static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";

    public TrendingAdapter(List<Trending> trendings, int rowLayout, Context context) {
        this.trendings = trendings;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class TrendingViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout moviesLayout;
        TextView movieTitle;
        TextView rating;
        ImageView movieImage;
        RatingBar rating_bar;

        public TrendingViewHolder(View v) {
            super(v);
            moviesLayout = v.findViewById(R.id.movies_layout);
            movieImage = v.findViewById(R.id.movie_image);
            movieTitle = v.findViewById(R.id.title);
            rating = v.findViewById(R.id.rating);
            rating_bar = v.findViewById(R.id.rating_bar);
        }
    }

    @Override
    public TrendingAdapter.TrendingViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TrendingAdapter.TrendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrendingAdapter.TrendingViewHolder holder, final int position) {
        String image_url = IMAGE_URL_BASE_PATH + trendings.get(position).getPosterPath();
        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_movie_black_24dp)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.movieImage);

        String title = "NoNe";
        if (trendings.get(position).getTitle() != null){
            title = trendings.get(position).getTitle();
        } else {
            title = trendings.get(position).getName();
        }

        holder.movieTitle.setText(title);
        holder.rating.setText(trendings.get(position).getVoteAverage().toString());

        float rating = Float.parseFloat(trendings.get(position).getVoteAverage().toString());

        holder.rating_bar.setRating(rating);
    }

    @Override
    public int getItemCount() {
        return trendings.size();
    }

}
