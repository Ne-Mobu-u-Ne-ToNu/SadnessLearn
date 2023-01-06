package com.example.sadnesslearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.SettingsHelper;

import java.util.Objects;

public class TaskTypeChoose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingsHelper.themeExists(this)) {
            setTheme(SettingsHelper.getThemeFromPrefs(this));
        }
        setContentView(R.layout.activity_task_type_choose);

        Toolbar tlb_choose_task_type = findViewById(R.id.tlb_choose_task_type);
        setSupportActionBar(tlb_choose_task_type);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        Intent intent = new Intent(TaskTypeChoose.this, TaskList.class);

        LinearLayout lin_lay_btn_code = findViewById(R.id.lin_lay_btn_choose_task_code);
        lin_lay_btn_code.setOnClickListener(view -> {
            intent.putExtra("task_type", "code");
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });

        LinearLayout lin_lay_btn_block = findViewById(R.id.lin_lay_btn_choose_task_block);
        lin_lay_btn_block.setOnClickListener(view -> {
            intent.putExtra("task_type", "block");
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });
    }
}