package com.imaginecode.imaginecode;

import java.util.ArrayList;

public class LessonClass {

    public Integer lesson_id;
    private Integer number;
    private int stars;
    private String instructions;


    public LessonClass(int lesson_id, int number, int stars, String instructions){
        this.lesson_id = lesson_id;
        this.number = number;
        this.stars = stars;
        this.instructions = instructions;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }



}
