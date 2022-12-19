package com.example.sadnesslearn.solveCodeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sadnesslearn.R;

public class TaskText extends Fragment {
    private String task_text;

    public TaskText(String task_text){
        this.task_text = task_text;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_text, container, false);
        TextView tv_task = view.findViewById(R.id.tv_fragment_solve_task_text);
        tv_task.setText(task_text);
        return view;
    }
}