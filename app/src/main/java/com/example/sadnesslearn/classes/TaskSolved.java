package com.example.sadnesslearn.classes;

public class TaskSolved {
    private int number;
    private boolean isSolved;

    public TaskSolved() {}

    public TaskSolved(int number, boolean isSolved) {
        this.number = number;
        this.isSolved = isSolved;
    }

    public int getNumber() {
        return this.number;
    }

    public boolean getIsSolved() {
        return this.isSolved;
    }
}
