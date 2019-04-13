package com.swp.culturehomestay.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.swp.culturehomestay.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
//                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundResource(R.drawable.splashscreen);
//                .withHeaderText("Header")
//                .withFooterText("WANT 2019")
//                .withBeforeLogoText("My cool company")
//                .withLogo(R.drawable.logo)
//                .withAfterLogoText("Welcome to Culture Homestay");
        //add custom font
//        Typeface pacificoFont = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
//        config.getAfterLogoTextView().setTypeface(pacificoFont);

        //change text color
//        config.getAfterLogoTextView().setTextColor(Color.WHITE);
//        config.getFooterTextView().setTextColor(getResources().getColor(R.color.colorPrimaryButtonActive));

        //finally create the view
        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);
    }
}
