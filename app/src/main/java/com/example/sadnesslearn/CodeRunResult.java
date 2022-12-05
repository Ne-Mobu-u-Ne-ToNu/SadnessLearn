package com.example.sadnesslearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class CodeRunResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_run_result);

        Toolbar tlb_code_run = findViewById(R.id.tlb_code_run);
        setSupportActionBar(tlb_code_run);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        String result = getIntent().getStringExtra("result");
        TextView tv_run_code = findViewById(R.id.tv_run_code);
        tv_run_code.setText(result);
    }
}