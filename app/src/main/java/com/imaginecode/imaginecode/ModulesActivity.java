package com.imaginecode.imaginecode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ModulesActivity extends AppCompatActivity {

    Button intro_button;
    Button solar_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        intro_button = (Button) findViewById(R.id.intro_button);
        intro_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ModulesActivity.this,
                        IntroModActivity.class);
                startActivity(myIntent);
            }
        });

        solar_button = (Button) findViewById(R.id.solar_mod_button);
        solar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ModulesActivity.this,
                        IntroModActivity.class);
                startActivity(myIntent);
            }
        });
    }

}
