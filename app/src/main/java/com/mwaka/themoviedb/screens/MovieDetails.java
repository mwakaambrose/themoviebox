package com.mwaka.themoviedb.screens;

import android.content.Intent;
import android.os.Bundle;

import com.aquery.AQuery;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mwaka.themoviedb.R;
import com.mwaka.themoviedb.adapters.CastAdapter;
import com.mwaka.themoviedb.adapters.CrewAdapter;
import com.mwaka.themoviedb.constants.Keys;
import com.mwaka.themoviedb.constants.URLs;
import com.mwaka.themoviedb.contracts.MovieApiService;
import com.mwaka.themoviedb.models.Cast;
import com.mwaka.themoviedb.models.Credit;
import com.mwaka.themoviedb.models.Crew;
import com.mwaka.themoviedb.models.Genre;
import com.mwaka.themoviedb.responses.AMovieResponse;
import com.mwaka.themoviedb.responses.MoviesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetails extends AppCompatActivity {

    AQuery aQuery;

    RatingBar ratingBar;
    ImageView back_drop, poster;

    String movie_id;

    Credit credit;

    private RecyclerView cast_recycler_view, crew_recycler_view;

    List<Genre> genreList;

    private static Retrofit retrofit = null;
    private static final String TAG = "MovieDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        aQuery = new AQuery(this);

        cast_recycler_view = findViewById(R.id.cast_recycler_view);
        crew_recycler_view = findViewById(R.id.crew_recycler_view);
        cast_recycler_view.setHasFixedSize(true);
        crew_recycler_view.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(crew_recycler_view.getContext(), layoutManager.getOrientation());

        cast_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        crew_recycler_view.setLayoutManager(layoutManager);
        crew_recycler_view.addItemDecoration(dividerItemDecoration);

        aQuery.id(R.id.movie_title).text(aQuery.getStringIntent("title"));
        aQuery.id(R.id.movie_rating).text(aQuery.getStringIntent("rating"));
        aQuery.id(R.id.moview_overview).text(aQuery.getStringIntent("overview"));
        aQuery.id(R.id.release_date).text(aQuery.getStringIntent("release_date"));

        movie_id = aQuery.getStringIntent("id");

        back_drop = findViewById(R.id.backdrop_img);
        poster = findViewById(R.id.poster_img);

        ratingBar = findViewById(R.id.rating_bar);

        Picasso.get().load(URLs.IMAGE_BASE + aQuery.getStringIntent("poster_path")).into(poster);
        Picasso.get().load(URLs.IMAGE_BASE + aQuery.getStringIntent("back_drop_path")).into(back_drop);

        fetchMovieDetails();

    }

    private void fetchMovieDetails() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URLs.BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<AMovieResponse> moviesResponseCall = movieApiService.getMovie(movie_id, Keys.API_KEY, "credits");
        moviesResponseCall.enqueue(new Callback<AMovieResponse>() {
            @Override
            public void onResponse(final Call<AMovieResponse> call, Response<AMovieResponse> response) {
                assert response.body() != null;

                credit = response.body().getCredit();

                String genres = "";

                genreList = response.body().getGenres();

                for (int i = 0; i < genreList.size(); i++){
                    genres = genres.concat(genreList.get(i).getName()+" ");
                    Log.d(TAG, genreList.get(i).getName());
                }

                aQuery.id(R.id.genres).text(genres);
                ratingBar.setRating(response.body().getVote_average());

                cast_recycler_view.setAdapter(new CastAdapter(credit.getCasts(), getApplicationContext(), new CastAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Cast cast) {

                    }
                }));

                crew_recycler_view.setAdapter(new CrewAdapter(credit.getCrews(), getApplicationContext(), new CrewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Crew cast) {

                    }
                }));
            }

            @Override
            public void onFailure(Call<AMovieResponse> call, Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
