package com.imaginecode.imaginecode;

import java.util.ArrayList;

public class LessonClass {

    private String number;
    private int stars;

    public LessonClass(String number, int stars){
        this.number = number;
        this.stars = stars;

    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public static ArrayList<LessonClass> generateInitialLessons(){
        ArrayList<LessonClass> lessons = new ArrayList<LessonClass>();
        lessons.add(new LessonClass("1", 3));
        lessons.add(new LessonClass("2", 2));
        lessons.add(new LessonClass("3", 2));
        lessons.add(new LessonClass("4", 0));
        lessons.add(new LessonClass("5", 0));
        lessons.add(new LessonClass("6", 0));
        lessons.add(new LessonClass("7", 0));
        lessons.add(new LessonClass("8", 0));
        return lessons;
    }
}
