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
import com.mwaka.themoviedb.models.Cast;
import com.mwaka.themoviedb.models.Crew;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {
    
    private List<Crew> crewList;
    private Context context;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Crew crew);
    }

    public CrewAdapter(List<Crew> crewList, Context context, OnItemClickListener listener) {
        this.crewList = crewList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CrewAdapter.CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.list_item_crew, parent, false);
        return new CrewAdapter.CrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewAdapter.CrewViewHolder holder, int position) {
        holder.name.setText(crewList.get(position).getName());
        holder.job.setText(crewList.get(position).getJob());
        holder.department.setText(crewList.get(position).getDepartment());
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public class CrewViewHolder extends RecyclerView.ViewHolder {

        TextView name, job, department;

        CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            job = itemView.findViewById(R.id.job);
            department = itemView.findViewById(R.id.department);
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
