package com.imaginecode.imaginecode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class IntroModActivity extends AppCompatActivity {

    ArrayList<LessonClass> lessons;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        GridView gridView = (GridView)findViewById(R.id.gridview);
        lessons = LessonClass.generateInitialLessons();
        LessonAdapter lessonsAdapter = new LessonAdapter(this, lessons);
        gridView.setAdapter(lessonsAdapter);

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(IntroModActivity.this,
                        ModulesActivity.class);
                startActivity(myIntent);
            }
        });
    }

}
