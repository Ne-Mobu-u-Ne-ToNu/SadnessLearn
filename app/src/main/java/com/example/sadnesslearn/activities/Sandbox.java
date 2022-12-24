package com.example.sadnesslearn.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import com.amrdeveloper.codeview.CodeView;
import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.Constants;
import com.example.sadnesslearn.classes.languages.CPPSyntaxManager;
import com.example.sadnesslearn.classes.languages.CSharpSyntaxManager;
import com.example.sadnesslearn.classes.languages.CompilerApi;
import com.example.sadnesslearn.classes.languages.GoLangSyntaxManager;
import com.example.sadnesslearn.classes.languages.JavaSyntaxManager;
import com.example.sadnesslearn.classes.languages.SyntaxManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class Sandbox extends AppCompatActivity {
    private static SyntaxManager syntaxManager;
    private static String initCode, language, versionIndex, lang;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        Toolbar tlb_sandBox = findViewById(R.id.tlb_sandBox);
        setSupportActionBar(tlb_sandBox);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if(getIntent().getStringExtra("language") != null) {
            lang = getIntent().getStringExtra("language");
            initLang(lang);
        }

        CodeView code = findViewById(R.id.cv_sandbox);
        code.setText(initCode);
        syntaxManager.applyCodeTheme(this, code);

        FloatingActionButton fab_sanbox_run = findViewById(R.id.fab_sanbox_run);
        fab_sanbox_run.setOnClickListener(view -> {
            String codeToRun = code.getText().toString();
            initCode = codeToRun;
            try {
                if(codeToRun.length() != 0){
                    Intent intent = new Intent(Sandbox.this, CodeRunResult.class);
                    String result = CompilerApi.compileAndRun(codeToRun, language, versionIndex);
                    intent.putExtra("result", result);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } else {
                    Toast.makeText(this, "Введите свой код!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initLang(String lang) {
        switch (lang) {
            case "C#":
                syntaxManager = new CSharpSyntaxManager();
                break;
            case "C++":
                syntaxManager = new CPPSyntaxManager();
                break;
            case "GoLang":
                syntaxManager = new GoLangSyntaxManager();
                break;
            default:
                syntaxManager = new JavaSyntaxManager();
                break;
        }
        language = syntaxManager.getLanguage();
        versionIndex = syntaxManager.getVersionIndex();
        initCode = syntaxManager.getInitCode();
    }
}