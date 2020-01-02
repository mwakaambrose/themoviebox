package com.mwaka.themoviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aquery.AQuery;

import java.util.Objects;

import static java.security.AccessController.getContext;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the  color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorOrange));

        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);


        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);
        TextView textVie2 = findViewById(R.id.textView2);
        TextView textVie3 = findViewById(R.id.textView3);
        imageView.startAnimation(fade_in);
        textView.startAnimation(fade_in);
        textVie2.startAnimation(fade_in);
        textVie3.startAnimation(fade_in);

        Thread th = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(4000);
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        th.start();
    }
}
