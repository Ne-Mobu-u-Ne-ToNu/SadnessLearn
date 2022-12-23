package com.example.sadnesslearn.classes;

public class TaskItem {
    private String name;
    private boolean isSolved;

    public TaskItem(String name, boolean isSolved){
        this.name = name;
        this.isSolved = isSolved;
    }

    public String getName() { return this.name; }

    public boolean getIsSolved() { return this.isSolved; }
}
