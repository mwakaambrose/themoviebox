package com.mwaka.themoviedb.screens;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mwaka.themoviedb.MainActivity;
import com.mwaka.themoviedb.R;
import com.mwaka.themoviedb.adapters.TopMovieAdapter;
import com.mwaka.themoviedb.adapters.TopTVShowAdapter;
import com.mwaka.themoviedb.adapters.TrendingAdapter;
import com.mwaka.themoviedb.constants.Keys;
import com.mwaka.themoviedb.contracts.MovieApiService;
import com.mwaka.themoviedb.contracts.TVShowApiService;
import com.mwaka.themoviedb.contracts.TrendingApiService;
import com.mwaka.themoviedb.models.Movie;
import com.mwaka.themoviedb.models.TVShow;
import com.mwaka.themoviedb.models.Trending;
import com.mwaka.themoviedb.responses.MoviesResponse;
import com.mwaka.themoviedb.responses.TVResponse;
import com.mwaka.themoviedb.responses.TrendingResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView movies_recycler_view, tv_recycler_view, trending_recycler_view;
    private TextView top_rated, up_coming, tv_top_rated, tv_popular;
    private List<Movie> upcoming_list;
    private List<Movie> top_rated_list;
    private List<TVShow> tv_popular_list;
    private List<TVShow> tv_top_rated_list;

    private List<Trending> trending;

    public Home() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    private static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        top_rated = view.findViewById(R.id.top_rated);
        up_coming = view.findViewById(R.id.upcoming);

        tv_top_rated = view.findViewById(R.id.tv_top_rated);
        tv_popular = view.findViewById(R.id.tv_popular);

        trending_recycler_view = view.findViewById(R.id.trending_recycler_view);

        movies_recycler_view = view.findViewById(R.id.movies_recycler_view);
        tv_recycler_view = view.findViewById(R.id.tv_recycler_view);

        movies_recycler_view.setHasFixedSize(true);
        movies_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        tv_recycler_view.setHasFixedSize(true);
        tv_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        trending_recycler_view.setHasFixedSize(true);
        trending_recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 2));

        connectAndGetApiData();

        top_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top_rated.setTextColor(getResources().getColor(R.color.colorBlack));
                up_coming.setTextColor(getResources().getColor(R.color.colorGreyLight));
                movies_recycler_view.setAdapter(new TopMovieAdapter(top_rated_list, R.layout.list_item_top_contents, getContext()));

            }
        });

        up_coming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_coming.setTextColor(getResources().getColor(R.color.colorBlack));
                top_rated.setTextColor(getResources().getColor(R.color.colorGreyLight));
                movies_recycler_view.setAdapter(new TopMovieAdapter(upcoming_list, R.layout.list_item_top_contents, getContext()));
            }
        });

        tv_top_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_top_rated.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_popular.setTextColor(getResources().getColor(R.color.colorGreyLight));
                tv_recycler_view.setAdapter(new TopTVShowAdapter(tv_top_rated_list, R.layout.list_item_top_contents, getContext()));

            }
        });

        tv_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_popular.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_top_rated.setTextColor(getResources().getColor(R.color.colorGreyLight));
                tv_recycler_view.setAdapter(new TopTVShowAdapter(tv_popular_list, R.layout.list_item_top_contents, getContext()));
            }
        });

        return view;
    }

    private void connectAndGetApiData() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieApiService top_rated_api = retrofit.create(MovieApiService.class);
        MovieApiService up_coming_api = retrofit.create(MovieApiService.class);

        TVShowApiService tv_top_rated_api = retrofit.create(TVShowApiService.class);
        TVShowApiService tv_popular_api = retrofit.create(TVShowApiService.class);

        TrendingApiService trending_api = retrofit.create(TrendingApiService.class);

        Call<MoviesResponse> call_top_rated = top_rated_api.getTopRatedMovies(Keys.API_KEY);
        call_top_rated.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                assert response.body() != null;
                top_rated_list = response.body().getResults();
                movies_recycler_view.setAdapter(new TopMovieAdapter(top_rated_list, R.layout.list_item_top_contents, getContext()));
                Log.d(TAG, "Number of Top Rated Movies received: " + top_rated_list.size());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

        Call<MoviesResponse> call_upcoming = up_coming_api.getUpcomingMovies(Keys.API_KEY);
        call_upcoming.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                assert response.body() != null;
                upcoming_list = response.body().getResults();
                Log.d(TAG, "Number of Upcoming Movies received: " + upcoming_list.size());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        Call<TVResponse> call_tv_top_rated = tv_top_rated_api.getTopRatedShows(Keys.API_KEY);
        call_tv_top_rated.enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                assert response.body() != null;
                tv_top_rated_list = response.body().getResults();
                tv_recycler_view.setAdapter(new TopTVShowAdapter(tv_top_rated_list, R.layout.list_item_top_contents, getContext()));
                Log.d(TAG, "Number of TopRated Shows received: " + tv_top_rated_list.size());
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        Call<TVResponse> call_tv_popular = tv_popular_api.getPopularShows(Keys.API_KEY);
        call_tv_popular.enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                assert response.body() != null;
                tv_popular_list = response.body().getResults();
                Log.d(TAG, "Number of Popular Shows received: " + tv_popular_list.size());
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        Call<TrendingResponse> trending_call = trending_api.getTrendingMedia(Keys.API_KEY);
        trending_call.enqueue(new Callback<TrendingResponse>(){

            @Override
            public void onResponse(Call<TrendingResponse> call, Response<TrendingResponse> response) {
                Log.d(TAG, "Response");
                assert response.body() != null;
                trending = response.body().getResults();
                trending_recycler_view.setAdapter(new TrendingAdapter(trending, R.layout.list_item_trending_contents, getContext()));
                Log.d(TAG, "Number of Trending received: " + trending.size());
            }

            @Override
            public void onFailure(Call<TrendingResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
