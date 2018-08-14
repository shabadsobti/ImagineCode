package com.imaginecode.imaginecode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "imaginecode.db";

    String DB_PATH = null;

    private final Context myContext;

    private SQLiteDatabase myDataBase;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DB_PATH= "/data/data/" + context.getPackageName()+"/"+"databases/";
    }


    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }


    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();

        }catch(SQLiteException e){
            //database does't exist yet.
        }
        return checkDB != null;
    }



    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */

    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        Log.d("TAG", outFileName);

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[20000];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    public void openDataBase() throws SQLiteException{
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


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


    public int[] getGraphics(Integer lesson_id){

        String query = "SELECT graphic FROM Lessons WHERE lesson_id = " + lesson_id ;
        SQLiteDatabase db = this.getReadableDatabase();
        String graphics = "";

        try{
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                graphics = cursor.getString(0);
            }
        }
        catch (Exception e){

        }

        String[] listGraphics = graphics.replaceAll("\\s","").split(",");




        int[] drawables = new int[listGraphics.length];

        int i = 0;

        for (String img: listGraphics)
        {
            Log.d("DETAILS", img);
            drawables[i] = myContext.getResources().getIdentifier(img, "drawable", myContext.getPackageName());
            i++;
        }

        return drawables;

    }


    public ArrayList<InstructionsPage> getInstructionPages(int lesson_id){
        ArrayList<InstructionsPage> pages = new ArrayList<>();
        String query = "SELECT intro_instructions, circuit_instructions, learning_instructions, type FROM Lessons WHERE lesson_id = " + lesson_id ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                String type = cursor.getString(3);
                if (type == "learning"){
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



}
