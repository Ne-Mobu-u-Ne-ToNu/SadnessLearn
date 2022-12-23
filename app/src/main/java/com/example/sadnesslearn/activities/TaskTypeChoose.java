package com.example.sadnesslearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.sadnesslearn.R;

import java.util.Objects;

public class TaskTypeChoose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_type_choose);

        Toolbar tlb_choose_task_type = findViewById(R.id.tlb_choose_task_type);
        setSupportActionBar(tlb_choose_task_type);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Button btn_code = findViewById(R.id.btn_choose_task_code);
        btn_code.setOnClickListener(view -> {
            startActivity(new Intent(TaskTypeChoose.this, TaskList.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });
    }
}