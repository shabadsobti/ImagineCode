package com.imaginecode.imaginecode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        DatabaseHelper db = new DatabaseHelper(this);

        GridView gridView = (GridView)findViewById(R.id.gridview);
        Intent intent = getIntent();
        Integer module_id = intent.getIntExtra("Module_ID", 1);
        Integer student_id = intent.getIntExtra("student_ID", 1);

        lessons = db.getLessons(student_id, module_id);
        LessonAdapter lessonsAdapter = new LessonAdapter(this, lessons);
        gridView.setAdapter(lessonsAdapter);

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(IntroModActivity.this,
                        BlocklyLessonActivity.class);
                startActivity(myIntent);
            }
        });
    }

}
