package com.imaginecode.imaginecode;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class AddStudent extends AppCompatActivity {


    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_add_student);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.headbar));
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.title_activity_add_student);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddStudent.this, CheckIn.class);
                startActivity(intent);
            }
        });

        final EditText fname = findViewById(R.id.first_name);
        final EditText lname = findViewById(R.id.last_name);

        Button add_user = findViewById(R.id.add_user);



        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = fname.getText().toString();
                String last_name = lname.getText().toString();



                if (first_name == null || first_name.equals("")) {
                    first_name = "I";
                }
                if (last_name == null || last_name.equals("")) {
                    last_name = "C";
                }



                myDb = new DatabaseHelper(getApplicationContext());

                Student a = new Student(first_name, last_name, "");
                myDb.createStudent(a);
                myDb.close();
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);//Set result OK

                finish();//finish activity

            }
        });

    }

}

