package com.veris.verisimagenes.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;

import com.veris.verisimagenes.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(2000);
        Thread background = new Thread() {
            public void run() {

                try {

                    sleep(5*800);
                    Intent i=new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);

                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }
}