package com.imaginecode.imaginecode;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        Character first_name = (student.first_name.charAt(0));
        Character last_name = (student.last_name.charAt(0));
        String student_initials = first_name + "" + last_name;
        Button student_button = view.findViewById(R.id.student_button);




//        TextView initials = (TextView) view.findViewById(R.id.initials);
//        initials.setText(student_initials);
        student_button.setText(student_initials);
        try {
            ContextWrapper cw = new ContextWrapper(mContext);
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir



            File f=new File(directory, student.avatar);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            try {
                student_button.setBackground(new BitmapDrawable(view.getResources(), getRoundedCornerBitmap(b.createScaledBitmap(b, 160, 160, true))));
            }
            catch (Exception e){

                student_button.setBackgroundResource(R.drawable.ic_person_black_24dp);

            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return view;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 90;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


}
