package com.example.sadnesslearn.solveCodeFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SolveCodeAdapter extends FragmentStateAdapter {
    String code_text, task_text;
    public SolveCodeAdapter(@NonNull FragmentActivity fragmentActivity, String task_text, String code_text) {
        super(fragmentActivity);
        this.code_text = code_text;
        this.task_text = task_text;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new TaskSolve(code_text);
        }
        return new TaskText(task_text);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
