package com.example.sadnesslearn.classes;

import com.example.sadnesslearn.classes.languages.LocaleLanguages;

public class BlockTask {
    public String code, id, nameRu, nameEn, options, test, textRu, textEn;
    private String name, text;
    private boolean isSolved;
    public int number;

    public BlockTask() {}

    public boolean getIsSolved() { return this.isSolved; }

    public void setIsSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

    public String getName() {
        return this.name;
    }
    public void setName(LocaleLanguages locale) {
        switch (locale) {
            case ENGLISH:
                name = nameEn;
                break;
            default:
                name = nameRu;
                break;
        }
    }

    public String getText() {
        return this.text;
    }

    public void setText(LocaleLanguages locale) {
        switch (locale) {
            case ENGLISH:
                text = textEn;
                break;
            default:
                text = textRu;
                break;
        }
    }
}
