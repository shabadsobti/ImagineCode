package com.imaginecode.imaginecode;

import java.util.ArrayList;

public class Module {
    public Integer module_id;
    private String module_name;


    public Module(){


    }

    public String getName(){
        return module_name;
    }

    public void setID(int number) {
        this.module_id= number;
    }





    public void setName(String name) {
        this.module_name = name;
    }




}
