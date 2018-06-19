package com.imaginecode.imaginecode;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import java.util.ArrayList;

public class CheckIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        ArrayList<Student> students = db.getAllStudents();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        Button add_user = findViewById(R.id.add_user);

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckIn.this, BlocklyLessonActivity.class);
                startActivity(intent);
            }
        });

        GridView gridview = (GridView) findViewById(R.id.gridview);

        ArrayAdapter<Student> adapter = new AdapterStudent(this, 0, students);

        gridview.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        ArrayList<Student> students = db.getAllStudents();

        GridView gridview = (GridView) findViewById(R.id.gridview);

        ArrayAdapter<Student> adapter = new AdapterStudent(this, 0, students);

        gridview.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is IntentId
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Student Added", Toast.LENGTH_LONG).show();
            }

            if (resultCode == RESULT_CANCELED)

                //When result is cancelled display toast
                Toast.makeText(this, "Activity cancelled.", Toast.LENGTH_SHORT).show();

        }
    }

}