package com.imaginecode.imaginecode;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;


import java.util.ArrayList;

public class CheckIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ArrayList<Student> students = new ArrayList<Student>();
        Student a = new Student("Shabad", "Sobti", "/url");
        Student b = new Student("Shlok", "Sobti", "/url");
        Student c = new Student("Shlok", "Sobti", "/url");
        students.add(a);
        students.add(b);
        students.add(c);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button add_user = findViewById(R.id.add_user);

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckIn.this, AddStudent.class);
                startActivityForResult(intent, 1);
            }
        });

        GridView gridview = (GridView) findViewById(R.id.gridview);

        ArrayAdapter<Student> adapter = new AdapterStudent(this, 0, students);

        gridview.setAdapter(adapter);


    }

}
