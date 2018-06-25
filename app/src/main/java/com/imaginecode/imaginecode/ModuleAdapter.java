package com.imaginecode.imaginecode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;






public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.MyRecyclerHolder> {

    private LayoutInflater inflater;
    private List<Module> list;

    public Integer student_id;



    ModuleAdapter(Context context, List<Module> list, Integer student_id) {
        inflater=LayoutInflater.from(context);
        this.list = list;
        this.student_id = student_id;

    }



    @Override
    public MyRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyRecyclerHolder(inflater.inflate(R.layout.module_item, parent, false));
    }


    @Override
    public void onBindViewHolder(MyRecyclerHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), IntroModActivity.class);
                intent.putExtra("Module_ID", list.get(position).module_id);
                intent.putExtra("student_ID", student_id);


                // Start the detail activity for result
                 v.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }






    public class MyRecyclerHolder extends RecyclerView.ViewHolder {

        private TextView name;

        public MyRecyclerHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.mod_name);

        }
    }


}
