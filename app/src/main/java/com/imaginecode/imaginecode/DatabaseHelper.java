package com.imaginecode.imaginecode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {


    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "ImagineCode";

    // Table Names
    private static final String TABLE_STUDENTS = "Students";
    private static final String TABLE_MODULE = "Modules";
    private static final String TABLE_LESSON = "Lessons";
    private static final String TABLE_STUDENT_MODULE = "Students_Module";
    private static final String TABLE_STUDENT_MODULES_LESSONS = "Students_Modules_Lessons";


    // Common column names

    // Students Table - column names

    private static final String KEY_FNAME = "first_name";
    private static final String KEY_LNAME = "last_name";
    private static final String KEY_AVATAR = "avatar";

    //Module Table - column Names

    private static final String KEY_MODULE_NAME = "module_name";
    private static final String KEY_MODULE_OPEN = "module_open";

    //Lesson Table - column Names
    private static final String KEY_LESSON_INSTUCTIONS= "lesson_instructions";
    private static final String KEY_LESSON_NUMBER= "lesson_numbejr";




    // STUDENT_MODULES_LESSONS  - column names
    private static final String KEY_STARS = "stars";
    private static final String KEY_MODULE_UNLOCKED = "module_unlocked";


    // COMMON Column Names
    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_MODULE_ID = "module_id";
    private static final String KEY_LESSON_ID= "lesson_id";


    // Table Create Statements
    // STUDENT TABLE
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_STUDENTS + "(" + KEY_STUDENT_ID + " INTEGER PRIMARY KEY," + KEY_FNAME
            + " TEXT," + KEY_LNAME + " TEXT," + KEY_AVATAR + " TEXT" + ")";


    //MODULE TABLE
    private static final String CREATE_TABLE_MODULES = "CREATE TABLE "
            + TABLE_MODULE + "(" + KEY_MODULE_ID + " INTEGER PRIMARY KEY, " + KEY_MODULE_NAME
            + " TEXT, " + KEY_MODULE_OPEN + " INTEGER" + ")";


    //LESSON TABLE
    private static final String CREATE_TABLE_LESSONS = "CREATE TABLE "
            + TABLE_LESSON + "(" + KEY_LESSON_ID + " INTEGER PRIMARY KEY, " +  KEY_LESSON_NUMBER + " INTEGER, " + KEY_LESSON_INSTUCTIONS + " TEXT" + ")";




    // STUDENT_MODULE
    private static final String CREATE_TABLE_STUDENT_MODULE = "CREATE TABLE "
            + TABLE_STUDENT_MODULE + "(" + KEY_STUDENT_ID + " INTEGER ," + KEY_MODULE_ID
            + " INTEGER" + ")";




    // STUDENT_LESSON TABLE
    private static final String CREATE_TABLE_STUDENT_MODULE_LESSON = "CREATE TABLE "
            + TABLE_STUDENT_MODULES_LESSONS + "(" + KEY_STUDENT_ID + " INTEGER, " + KEY_LESSON_ID + "INTEGER," + KEY_STARS + "INTEGER" + ")";






    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_MODULES);
        db.execSQL(CREATE_TABLE_LESSONS);
        db.execSQL(CREATE_TABLE_STUDENT_MODULE);
        db.execSQL(CREATE_TABLE_STUDENT_MODULE_LESSON);


        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_MODULE_NAME, "Introduction");
        initialValues.put(KEY_MODULE_OPEN, 1);
        db.insert(TABLE_MODULE, null, initialValues);


        ContentValues second = new ContentValues();
        second.put(KEY_MODULE_NAME, "Solar Module");
        second.put(KEY_MODULE_OPEN, 1);
        db.insert(TABLE_MODULE, null, second);


    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LESSON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT_MODULES_LESSONS);
        // create new tables
        onCreate(db);
    }

    public long createStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, student.first_name);
        values.put(KEY_LNAME, student.last_name);
        values.put(KEY_AVATAR, student.avatar);
        long student_id = db.insert(TABLE_STUDENTS, null, values);

        return student_id;
    }

    public ArrayList<Student> getAllStudents(){

        ArrayList<Student> studentList = new ArrayList<Student>();

        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS
                + " ORDER BY " + KEY_STUDENT_ID + " DESC";

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
        String selectQuery = "SELECT module_id, module_name FROM Modules";


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





}
