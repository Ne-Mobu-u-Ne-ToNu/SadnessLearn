package com.example.sadnesslearn.classes.languages;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;

public interface SyntaxManager {
    void applyCodeTheme(Context context, CodeView code);
    String getInitCode();
    String getLanguage();
    String getVersionIndex();
}
