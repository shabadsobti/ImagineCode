package com.imaginecode.imaginecode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Property;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.lang.reflect.Array;
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

        GridView gridview = (GridView) findViewById(R.id.gridview);

        ArrayAdapter<Student> adapter = new AdapterStudent(this, 0, students);

        gridview.setAdapter(adapter);


    }

}
