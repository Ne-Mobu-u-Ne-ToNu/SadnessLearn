package com.example.sadnesslearn.classes.Tasks;

import com.example.sadnesslearn.classes.languages.LocaleLanguages;

public class Task {
    public String code, id, nameRu, nameEn, test, textRu, textEn;

    private String name, text;
    private boolean isSolved;
    public int number;

    public Task(){
        this.isSolved = false;
    }

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
