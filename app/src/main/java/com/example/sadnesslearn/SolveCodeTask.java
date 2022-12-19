package com.example.sadnesslearn;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.amrdeveloper.codeview.CodeView;
import com.example.sadnesslearn.classes.JavaSyntaxManager;
import com.example.sadnesslearn.solveCodeFragments.SolveCodeAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class SolveCodeTask extends AppCompatActivity {
    private TabLayout tl_solve_code_task;
    private ViewPager2 vp2_solve_code_task;
    private SolveCodeAdapter sca;
    private String task_text, task_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_code_task);
        init();
        implementingNavigation();
    }

    void init(){
        Toolbar tlb_solve_code_task = findViewById(R.id.tlb_solve_code_task);
        setSupportActionBar(tlb_solve_code_task);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        tl_solve_code_task = findViewById(R.id.tl_solve_code_task);
        vp2_solve_code_task = findViewById(R.id.vp2_solve_code_task);
        task_text = getIntent().getStringExtra("task_text");
        task_code = getIntent().getStringExtra("task_code");
        task_code = task_code.replace("\\n", "\n");
        sca = new SolveCodeAdapter(this, task_text, task_code);
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