package com.learnacad.cashgo.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learnacad.cashgo.Models.SharedPrefManager;
import com.learnacad.cashgo.R;

public class SplashActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.setTitle("Welcome");

        sharedPrefManager = new SharedPrefManager(this);

        if(sharedPrefManager.isFirstTimeLaunch()) {

            sharedPrefManager.setFirstTimeLaunch(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }, 5000);

        }else{

            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            sharedPrefManager.setFirstTimeLaunch(false);

        }
    }
}
