package com.example.sadnesslearn.activities.solveCodeFragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amrdeveloper.codeview.CodeView;
import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.Constants;
import com.example.sadnesslearn.classes.languages.CompilerApi;
import com.example.sadnesslearn.classes.languages.JavaSyntaxManager;
import com.example.sadnesslearn.classes.languages.SyntaxManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskSolve extends Fragment {
    private String task_code;
    private final String task_test;
    private final String SOLVED = "Задание выполнено!";

    public TaskSolve(String task_code, String task_test){
        this.task_code = task_code;
        this.task_test = task_test;
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_solve, container, false);
        SyntaxManager syntaxManager = new JavaSyntaxManager();

        TextView tv_result = view.findViewById(R.id.tv_result);

        CodeView cv_code = view.findViewById(R.id.cv_code_task_solve);
        syntaxManager.applyCodeTheme(this.getContext(), cv_code);
        cv_code.setText(task_code);

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_layout));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        FloatingActionButton fab_run = view.findViewById(R.id.fab_code_task_solve_run);
        fab_run.setOnClickListener(view1 -> {
            String buf = cv_code.getText().toString();
            task_code = buf.substring(0, buf.lastIndexOf('}')) + task_test;
            tv_result.setText(CompilerApi.compileAndRun(task_code, syntaxManager.getLanguage(), syntaxManager.getVersionIndex()));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        return view;
    }
}