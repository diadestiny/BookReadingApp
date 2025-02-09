package com.example.bottomtabber.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.bottomtabber.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(500);
                    Intent intent = new Intent(Splash.this,Login.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
