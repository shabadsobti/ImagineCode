package com.imaginecode.imaginecode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {


    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ImagineCode";

    // Table Names
    private static final String TABLE_STUDENTS = "Students";
    private static final String TABLE_STUDENT_MODULES = "Students_Modules";
    private static final String TABLE_STUDENT_PROGRESS = "Student_Progress";

    // Common column names



    // Students Table - column names
    private static final String KEY_STUDENT_ID = "id";
    private static final String KEY_FNAME = "first_name";
    private static final String KEY_LNAME = "last_name";

    // STUDENT_MODULES Table - column names
    private static final String KEY_MODULE_ID = "id";
    private static final String KEY_STUDENT_FOREIGN = "student_id";
    private static final String KEY_MODULE = "module";

    // STUDENT_PROGRESS Table - column names
    private static final String KEY_FOREIGN_MODULE_ID = "student_module_id";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_STARS = "stars";




    // Table Create Statements
    // Students table create statement
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_STUDENTS + "(" + KEY_STUDENT_ID + " INTEGER PRIMARY KEY," + KEY_FNAME
            + " TEXT," + KEY_LNAME + " TEXT" + ")";


    // Tag table create statement
    private static final String CREATE_TABLE_STUDENT_MODULES = "CREATE TABLE " + TABLE_STUDENT_MODULES
            + "(" + KEY_MODULE_ID + " INTEGER PRIMARY KEY," + KEY_STUDENT_FOREIGN + " INTEGER,"
            + KEY_MODULE + " INTEGER" + ")";



    // todo_tag table create statement
    private static final String CREATE_TABLE_STUDENT_PROGRESS = "CREATE TABLE "
            + TABLE_STUDENT_PROGRESS + "(" + KEY_FOREIGN_MODULE_ID + " INTEGER,"
            + KEY_LEVEL + " INTEGER," + KEY_STARS + " INTEGER" + ")";





    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_STUDENT_MODULES);
        db.execSQL(CREATE_TABLE_STUDENT_PROGRESS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT_MODULES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT_PROGRESS);

        // create new tables
        onCreate(db);
    }

    public long createStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, student.first_name);
        values.put(KEY_LNAME, student.last_name);
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


                //Add movie details to list
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        db.close();
        return studentList;



    }
}
