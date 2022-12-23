package com.example.sadnesslearn.classes;

public class Comics {
    String id, link;
    int number;

    public Comics(){

    }

    public Comics(String id, String link, int number){
        this.id = id;
        this.link = link;
        this.number = number;
    }

    public String getId() { return this.id; }

    public String getLink() { return this.link; }

    public int getNumber() { return this.number; }
}
