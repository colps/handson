package com.tagrem.prizy.command;


public class Void {

    public static final Void INSTANCE = new Void();

    public static Void newInstance () {
        return INSTANCE;
    }

    public String getResult(){
        return "";    }

}
