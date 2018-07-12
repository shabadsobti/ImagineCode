package com.imaginecode.imaginecode;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class AddStudent extends AppCompatActivity {

    private ImageView mImage;
    private static final int CAMERA_PIC_REQUEST = 1111;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_add_student);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.headbar));
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.title_activity_add_student);

        final EditText fname = findViewById(R.id.first_name);
        final EditText lname = findViewById(R.id.last_name);

        Button add_user = findViewById(R.id.add_user);

        Button img = findViewById(R.id.updateImg);

        Context context = getApplicationContext();

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(context, "CAMERA ERROR", Toast.LENGTH_SHORT).show();
                //Show permission dialog
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1111);
            }
        }

        mImage = (ImageView) findViewById(R.id.avatarImg);





        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_PIC_REQUEST);

            }
        });







        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = fname.getText().toString();
                String last_name = lname.getText().toString();
                ImageView avatar =  findViewById(R.id.avatarImg);
                Bitmap thumbnail;

                try{
                     thumbnail = ((BitmapDrawable) avatar.getDrawable()).getBitmap();

                }
                catch (Exception e){
                    thumbnail = BitmapFactory.decodeResource(getResources(), R.drawable.ic_person_black_24dp);
                }


                Random rand = new Random();

                // Generate random integers in range 0 to 999
                int rand_int = rand.nextInt(1000);
                String unique_string = first_name + last_name + rand_int;

                String avatarString = "avatar_" + unique_string +".jpg";
                saveToInternalStorage(thumbnail, unique_string);

                myDb = new DatabaseHelper(getApplicationContext());

                Student a = new Student(first_name, last_name, avatarString);
                myDb.createStudent(a);
                myDb.close();
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);//Set result OK

                finish();//finish activity

            }
        });



    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mImage.setImageBitmap(thumbnail);
        }

    }

    private String saveToInternalStorage(Bitmap bitmapImage, String unique_string){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory,"avatar_" + unique_string +".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}

