package com.imaginecode.imaginecode;

public class InstructionsPage {

    private String instructions;
    private int image;
    private String type;


    InstructionsPage(String instructions, String type, int image){
        this.instructions = instructions;
        this.type = type;
        this.image = image;
    }

    InstructionsPage(String instructions, String type){
        this.instructions = instructions;
        this.type = type;
    }


    public String getInstructions() {
        return instructions;
    }

    public String getType() {
        return type;
    }

    public int getImage() {
        return image;
    }
}
