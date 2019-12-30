package com.mwaka.themoviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mwaka.themoviedb.R;
import com.mwaka.themoviedb.constants.URLs;
import com.mwaka.themoviedb.models.Cast;
import com.mwaka.themoviedb.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private List<Cast> castList;
    private Context context;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Cast cast);
    }

    public CastAdapter(List<Cast> castList, Context context, OnItemClickListener listener) {
        this.castList = castList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.list_item_cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Picasso.get()
                .load(URLs.IMAGE_BASE+castList.get(position).getProfilePath())
                .placeholder(R.drawable.headshot)
                .error(R.drawable.headshot)
                .into(holder.cast_holder);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {

        ImageView cast_holder;

        CastViewHolder(@NonNull View itemView) {
            super(itemView);
            cast_holder = itemView.findViewById(R.id.cast_holder);
        }

        public void bind(final Cast cast, final CastAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(cast);
                }
            });
        }
    }
}