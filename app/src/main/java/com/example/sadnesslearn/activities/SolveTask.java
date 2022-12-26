package com.example.sadnesslearn.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.activities.solveCodeFragments.SolveAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class SolveTask extends AppCompatActivity {
    private TabLayout tl_solve_code_task;
    private ViewPager2 vp2_solve_code_task;
    private SolveAdapter sca;
    private String task_text, task_code, task_test, task_id, task_options;
    private static String taskType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_task);
        init();
        scenario(taskType);
        implementingNavigation();
    }

    private void init(){
        Toolbar tlb_solve_code_task = findViewById(R.id.tlb_solve_code_task);
        setSupportActionBar(tlb_solve_code_task);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if(getIntent().getStringExtra("task_type") != null)
            taskType = getIntent().getStringExtra("task_type");
        tl_solve_code_task = findViewById(R.id.tl_solve_code_task);
        vp2_solve_code_task = findViewById(R.id.vp2_solve_code_task);
        task_text = getIntent().getStringExtra("task_text");
        task_id = getIntent().getStringExtra("task_id");
        task_code = getIntent().getStringExtra("task_code");
        task_code = task_code.replace("\\n", "\n");
        task_test = getIntent().getStringExtra("task_test");
    }

    private void initCode() {
        sca = new SolveAdapter(this, task_text, task_code, task_test, task_id);
    }

    private void initBlock() {
        task_options = getIntent().getStringExtra("task_options");
        sca = new SolveAdapter(this, task_text, task_code, task_test, task_id, task_options);
    }

    private void scenario(String taskType) {
        switch (taskType) {
            case "code":
                initCode();
                break;
            case "block":
                initBlock();
                break;
        }
        vp2_solve_code_task.setAdapter(sca);
    }

    private void implementingNavigation(){
        tl_solve_code_task.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp2_solve_code_task.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vp2_solve_code_task.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onPageSelected(int position) {
                tl_solve_code_task.selectTab(tl_solve_code_task.getTabAt(position));
            }
        });
    }

}