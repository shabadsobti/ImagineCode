package com.imaginecode.imaginecode;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Locale;

public class CheckIn extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {






        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        ArrayList<Student> students = db.getAllStudents();

        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_check_in);

        Toolbar myToolbar = findViewById(R.id.top_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.headbar));
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckIn.this, LanguageSelection.class);
                startActivity(intent);
            }
        });


        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.title_activity_check_in);



        Button add_user = findViewById(R.id.add_user);

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckIn.this, AddStudent.class);
                startActivityForResult(intent, 1);
            }
        });

        GridView gridview = findViewById(R.id.gridview);

        ArrayAdapter<Student> adapter = new AdapterStudent(this, 0, students);

        gridview.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        ArrayList<Student> students = db.getAllStudents();

        GridView gridview = findViewById(R.id.gridview);

        ArrayAdapter<Student> adapter = new AdapterStudent(this, 0, students);

        gridview.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is IntentId
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.toast_student_added, Toast.LENGTH_LONG).show();
            }

        }
    }




}
