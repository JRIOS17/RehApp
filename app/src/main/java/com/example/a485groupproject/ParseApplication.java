package com.example.a485groupproject;

import com.parse.Parse;
import android.app.Application;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("DASsKTzEyXcAlkWj69x8r08rnXprf46UHZr7PHgg")
                .clientKey("yarqeeYIIZIeR7zqhS3SEjdo83RjHChcr1dcwN8F")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
