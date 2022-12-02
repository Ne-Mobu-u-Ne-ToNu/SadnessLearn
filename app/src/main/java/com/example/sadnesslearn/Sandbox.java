package com.example.sadnesslearn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.amrdeveloper.codeview.CodeView;

import java.util.Objects;

public class Sandbox extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        Toolbar tlb_sandBox = findViewById(R.id.tlb_sandBox);
        setSupportActionBar(tlb_sandBox);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CodeView code = findViewById(R.id.cv_sandbox);
        code.setTypeface(getResources().getFont(R.font.comicsansms));
        code.setEnableLineNumber(true);
        code.setLineNumberTextSize(code.getTextSize());
        code.setEnableHighlightCurrentLine(true);
        code.setHighlightCurrentLineColor(getResources().getColor(R.color.grey));
        code.setLineNumberTypeface(getResources().getFont(R.font.comicsansms));
    }
}