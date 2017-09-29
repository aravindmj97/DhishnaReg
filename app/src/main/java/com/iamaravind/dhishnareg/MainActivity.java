package com.iamaravind.dhishnareg;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent tologin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(tologin, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                //finish();
            }
        },SPLASH_TIME_OUT);
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}