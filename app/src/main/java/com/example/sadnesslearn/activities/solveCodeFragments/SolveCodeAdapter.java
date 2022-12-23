package com.example.sadnesslearn.activities.solveCodeFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SolveCodeAdapter extends FragmentStateAdapter {
    String code_text, task_text, task_test;
    public SolveCodeAdapter(@NonNull FragmentActivity fragmentActivity, String task_text, String code_text,
                            String task_test) {
        super(fragmentActivity);
        this.code_text = code_text;
        this.task_text = task_text;
        this.task_test = task_test;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new TaskSolve(code_text, task_test);
        }
        return new TaskText(task_text);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
