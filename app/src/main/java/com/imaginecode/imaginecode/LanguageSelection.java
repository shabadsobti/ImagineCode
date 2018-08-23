package com.imaginecode.imaginecode;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import java.util.Locale;

public class LanguageSelection extends AppCompatActivity implements View.OnClickListener {

    Locale myLocale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_language_selection);

        //Create your buttons and set their onClickListener to "this"
        ImageButton buttonHindi = (ImageButton) findViewById(R.id.hindi);
        buttonHindi.setOnClickListener(this);

        ImageButton buttonEnglish = (ImageButton) findViewById(R.id.english);
        buttonEnglish.setOnClickListener(this);



    }

    //implement the onClick method here
    public void onClick(View v) {
        Intent intent = new Intent(this, CheckIn.class);
        startActivity(intent);
        switch(v.getId()) {
            case R.id.english:
                setLocale("en");
                break;

            case R.id.hindi:
                setLocale("hi");
                break;
        }
    }


    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }


}
