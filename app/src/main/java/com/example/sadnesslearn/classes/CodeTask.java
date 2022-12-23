package com.example.sadnesslearn.classes;

public class CodeTask {
    private String code, id, name, test, text;
    private int number;

    public CodeTask(){

    }

    public CodeTask(String code, String id, String name, int number, String test, String text){
        this.code = code;
        this.id = id;
        this.name = name;
        this.number = number;
        this.test = test;
        this.text = text;
    }

    public String getCode() { return this.code; }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getText(){
        return this.text;
    }

    public String getTest() { return this.test; }

    public int getNumber(){
        return this.number;
    }
}
