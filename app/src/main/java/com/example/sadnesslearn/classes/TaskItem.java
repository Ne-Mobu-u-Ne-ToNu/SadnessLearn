package com.example.sadnesslearn.classes;

public class TaskItem {
    private final String name;
    private final boolean isSolved;

    public TaskItem(String name, boolean isSolved){
        this.name = name;
        this.isSolved = isSolved;
    }

    public String getName() { return this.name; }

    public boolean getIsSolved() { return this.isSolved; }
}
