package com.example.sadnesslearn.activities.solveCodeFragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.amrdeveloper.codeview.CodeView;
import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.BottomSheetBehaviorNoSwipe;
import com.example.sadnesslearn.classes.Constants;
import com.example.sadnesslearn.classes.OptionRecyclerViewAdapter;
import com.example.sadnesslearn.classes.languages.CompilerApi;
import com.example.sadnesslearn.classes.languages.JavaSyntaxManager;
import com.example.sadnesslearn.classes.languages.SyntaxManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskSolve extends Fragment {
    private String task_code, task_key;
    private List<Integer> addedElementsFirst, addedElementsLast;
    private final String task_test, task_id, task_options;
    private final String SOLVED = "Задание выполнено!";
    private RecyclerView rv_solve;
    private OptionRecyclerViewAdapter adapter;
    private CodeView cv_code;

    public TaskSolve(String task_code, String task_test, String task_id, String task_options){
        this.task_code = task_code;
        this.task_test = task_test;
        this.task_id = task_id;
        this.task_options = task_options;
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_solve, container, false);
        SyntaxManager syntaxManager = new JavaSyntaxManager();

        TextView tv_result = view.findViewById(R.id.tv_result);

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        rv_solve = view.findViewById(R.id.rv_task_solve);

        cv_code = view.findViewById(R.id.cv_code_task_solve);
        syntaxManager.applyCodeTheme(this.getContext(), cv_code);
        cv_code.setText(task_code);

        task_key = null;

        if(task_options != null) {
            addedElementsFirst = new ArrayList<>();
            addedElementsLast = new ArrayList<>();
            fillOptions(view);
            cv_code.setEnabled(false);
            addDeleteSelectedOption();
            task_key = Constants.BLOCK_TASK_KEY;
        }
        else {
            task_key = Constants.CODE_TASK_KEY;
        }


        BottomSheetBehaviorNoSwipe<View> bottomSheetBehavior = (BottomSheetBehaviorNoSwipe<View>) BottomSheetBehaviorNoSwipe.from(view.findViewById(R.id.bottom_sheet_layout));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        FloatingActionButton fab_run = view.findViewById(R.id.fab_code_task_solve_run);
        FloatingActionButton fab_hide_dialog = view.findViewById(R.id.fab_code_task_solve_hide_dialog);

        fab_hide_dialog.setVisibility(View.GONE);
        fab_hide_dialog.setOnClickListener(view1 -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            fab_hide_dialog.setVisibility(View.GONE);
            fab_run.setVisibility(View.VISIBLE);
            rv_solve.setVisibility(View.VISIBLE);
        });

        fab_run.setOnClickListener(view1 -> {
            fab_run.setVisibility(View.GONE);
            rv_solve.setVisibility(View.GONE);
            fab_hide_dialog.setVisibility(View.VISIBLE);
            String buf = cv_code.getText().toString();
            task_code = buf.substring(0, buf.lastIndexOf('}')) + task_test;
            tv_result.setText(CompilerApi.compileAndRun(task_code, syntaxManager.getLanguage(), syntaxManager.getVersionIndex()));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            checkAndWriteSolved(task_key, task_id, tv_result.getText().toString());
        });

        return view;
    }

    private void checkAndWriteSolved(String task_key, String child, String codeResult){
        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
        boolean isSolved = codeResult.contains(SOLVED);

        mDataBase.child(task_key).child(child).child("isSolved").setValue(isSolved);
    }

    private void fillOptions(View v) {
        List<String> listOptions = new ArrayList<>(Arrays.asList(task_options.split(" ")));
        adapter = new OptionRecyclerViewAdapter(v.getContext(), listOptions);
        rv_solve.setAdapter(adapter);
        rv_solve.setLayoutManager(new LinearLayoutManager(v.getContext(), RecyclerView.HORIZONTAL, false));
    }

    private void addDeleteSelectedOption() {
        adapter.setClickListener((view, position) -> {
            String changedText = cv_code.getText().toString();
            int firstIndex = changedText.indexOf("___");

            if(firstIndex != -1) {
                if (!adapter.getItem(position).equals("Удалить")) {
                    addedElementsFirst.add(firstIndex);
                    addedElementsLast.add(firstIndex + adapter.getItem(position).length());
                    String after = changedText.substring(firstIndex + 3);
                    changedText = changedText.substring(0, firstIndex) + adapter.getItem(position) + after;
                    cv_code.setText(changedText);
                }
            }

            if(addedElementsFirst.size() != 0 & addedElementsLast.size() != 0) {
                if (adapter.getItem(position).equals("Удалить")) {
                    String before = changedText.substring(0,
                            addedElementsFirst.get(addedElementsFirst.size() - 1));
                    String after = changedText.substring(addedElementsLast.get(addedElementsLast.size() - 1));
                    changedText = before + "___" + after;
                    cv_code.setText(changedText);
                    addedElementsFirst.remove(addedElementsFirst.size() - 1);
                    addedElementsLast.remove(addedElementsLast.size() - 1);
                }
            }
        });
    }
}