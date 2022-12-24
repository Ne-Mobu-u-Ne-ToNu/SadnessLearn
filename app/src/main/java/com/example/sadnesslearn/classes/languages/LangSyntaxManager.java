package com.example.sadnesslearn.classes.languages;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.amrdeveloper.codeview.Code;
import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeview.Keyword;
import com.example.sadnesslearn.R;

import java.util.List;

public class LangSyntaxManager {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected static void applyOtherFeatures(Context context, CodeView code){
        code.setTypeface(context.getResources().getFont(R.font.comicsansms));
        code.setEnableLineNumber(true);
        code.setLineNumberTextSize(code.getTextSize());
        code.setEnableHighlightCurrentLine(true);
        code.setHighlightCurrentLineColor(context.getResources().getColor(R.color.grey));
        code.setLineNumberTypeface(context.getResources().getFont(R.font.comicsansms));
        code.resetSyntaxPatternList();
    }

    //Array of keywords
    private static String[] getKeywords(Context context, int resArray){
        return context.getResources().getStringArray(resArray);
    }

    //Adds keywords to a list
    protected static void addKeywords(Context context, List<Code> codes, int resArray){
        String[] keyWords = getKeywords(context, resArray);

        for(String s : keyWords){
            codes.add(new Keyword(s));
        }
    }
}
