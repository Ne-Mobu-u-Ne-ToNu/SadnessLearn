package com.example.sadnesslearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.SettingsHelper;

import java.util.Objects;

public class CodeRunResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingsHelper.themeExists(this)) {
            setTheme(SettingsHelper.getThemeFromPrefs(this));
        }
        setContentView(R.layout.activity_code_run_result);

        Toolbar tlb_code_run = findViewById(R.id.tlb_code_run);
        setSupportActionBar(tlb_code_run);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        String result = getIntent().getStringExtra("result");
        TextView tv_run_code = findViewById(R.id.tv_run_code);
        tv_run_code.setText(result);
    }
}