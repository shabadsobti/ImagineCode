package com.imaginecode.imaginecode;

import java.util.ArrayList;

public class LessonClass {

    private String number;
    private int stars;
    private boolean unlocked;

    public LessonClass(String number, int stars, boolean unlocked){
        this.number = number;
        this.stars = stars;
        this.unlocked = unlocked;

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

    public boolean getUnlocked() {return unlocked;}

    public void setUnlocked(boolean unlocked) {this.unlocked = unlocked;}

    public static ArrayList<LessonClass> generateInitialLessons(){
        ArrayList<LessonClass> lessons = new ArrayList<LessonClass>();
        lessons.add(new LessonClass("1", 3, true));
        lessons.add(new LessonClass("2", 2, true));
        lessons.add(new LessonClass("3", 2, true));
        lessons.add(new LessonClass("4", 0, true));
        lessons.add(new LessonClass("5", 0, false));
        lessons.add(new LessonClass("6", 0, false));
        lessons.add(new LessonClass("7", 0, false));
        lessons.add(new LessonClass("8", 0, false));
        lessons.add(new LessonClass("9", 0, false));
        lessons.add(new LessonClass("10", 0, false));
        lessons.add(new LessonClass("11", 0, false));
        lessons.add(new LessonClass("12", 0, false));
        lessons.add(new LessonClass("13", 0, false));
        lessons.add(new LessonClass("14", 0, false));
        lessons.add(new LessonClass("15", 0, false));
        lessons.add(new LessonClass("16", 0, false));
        return lessons;
    }
}
