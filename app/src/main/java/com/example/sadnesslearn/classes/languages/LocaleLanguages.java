package com.example.sadnesslearn.classes.languages;

public enum LocaleLanguages {
    RUSSIAN("ru"),
    ENGLISH("en");

    private final String title;

    LocaleLanguages (String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public static LocaleLanguages getLocale(String locale) {
        switch (locale) {
            case "en":
                return ENGLISH;
            default:
                return RUSSIAN;
        }
    }
}
