package com.example.sadnesslearn.classes;

public class InterfaceLangItem {
    private final String langName;
    private final int imageId;

    public InterfaceLangItem(String langName, int imageId) {
        this.langName = langName;
        this.imageId = imageId;
    }

    public String getLangName() { return this.langName; }

    public int getImageId() { return this.imageId; }
}
