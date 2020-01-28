package com.mwaka.themoviedb.screens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aquery.AQuery;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;
import com.mwaka.themoviedb.R;
import com.mwaka.themoviedb.adapters.TVAdapter;
import com.mwaka.themoviedb.constants.Keys;
import com.mwaka.themoviedb.constants.URLs;
import com.mwaka.themoviedb.contracts.TVShowApiService;
import com.mwaka.themoviedb.models.TVShow;
import com.mwaka.themoviedb.responses.TVResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TV extends Fragment {

    private static final String TAG = "TV";
    private static Retrofit retrofit = null;

    private ShimmerRecyclerViewX airing_today_recycler_view, on_air_recycler_view;

    private List<TVShow> onAir;

    private List<TVShow> airing;

    private AQuery aQuery;

    private TextView overview, load_more;
    private ImageView poster_image;

    private int pageNumber = 1;
    private TVAdapter onAirAdapter;

    public TV() {}

    static TV newInstance() {
        TV fragment = new TV();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);

        Objects.requireNonNull(getActivity()).getWindow().setNavigationBarColor(getResources().getColor(R.color.colorDarkBlack));

        aQuery = new AQuery(Objects.requireNonNull(getContext()));

        Window window = Objects.requireNonNull(getActivity()).getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the  color
        window.setStatusBarColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorAccentDark));


        airing_today_recycler_view = view.findViewById(R.id.airing_today_recycler_view);
        on_air_recycler_view = view.findViewById(R.id.on_air_recycler_view);

        on_air_recycler_view.setHasFixedSize(true);
        on_air_recycler_view.setLayoutManager(new GridLayoutManager(getActivity(),  2));
        on_air_recycler_view.showShimmerAdapter();

        airing_today_recycler_view.setHasFixedSize(true);
        airing_today_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        airing_today_recycler_view.showShimmerAdapter();

//        overview = view.findViewById(R.id.overview);
        poster_image = view.findViewById(R.id.poster_image);
        load_more = view.findViewById(R.id.load_more);

        load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNextPage();
            }
        });

        connectAndGetApiData();

        return view;
    }

    private void connectAndGetApiData() {

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URLs.BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        TVShowApiService latestApi = retrofit.create(TVShowApiService.class);

        Call<TVResponse> latestCall = latestApi.getShowsOnTheAir(Keys.API_KEY, pageNumber);
        latestCall.enqueue(new Callback<TVResponse>() {

            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                assert  response.body() != null;

//                Picasso.get().load(URLs.IMAGE_BASE+"")
//                        .error(R.drawable.poster)
//                        .placeholder(R.drawable.ic_movie_black_24dp)
//                        .into(poster_image);

//                overview.setText(R.string.top_rated);

            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });


        TVShowApiService onAirTodayApi = retrofit.create(TVShowApiService.class);

        Call<TVResponse> onTheAirTodayCall = onAirTodayApi.getShowsOnTheAir(Keys.API_KEY, pageNumber);
        onTheAirTodayCall.enqueue(new Callback<TVResponse>() {

            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                assert  response.body() != null;
                onAir = response.body().getResults();

                onAirAdapter = new TVAdapter(onAir, new TVAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TVShow tvShow, View v) {
                        //get a library called percelors
                        Intent intent = new Intent(getContext(), MediaDetails.class);
                        intent.putExtra("id", tvShow.getId()+"");
                        intent.putExtra("title", tvShow.getName());
                        intent.putExtra("overview", tvShow.getOverview());
                        intent.putExtra("poster_path", tvShow.getPosterPath());
                        intent.putExtra("back_drop_path", tvShow.getBackdropPath());
                        intent.putExtra("rating", tvShow.getVoteAverage());
                        intent.putExtra("media_type", "tv");
                        aQuery.openFromLeft(intent);
                    }
                });

                on_air_recycler_view.setAdapter(onAirAdapter);

                Log.d(TAG, response.raw().toString());
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        TVShowApiService airingTodayApi = retrofit.create(TVShowApiService.class);

        Call<TVResponse> airingTodayCall = airingTodayApi.getShowsOnTheAir(Keys.API_KEY, pageNumber);
        airingTodayCall.enqueue(new Callback<TVResponse>() {

            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                assert  response.body() != null;
                airing = response.body().getResults();
                airing_today_recycler_view.setAdapter(new TVAdapter(airing, new TVAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TVShow tvShow, View v) {
                        Intent intent = new Intent(getContext(), MediaDetails.class);
                        intent.putExtra("id", tvShow.getId()+"");
                        intent.putExtra("title", tvShow.getName());
                        intent.putExtra("overview", tvShow.getOverview());
                        intent.putExtra("poster_path", tvShow.getPosterPath());
                        intent.putExtra("back_drop_path", tvShow.getBackdropPath());
                        intent.putExtra("rating", tvShow.getVoteAverage());
                        intent.putExtra("media_type", "tv");
                        aQuery.openFromLeft(intent);
                    }
                }));

                Log.d(TAG, response.raw().toString());
                Log.d(TAG, response.body().getResults().size()+"");
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void getNextPage() {
        pageNumber = pageNumber + 1;

        TVShowApiService tvShowApiService = retrofit.create(TVShowApiService.class);

        Call<TVResponse> tvResponseCall = tvShowApiService.getShowsOnTheAir(Keys.API_KEY, pageNumber);
        tvResponseCall.enqueue(new Callback<TVResponse>() {

            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                assert  response.body() != null;
                onAir.addAll(response.body().getResults());
                onAirAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {

            }
        });
    }

    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
