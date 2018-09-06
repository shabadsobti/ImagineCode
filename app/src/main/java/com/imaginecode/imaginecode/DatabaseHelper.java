package com.imaginecode.imaginecode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteAssetHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "imaginecode.db";


    private final Context myContext;

    private SQLiteDatabase myDataBase;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;

    }



    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + "Students");
        db.execSQL("DROP TABLE IF EXISTS " + "Modules");
        db.execSQL("DROP TABLE IF EXISTS " + "Lessons");
        db.execSQL("DROP TABLE IF EXISTS " + "Students_Lessons");
        // create new tables
        onCreate(db);
    }

    public long createStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", student.first_name);
        values.put("last_name", student.last_name);
        values.put("avatar", student.avatar);
        long student_id = db.insert("Students", null, values);

        return student_id;
    }

    public void editStudent(int student_id, String fname, String lname){
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE Students SET first_name = '" + fname + "', last_name = '" + lname + "' WHERE student_id = "+ student_id;

        db.execSQL(strSQL);
    }

    public boolean deleteStudent(int student_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Students",  "student_id = " + student_id, null) > 0;
    }

    public ArrayList<Student> getAllStudents(){

        ArrayList<Student> studentList = new ArrayList<Student>();

        String selectQuery = "SELECT * FROM " + "Students"
                + " ORDER BY " + "student_id" + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                Student student = new Student();
                student.id = cursor.getInt(0);
                student.first_name = cursor.getString(1);
                student.last_name = cursor.getString(2);
                student.avatar = cursor.getString(3);


                //Add movie details to list
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        db.close();
        return studentList;
    }


    public ArrayList<Module> getModules(){

        ArrayList<Module> moduleList = new ArrayList<Module>();
        String selectQuery = "SELECT module_id, "+  myContext.getResources().getString(R.string.module_name_column) +" FROM Modules";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                Module module = new Module();
                module.setID(cursor.getInt(0));
                module.setName(cursor.getString(1));
                moduleList.add(module);
            } while (cursor.moveToNext());
        }
        db.close();
        return moduleList;
    }


    public ArrayList<LessonClass> getLessons(int student_id, int module_id){

        ArrayList<LessonClass> lessonList = new ArrayList<LessonClass>();

        String selectQuery = "SELECT lesson_id, lesson_number, " + myContext.getResources().getString(R.string.lesson_instruction_column) + ", code" + " FROM Lessons WHERE module_id = " + module_id + " ORDER BY lesson_number ASC";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                // public LessonClass(int lesson_id, int number, int stars, String instructions){
                LessonClass lesson = new LessonClass(cursor.getInt(0), cursor.getInt(1), 0, cursor.getString(2), cursor.getString(3));

                lessonList.add(lesson);
            } while (cursor.moveToNext());
        }


        String secondQuery = "SELECT Lessons.lesson_id, Lessons.lesson_number, Lessons.module_id, Students_Lessons.student_id, Students_Lessons.stars FROM Lessons INNER JOIN Students_Lessons ON Lessons.lesson_id = Students_Lessons.lesson_id WHERE student_id = " + student_id + " AND module_id = " + module_id;
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor1 = db1.rawQuery(secondQuery, null);
        if (cursor1.moveToFirst()) {
            //Loop through the table rows
            do {
                for(int n = 0; n < lessonList.size(); n++){
                    if (lessonList.get(n).lesson_id == cursor1.getInt(0)){
                        lessonList.get(n).setStars(cursor1.getInt(4));
                    }
                }
            } while (cursor1.moveToNext());
        }

        db.close();
        return lessonList;
    }


    public void giveStars(Integer student_id, Integer lesson_id, Integer stars){
        SQLiteDatabase db = this.getWritableDatabase();
        int current_stars = 0;

        try {
            String query = "SELECT stars FROM Students_Lessons WHERE student_id = " + student_id + " AND lesson_id = " + lesson_id;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                current_stars = cursor.getInt(0);
            }
        }
        catch (Exception e){

        }
        if(stars >= current_stars && stars < 4){
            String sql = "INSERT or replace INTO Students_Lessons (student_id, lesson_id, stars) VALUES(" + student_id + "," + lesson_id + "," + stars + ")";
            db.execSQL(sql);
        }
        db.close();
    }


    public Integer getModuleID(Integer lesson_id){
        String query = "SELECT module_id FROM Lessons WHERE lesson_id = " + lesson_id;

        SQLiteDatabase db = this.getReadableDatabase();
        int module_id = 1;

        try{
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                module_id = cursor.getInt(0);
            }
        }
        catch (Exception e){

        }
        return module_id;
    }

    public String[] getName(Integer student_id){
        String query = "SELECT first_name, last_name FROM Students WHERE student_id = " + student_id;

        SQLiteDatabase db = this.getReadableDatabase();
        String fname = "";
        String lname = "";

        try{
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                fname = cursor.getString(0);
                lname = cursor.getString(1);
            }
        }
        catch (Exception e){

        }
        String[] A = {fname, lname};
        return A;
    }


    public Integer getLessonID(Integer module_id, Integer lesson_number){

        String query = "SELECT lesson_id FROM Lessons WHERE module_id = " + module_id + " AND lesson_number = " + lesson_number;
        SQLiteDatabase db = this.getReadableDatabase();

        int lesson_id = 1;

        try{
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                lesson_id = cursor.getInt(0);
            }
        }
        catch (Exception e){

        }
        return lesson_id;
    }


    public String getLessonInstructions(Integer lesson_id){
        String query = "SELECT " + myContext.getResources().getString(R.string.lesson_instruction_column) +" FROM Lessons WHERE lesson_id = " + lesson_id ;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("TSR", query);

        String instructions = "";

        try{
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                instructions = cursor.getString(0);
            }
        }
        catch (Exception e){

        }
        return instructions;

    }


    public ArrayList<InstructionsPage> getInstructionPages(int lesson_id){
        ArrayList<InstructionsPage> pages = new ArrayList<>();
        String query = "SELECT " + myContext.getResources().getString(R.string.intro_instruction_column) + ", "+myContext.getResources().getString(R.string.circuit_instruction_column)+", " + myContext.getResources().getString(R.string.learning_instruction_column) + ", type FROM Lessons WHERE lesson_id = " + lesson_id ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                String type = cursor.getString(3);
                if (type.equals("learning")){
                    pages.add(new InstructionsPage(cursor.getString(2), "learning_instructions", 1));
                }
                else{
                    pages.add(new InstructionsPage(cursor.getString(0), "intro_instructions", 1));
                    pages.add(new InstructionsPage(cursor.getString(1), "circuit_instructions"));
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();




        return  pages;
    }



    public String getLessonType(int lesson_id){

        String query = "SELECT type FROM Lessons WHERE lesson_id = " + lesson_id ;
        SQLiteDatabase db = this.getReadableDatabase();
        String type = "";

        try{
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                type = cursor.getString(0);
            }
        }
        catch (Exception e){

        }


        return type;
    }


    public Integer lessonGetStars(int student_id, int lesson_id){


        String query = "SELECT stars FROM Students_Lessons WHERE student_id = " + student_id + " AND lesson_id = " + lesson_id;
        SQLiteDatabase db = this.getReadableDatabase();

        int stars = 0;

        try{
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                stars = cursor.getInt(0);
            }
        }
        catch (Exception e){

        }
        return stars;


    }




}
