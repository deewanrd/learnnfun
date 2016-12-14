package com.example.rahul.learnnfun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        Thread t=new Thread(){
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent i=new Intent(SplashActivity.this,Login.class);
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}
