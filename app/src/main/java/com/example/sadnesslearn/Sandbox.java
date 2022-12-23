package com.example.sadnesslearn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amrdeveloper.codeview.CodeView;
import com.example.sadnesslearn.classes.JavaCompilerApi;
import com.example.sadnesslearn.classes.JavaSyntaxManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class Sandbox extends AppCompatActivity {
    private static String initCode = "public class Main {\n" +
            "    public static void main(String[] args) {\n        \n    }\n}";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        Toolbar tlb_sandBox = findViewById(R.id.tlb_sandBox);
        setSupportActionBar(tlb_sandBox);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CodeView code = findViewById(R.id.cv_sandbox);
        code.setText(initCode);
        JavaSyntaxManager.applyJavaCodeTheme(this, code);

        FloatingActionButton fab_sanbox_run = findViewById(R.id.fab_sanbox_run);
        fab_sanbox_run.setOnClickListener(view -> {
            String codeToRun = code.getText().toString();
            initCode = codeToRun;
            try {
                if(codeToRun.length() != 0){
                    Intent intent = new Intent(Sandbox.this, CodeRunResult.class);
                    String result = JavaCompilerApi.compileAndRun(codeToRun);
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
}