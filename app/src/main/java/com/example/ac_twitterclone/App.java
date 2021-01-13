package com.example.ac_twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("1OwF0l7svCmmL1VWoniXJHrGE33u5mKtaZE3bv1c")
                // if defined
                .clientKey("bQUqjfYHDkEqyw5Bs7dDvVlLfF0WF1mPDDHnhkwE")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
