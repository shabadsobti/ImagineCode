package com.imaginecode.imaginecode;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class InstructionsBlockly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_instructions_blockly);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.title_lesson_instructions);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.headbar));

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Intent oldintent = getIntent();
        Integer lesson_number = oldintent.getIntExtra("Lesson_Number", 1);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("Student_ID", student_id);
//        intent.putExtra("Lesson_ID", lesson.lesson_id);
//        intent.putExtra("Module_ID", module_id);
//        intent.putExtra("Lesson_Number", lesson.getNumber());
//        intent.putExtra("Lesson_Instructions", lesson.getInstructions());
//        intent.putExtra("Lesson_Code", lesson.getCorrectCode());
//        view.getContext().startActivity(intent);

        Bundle oldIntentExtras = oldintent.getExtras();
        final Intent intent = new Intent(this, BlocklyLessonActivity.class);
        intent.putExtras(oldIntentExtras);

        Button lesson = findViewById(R.id.openLesson);
        lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);

            }
        });



        DatabaseHelper db;
        db = new DatabaseHelper(this);
        ViewPager viewPager = findViewById(R.id.viewpager1);
        Log.d("INSTRUCTIONs", db.getInstructionPages(7).get(0).getInstructions());
        viewPager.setAdapter(new InstructionsAdapter(this, db.getInstructionPages(7), lesson_number));


    }
}
