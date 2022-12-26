package com.example.sadnesslearn.classes;

public class BlockTask {
    private String code, id, name, options, test, text;
    private boolean isSolved;
    private int number;

    public BlockTask() {}

    public BlockTask(String code, String id, boolean isSolved, String name, int number,
                     String options, String test, String text) {
        this.code = code;
        this.id = id;
        this.isSolved = isSolved;
        this.name = name;
        this.number = number;
        this.options = options;
        this.test = test;
        this.text = text;
    }

    public String getCode() { return this.code; }

    public String getId() { return this.id; }

    public boolean getIsSolved() { return this.isSolved; }

    public String getName() { return this.name; }

    public int getNumber() { return this.number; }

    public String getOptions() { return this.options; }

    public String getTest() { return this.test; }

    public String getText() { return this.text; }
}
