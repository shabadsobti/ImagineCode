package com.imaginecode.imaginecode;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AdapterStudent extends ArrayAdapter<Student> {

    private Context mContext;

    private List<Student> studentsList = new ArrayList<>();

    //constructor, call on creation

    public AdapterStudent(Context context, int resource, ArrayList<Student> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.studentsList = objects;
    }


    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the property we are displaying
        Student student = studentsList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.student_item, null);
        TextView first_name = (TextView) view.findViewById(R.id.first_name);
        first_name.setText(student.first_name);


        return view;
    }


}
