package com.imaginecode.imaginecode;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class InstructionsAdapter extends PagerAdapter {
    private Context mContext;
    private List<InstructionsPage> mListData;
    private Integer lesson_num;

    public InstructionsAdapter(Context context, List<InstructionsPage> listDate, int lesson_num) {
        mContext = context;
        mListData = listDate;
        this.lesson_num = lesson_num;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(getLayout(mListData.get(position)), container, false);

        final TextView instructions = view.findViewById(R.id.instructions);
        instructions.setText(mListData.get(position).getInstructions());

        try {
            final ImageView image = view.findViewById(R.id.image);
            Drawable drawable = mContext.getResources().getDrawable(mContext.getResources()
                    .getIdentifier("arduino_"+lesson_num, "drawable", mContext.getPackageName()));
            image.setImageDrawable(drawable);
        }
        catch (Exception e){

        }


        container.addView(view);
        return view;
    }

    private int getLayout(InstructionsPage page){
        if(page.getType() == "learning_instructions"){
            return R.layout.learning_instructions;
        }
        else if (page.getType() == "circuit_instructions"){
            return R.layout.circuit_instructions;
        }
        else{
            return R.layout.intro_instructions;
        }

    }
}
