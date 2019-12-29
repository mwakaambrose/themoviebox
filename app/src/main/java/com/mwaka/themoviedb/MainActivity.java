package com.mwaka.themoviedb;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.mwaka.themoviedb.screens.Favourites;
import com.mwaka.themoviedb.screens.Home;
import com.mwaka.themoviedb.screens.Movies;
import com.mwaka.themoviedb.screens.TV;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements
        Home.OnFragmentInteractionListener,
        Movies.OnFragmentInteractionListener,
        TV.OnFragmentInteractionListener,
        Favourites.OnFragmentInteractionListener {

    private ChipNavigationBar menu;
    private String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new Home()).addToBackStack(null).commit();

        menu = findViewById(R.id.bottom_nav);

        menu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (R.id.home == i){
                    menu.showBadge(R.id.favorites, 1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new Home()).addToBackStack(null).commit();
                }else if (R.id.movies == i){
                    menu.showBadge(R.id.favorites, 14);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new Movies()).addToBackStack(null).commit();
                }else if (R.id.tv == i){
                    menu.showBadge(R.id.favorites, 200);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new TV()).addToBackStack(null).commit();
                }else if (R.id.favorites == i){
                    menu.showBadge(R.id.favorites, 7);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new Favourites()).addToBackStack(null).commit();
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, Objects.requireNonNull(uri.getPath()));
    }
}
