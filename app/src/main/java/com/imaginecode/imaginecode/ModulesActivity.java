package com.imaginecode.imaginecode;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ModulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_modules);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.title_activity_modules);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.headbar));

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });








        Intent intent = getIntent();
        Integer student_id = intent.getIntExtra("student_ID", 1);

        DatabaseHelper db = new DatabaseHelper(this);


        RecyclerView recyclerView = findViewById(R.id.rv);
        try{
            ModuleAdapter recyclerAdapter = new ModuleAdapter(this, db.getModules(), student_id);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(recyclerAdapter);
        }
        catch (Exception e){

        }


    }

    

}
