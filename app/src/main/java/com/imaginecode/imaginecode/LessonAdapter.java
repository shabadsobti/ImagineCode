package com.imaginecode.imaginecode;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LessonAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<LessonClass>  lessons;
    private final Integer student_id;

    public LessonAdapter(Context context, ArrayList<LessonClass> lessons, Integer student_id) {
        this.mContext = context;
        this.lessons = lessons;
        this.student_id = student_id;
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }


    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LessonClass lesson = lessons.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.cell_lesson, null);
        }

        RatingBar stars = convertView.findViewById(R.id.stars);
        stars.setRating(lesson.getStars());


        TextView lesson_num = convertView.findViewById(R.id.lesson_num);
        lesson_num.setText(((lesson.getNumber().toString())));



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BlocklyLessonActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Student_ID", student_id);
                intent.putExtra("Lesson_ID", lesson.lesson_id);
                intent.putExtra("Lesson_Number", lesson.getNumber());
                intent.putExtra("Lesson_Instructions", lesson.getInstructions());
                view.getContext().startActivity(intent);
            }
        });

        return convertView;
    }


}
