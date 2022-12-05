package com.example.sadnesslearn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;

import com.amrdeveloper.codeview.CodeView;
import com.example.sadnesslearn.classes.JavaSyntaxManager;
import java.util.Objects;

public class Sandbox extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        Toolbar tlb_sandBox = findViewById(R.id.tlb_sandBox);
        setSupportActionBar(tlb_sandBox);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ImageButton ib_sanbox_run = findViewById(R.id.ib_sanbox_run);
        ib_sanbox_run.setOnClickListener(view -> {
            startActivity(new Intent(Sandbox.this, CodeRunResult.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });

        CodeView code = findViewById(R.id.cv_sandbox);
        JavaSyntaxManager.applyJavaCodeTheme(this, code);
    }
}