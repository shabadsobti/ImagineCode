package com.imaginecode.imaginecode;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    int[] image;
    String[] instruction;

    public ViewPagerAdapter(Context context, String[] instruction, int[] image) {
        this.context = context;
        this.image = image;
        this.instruction = instruction;
    }



    @Override
    public int getCount(){
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view ==  object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        TextView txt_instructions;
        ImageView project_image;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);




        // Locate the ImageView in viewpager_item.xml
        project_image = (ImageView) itemView.findViewById(R.id.projectimage);

        // Capture position and set to the ImageView
        project_image.setImageResource(image[position]);

        ((ViewPager) container).addView(itemView);

        return itemView;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
