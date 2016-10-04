package com.devqt.kubg.info;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class LoadScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent mainIntent = new Intent(LoadScreen.this,KUBG_main.class);
                LoadScreen.this.startActivity(mainIntent);
                LoadScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}