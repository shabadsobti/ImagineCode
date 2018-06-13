package com.imaginecode.imaginecode;

/**
 * Created by shabadsobti on 6/12/18.
 */

public class Student {

    int id;
    String first_name;
    String last_name;
    String avatar;


    public Student(){

    }

    public Student(String first_name, String last_name, String avatar) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

    public Student(int id, String first_name, String last_name, String avatar) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

}
