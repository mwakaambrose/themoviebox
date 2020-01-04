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
import android.widget.ImageView;
import android.widget.TextView;

import com.aquery.AQuery;
import com.google.android.material.snackbar.Snackbar;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;
import com.mwaka.themoviedb.R;
import com.mwaka.themoviedb.adapters.MovieAdapter;
import com.mwaka.themoviedb.adapters.SeasonsAdapter;
import com.mwaka.themoviedb.constants.Keys;
import com.mwaka.themoviedb.constants.URLs;
import com.mwaka.themoviedb.contracts.MovieApiService;
import com.mwaka.themoviedb.models.Movie;
import com.mwaka.themoviedb.models.Season;
import com.mwaka.themoviedb.responses.AMediaResponse;
import com.mwaka.themoviedb.responses.MoviesResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Movies.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Movies#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Movies extends Fragment {

    private static final String TAG = "Movies";
    private static Retrofit retrofit = null;

    private ShimmerRecyclerViewX popular_recycler_view, now_playing_recycler_view;

    private List<Movie> popularList;

    private List<Movie> nowPlayingList;

    private AQuery aQuery;

    private TextView genres, load_more;
    private ImageView poster_image;

    private OnFragmentInteractionListener mListener;

    private int pageNumber = 1;
    private MovieAdapter popular_adapter;

    public Movies() {}

    private static Movies newInstance(String param1, String param2) {
        Movies fragment = new Movies();
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
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        aQuery = new AQuery(Objects.requireNonNull(getContext()));

        Window window = Objects.requireNonNull(getActivity()).getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the  color
        window.setStatusBarColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary));


        popular_recycler_view = view.findViewById(R.id.popular_recycler_view);
        now_playing_recycler_view = view.findViewById(R.id.now_playing_recycler_view);

        popular_recycler_view.setHasFixedSize(true);
        popular_recycler_view.setLayoutManager(new GridLayoutManager(getContext(),  3));
        popular_recycler_view.showShimmerAdapter();

        now_playing_recycler_view.setHasFixedSize(true);
        now_playing_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        now_playing_recycler_view.showShimmerAdapter();

        genres = view.findViewById(R.id.genres);
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

        MovieApiService latest = retrofit.create(MovieApiService.class);
        MovieApiService playing = retrofit.create(MovieApiService.class);
        MovieApiService popular = retrofit.create(MovieApiService.class);

        Call<AMediaResponse> callLatest = latest.getLatestMovie(Keys.API_KEY);
        callLatest.enqueue(new Callback<AMediaResponse>(){

            @Override
            public void onResponse(Call<AMediaResponse> call, Response<AMediaResponse> response) {
                assert response.body() != null;

                AMediaResponse aMediaResponse = response.body();

                Picasso.get().load(URLs.IMAGE_BASE+aMediaResponse.getPosterPath())
                        .error(R.drawable.poster)
                        .placeholder(R.drawable.ic_movie_black_24dp)
                        .into(poster_image);

                genres.setText(aMediaResponse.getOverview());
            }

            @Override
            public void onFailure(Call<AMediaResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        Call<MoviesResponse> callPlaying = playing.getNowPlayingMovies(Keys.API_KEY);
        callPlaying.enqueue(new Callback<MoviesResponse>(){

            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                now_playing_recycler_view.hideShimmerAdapter();
                assert response.body() != null;
                nowPlayingList = response.body().getResults();

                now_playing_recycler_view.setAdapter(new MovieAdapter(nowPlayingList, new MovieAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Movie item, View v) {
                        Intent intent = new Intent(getContext(), MediaDetails.class);
                        intent.putExtra("id", item.getId()+"");
                        intent.putExtra("title", item.getTitle());
                        intent.putExtra("overview", item.getOverview());
                        intent.putExtra("poster_path", item.getPosterPath());
                        intent.putExtra("back_drop_path", item.getBackdropPath());
                        intent.putExtra("rating", item.getVoteAverage());
                        intent.putExtra("media_type", "movie");
                        aQuery.openFromLeft(intent);
                    }
                }));

                Log.d(TAG, response.raw().toString());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                now_playing_recycler_view.hideShimmerAdapter();
                Log.e(TAG, t.toString());
            }
        });

        Call<MoviesResponse> callPopular = popular.getPopularMovies(Keys.API_KEY, 1);
        callPopular.enqueue(new Callback<MoviesResponse>(){

            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                popular_recycler_view.hideShimmerAdapter();
                assert response.body() != null;
                popularList = response.body().getResults();

                popular_adapter = new MovieAdapter(popularList, new MovieAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Movie item, View v) {
                        Intent intent = new Intent(getContext(), MediaDetails.class);
                        intent.putExtra("id", item.getId()+"");
                        intent.putExtra("title", item.getTitle());
                        intent.putExtra("overview", item.getOverview());
                        intent.putExtra("poster_path", item.getPosterPath());
                        intent.putExtra("back_drop_path", item.getBackdropPath());
                        intent.putExtra("rating", item.getVoteAverage());
                        intent.putExtra("media_type", "movie");
                        aQuery.openFromLeft(intent);
                    }
                });

                popular_recycler_view.setAdapter(popular_adapter);

                Log.d(TAG, response.raw().toString());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                popular_recycler_view.hideShimmerAdapter();
                Log.e(TAG, t.toString());
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void getNextPage(){

        pageNumber = pageNumber + 1;

        MovieApiService popular = retrofit.create(MovieApiService.class);

        Call<MoviesResponse> callPopular = popular.getPopularMovies(Keys.API_KEY, pageNumber);
        callPopular.enqueue(new Callback<MoviesResponse>(){

            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                assert response.body() != null;
                popularList.addAll(response.body().getResults());
                popular_adapter.notifyDataSetChanged();
                Log.d(TAG, "Adding new data");
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }
}
