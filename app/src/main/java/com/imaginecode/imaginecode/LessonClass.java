package com.imaginecode.imaginecode;

import java.util.ArrayList;

public class LessonClass {

    public Integer lesson_id;
    private Integer number;
    private int stars;
    private String instructions;
    private String correct_code;
    public boolean isLocked;


    public LessonClass(int lesson_id, int number, int stars, String instructions, String correctCode){
        this.lesson_id = lesson_id;
        this.number = number;
        this.stars = stars;
        this.instructions = instructions;
        this.correct_code = correctCode;
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

    public String getInstructions() {
        return instructions;
    }

    public String getCorrectCode() {
        return correct_code;
    }


    public void setCorrectCode(String correct_code) {
        this.correct_code = correct_code;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }



}
