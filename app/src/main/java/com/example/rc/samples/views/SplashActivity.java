package com.example.rc.samples.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.rc.samples.AppConfig;
import com.example.rc.samples.views.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        prefs.edit()
                .remove(AppConfig.TOKEN)
                .commit();

        Intent intent = null;

        if (prefs.contains(AppConfig.TOKEN)) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }


        startActivity(intent);
        finish();


    }
}
