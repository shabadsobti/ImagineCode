package com.imaginecode.imaginecode;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        new BackgroundTask().execute();
    }

    private class BackgroundTask extends AsyncTask {
        Intent intent;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            intent = new Intent(LaunchScreenActivity.this, CheckIn.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            /*  Use this method to load background
             * data that your app needs. */

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

            startActivity(intent);
            finish();
        }


    }


}
