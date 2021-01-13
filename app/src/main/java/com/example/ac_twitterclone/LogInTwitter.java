package com.example.ac_twitterclone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseInstallation;

public class LogInTwitter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_twitter);


        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}