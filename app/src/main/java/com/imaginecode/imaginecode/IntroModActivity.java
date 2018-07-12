package com.imaginecode.imaginecode;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class IntroModActivity extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db =  new DatabaseHelper(this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.headbar));
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.title_activity_levels);


        Intent intent = getIntent();
        Integer module_id = intent.getIntExtra("Module_ID", 1);
        Integer student_id = intent.getIntExtra("student_ID", 1);

        showGridTask task = new showGridTask();
        task.execute(student_id, module_id);
    }

    private class showGridTask extends AsyncTask<Integer, Integer, LessonAdapter>{

        @Override
        protected LessonAdapter doInBackground(Integer... params) {
            ArrayList<LessonClass> lessons = db.getLessons(params[0], params[1]);
            publishProgress(1);
            return new LessonAdapter(getApplicationContext(), lessons, params[0]);
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            Log.d("LOADING","loading");

        }

        @Override
        protected void onPostExecute(LessonAdapter result) {
            GridView gridView = findViewById(R.id.gridview);
            gridView.setAdapter(result);
            Log.d("FINAL","success");
        }

    }

}
