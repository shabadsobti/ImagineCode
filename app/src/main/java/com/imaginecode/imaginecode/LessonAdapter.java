package com.imaginecode.imaginecode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LessonAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<LessonClass>  lessons;

    public LessonAdapter(Context context, ArrayList<LessonClass> lessons) {
        this.mContext = context;
        this.lessons = lessons;
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
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.cell_lesson, null);
        }

        final TextView lesson_num = (TextView)convertView.findViewById(R.id.lesson_num);
        final ImageView star1 = (ImageView)convertView.findViewById(R.id.imageview_1);
        final ImageView star2 = (ImageView)convertView.findViewById(R.id.imageview_2);
        final ImageView star3 = (ImageView)convertView.findViewById(R.id.imageview_3);

        lesson_num.setText(lesson.getNumber());

        if(lesson.getStars() == 1){
            star1.setImageResource(R.drawable.star_lvlcomp);
        }
        else if(lesson.getStars() == 2){
            star1.setImageResource(R.drawable.star_lvlcomp);
            star2.setImageResource(R.drawable.star_lvlcomp);
        }
        else if(lesson.getStars() == 3){
            star1.setImageResource(R.drawable.star_lvlcomp);
            star2.setImageResource(R.drawable.star_lvlcomp);
            star3.setImageResource(R.drawable.star_lvlcomp);
        }
        if (!lesson.getUnlocked()){
            convertView.setAlpha(0.5f);
            convertView.setClickable(false);
        }
        return convertView;
    }
}
