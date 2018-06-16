package com.imaginecode.imaginecode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddStudent extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        final EditText fname = findViewById(R.id.first_name);
        final EditText lname = findViewById(R.id.last_name);
        Button avatar = findViewById(R.id.avatar);
        Button add_user = findViewById(R.id.add_user);

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = fname.getText().toString();
                String last_name = lname.getText().toString();
                myDb = new DatabaseHelper(getApplicationContext());
                Student a = new Student(first_name, last_name, "/url");
                myDb.createStudent(a);
                myDb.close();
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);//Set result OK
                finish();//finish activity

            }
        });



    }

}
