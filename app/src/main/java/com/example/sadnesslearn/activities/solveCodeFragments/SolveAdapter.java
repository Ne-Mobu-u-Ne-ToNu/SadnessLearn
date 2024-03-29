package com.example.sadnesslearn.activities.solveCodeFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SolveAdapter extends FragmentStateAdapter {
    String code_text, task_text, task_test, task_id, task_options;
    int task_number;
    public SolveAdapter(@NonNull FragmentActivity fragmentActivity, String task_text, String code_text,
                        String task_test, String task_id, int task_number) {
        super(fragmentActivity);
        this.code_text = code_text;
        this.task_text = task_text;
        this.task_test = task_test;
        this.task_id = task_id;
        task_options = null;
        this.task_number = task_number;
    }

    public SolveAdapter(@NonNull FragmentActivity fragmentActivity, String task_text, String code_text,
                        String task_test, String task_id, String task_options, int task_number) {
        super(fragmentActivity);
        this.code_text = code_text;
        this.task_text = task_text;
        this.task_test = task_test;
        this.task_id = task_id;
        this.task_options = task_options;
        this.task_number = task_number;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new TaskSolve(code_text, task_test, task_id, task_options, task_number);
        }
        return new TaskText(task_text);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
