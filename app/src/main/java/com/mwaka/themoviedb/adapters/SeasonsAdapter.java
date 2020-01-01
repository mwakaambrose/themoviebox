package com.mwaka.themoviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mwaka.themoviedb.R;
import com.mwaka.themoviedb.constants.URLs;
import com.mwaka.themoviedb.models.Season;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.SeasonViewHolder> {
    private List<Season> seasons;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Season season, View v);
    }

    public SeasonsAdapter(List<Season> seasons, Context context, OnItemClickListener listener) {
        this.seasons = seasons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeasonsAdapter.SeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_seasons, parent, false);
        return new SeasonsAdapter.SeasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonsAdapter.SeasonViewHolder holder, int position) {
        String image_url = URLs.IMAGE_BASE + seasons.get(position).getPosterPath();
        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_movie_black_24dp)
                .error(R.drawable.ic_movie_black_24dp)
                .into(holder.poster_image);

        holder.bind(seasons.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return seasons.size();
    }

    class SeasonViewHolder extends RecyclerView.ViewHolder {

        TextView name, air_date, season_number, episode_count;
        ImageView poster_image;

        SeasonViewHolder(@NonNull View itemView) {
            super(itemView);
            poster_image = itemView.findViewById(R.id.poster_image);
            air_date = itemView.findViewById(R.id.air_date);
            season_number = itemView.findViewById(R.id.season_number);
            episode_count = itemView.findViewById(R.id.episode_count);
            name = itemView.findViewById(R.id.name);
        }

        void bind(final Season item, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(item, v));
        }
    }
}
