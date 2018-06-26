package com.imaginecode.imaginecode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class IntroModActivity extends AppCompatActivity {

    ArrayList<LessonClass> lessons;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_intro);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.headbar));
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);


        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Levels");

        DatabaseHelper db = new DatabaseHelper(this);

        GridView gridView = (GridView)findViewById(R.id.gridview);
        Intent intent = getIntent();
        Integer module_id = intent.getIntExtra("Module_ID", 1);
        Integer student_id = intent.getIntExtra("student_ID", 1);

        lessons = db.getLessons(student_id, module_id);
        LessonAdapter lessonsAdapter = new LessonAdapter(this, lessons);

        gridView.setAdapter(lessonsAdapter);


    }

}
