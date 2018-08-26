package com.imaginecode.imaginecode;


import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {



    private List<CardView> mViews;
    private Integer module_id;
    private Context mContext;
    private Integer student_id;
    private List<LessonClass> mData;
    private float mBaseElevation;

    public CardPagerAdapter(Integer student_id, Integer module_id, Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.student_id = student_id;
        this.module_id = module_id;
        this.mContext = context;

    }

    public void addCardItem(LessonClass item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(LessonClass item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        Button play = view.findViewById(R.id.play);
        ImageView done = view.findViewById(R.id.done);
        if(item.getStars()>0){
            play.setText(view.getResources().getText(R.string.redo));
        }
        else{
            play.setText(view.getResources().getText(R.string.play));
            done.setVisibility(View.INVISIBLE);

        }

        DatabaseHelper db = new DatabaseHelper(mContext);
        int lesson_id_previous;

        if(item.getNumber() > 1){
            lesson_id_previous = db.getLessonID(module_id, item.getNumber()-1);
            if (db.lessonGetStars(student_id, lesson_id_previous) == 0){
                play.setEnabled(false);
                play.setText(view.getResources().getText(R.string.locked));


            }
        }




        final int lesson_id = item.lesson_id;
        final int lesson_number = item.getNumber();
        final String lesson_instructions = item.getInstructions();
        final String lesson_code = item.getCorrectCode();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(module_id == 2){
                    intent = new Intent(view.getContext(), InstructionsBlockly.class);
                }
                else{
                    intent = new Intent(view.getContext(), BlocklyLessonActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Student_ID", student_id);
                intent.putExtra("Lesson_ID", lesson_id);
                intent.putExtra("Module_ID", module_id);
                intent.putExtra("Lesson_Number", lesson_number);
                intent.putExtra("Lesson_Instructions", lesson_instructions);
                intent.putExtra("Lesson_Code", lesson_code);
                view.getContext().startActivity(intent);
            }
        });

        titleTextView.setText(view.getResources().getText(R.string.lesson) + " " + item.getNumber().toString() + "/" + mData.size());
        RatingBar stars = view.findViewById(R.id.stars);
        if (module_id == 2){
            stars.setNumStars(1);
        }
        stars.setRating(item.getStars());

    }


    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

}