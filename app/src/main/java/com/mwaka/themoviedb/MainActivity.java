package com.mwaka.themoviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.mwaka.themoviedb.adapters.TopRatedMovieAdapter;
import com.mwaka.themoviedb.contracts.MovieApiService;
import com.mwaka.themoviedb.models.TopRated;
import com.mwaka.themoviedb.models.TopRatedResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;

    private ChipNavigationBar menu;

    private final static String API_KEY = "a458c033ef179bea8fc30030663c9c3f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        menu = findViewById(R.id.bottom_nav);

        menu.showBadge(R.id.favorites, 0);

        menu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (R.id.home == i){
                    menu.showBadge(R.id.favorites, 1);
                }else if (R.id.movies == i){
                    menu.showBadge(R.id.favorites, 14);
                }else if (R.id.tv == i){
                    menu.showBadge(R.id.favorites, 200);
                }else if (R.id.favorites == i){
                    menu.showBadge(R.id.favorites, 7);
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        connectAndGetApiData();
    }

    /**
     * This method create an instance of Retrofit
     * set the base url
     */
    public void connectAndGetApiData(){

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        
        MovieApiService TopRatedApiService = retrofit.create(MovieApiService.class);
        Call<TopRatedResponse> call = TopRatedApiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                assert response.body() != null;
                List<TopRated> TopRateds = response.body().getResults();
                recyclerView.setAdapter(new TopRatedMovieAdapter(TopRateds, R.layout.list_item_top_rated, getApplicationContext()));
                Log.d(TAG, "Number of TopRateds received: " + TopRateds.size());
            }
            @Override
            public void onFailure(Call<TopRatedResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}
