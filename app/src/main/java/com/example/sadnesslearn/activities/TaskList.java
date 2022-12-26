package com.example.sadnesslearn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.BlockTask;
import com.example.sadnesslearn.classes.CodeTask;
import com.example.sadnesslearn.classes.Constants;
import com.example.sadnesslearn.classes.TaskItem;
import com.example.sadnesslearn.classes.TaskListArrayAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TaskList extends AppCompatActivity {
    private ListView listTasks;
    private List<TaskItem> listData;
    private Map<Integer, CodeTask> mapListCodeTask;
    private Map<Integer, BlockTask> mapListBlockTask;
    private static String taskType;
    private Intent intent;
    private TaskListArrayAdapter adapter;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        if(getIntent().getStringExtra("task_type") != null)
            taskType = getIntent().getStringExtra("task_type");

        intent = new Intent(TaskList.this, SolveTask.class);
        intent.putExtra("task_type", taskType);

        init();
        scenario(taskType);
    }

    private void init(){
        Toolbar tlb_task_list = findViewById(R.id.tlb_task_list);
        setSupportActionBar(tlb_task_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        listTasks = findViewById(R.id.lv_task_taskList);
        listData = new ArrayList<>();
        adapter = new TaskListArrayAdapter(this, listData);
        listTasks.setAdapter(adapter);
    }

    private void initCode(){
        mDataBase = FirebaseDatabase.getInstance().getReference(Constants.CODE_TASK_KEY);
        mapListCodeTask = new HashMap<>();
    }

    private void initBlock() {
        mDataBase = FirebaseDatabase.getInstance().getReference(Constants.BLOCK_TASK_KEY);
        mapListBlockTask = new HashMap<>();
    }

    private void goSolveCode() {
        listTasks.setOnItemClickListener((adapterView, view, i, l) -> {
            CodeTask task = mapListCodeTask.get(i);
            assert task != null;
            intent.putExtra("task_id", task.getId());
            intent.putExtra("task_text", task.getText());
            intent.putExtra("task_code", task.getCode());
            intent.putExtra("task_test", task.getTest());
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });
    }

    private void goSolveBlock() {
        listTasks.setOnItemClickListener((adapterView, view, i, l) -> {
            BlockTask task = mapListBlockTask.get(i);
            assert task != null;
            intent.putExtra("task_id", task.getId());
            intent.putExtra("task_text", task.getText());
            intent.putExtra("task_code", task.getCode());
            intent.putExtra("task_test", task.getTest());
            intent.putExtra("task_options", task.getOptions());
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });
    }

    private void scenario(String taskType) {
        switch (taskType) {
            case "code":
                initCode();
                getCodeTaskFromDB();
                goSolveCode();
                break;
            case "block":
                initBlock();
                getBlockTaskFromDB();
                goSolveBlock();
                break;
        }
    }

    private void getCodeTaskFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(mapListCodeTask.size() > 0) mapListCodeTask.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    CodeTask task =  ds.getValue(CodeTask.class);
                    assert task != null;
                    mapListCodeTask.put(task.getNumber() - 1, task);
                }
                updateCodeTaskList();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

    private void getBlockTaskFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(mapListBlockTask.size() > 0) mapListBlockTask.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    BlockTask task =  ds.getValue(BlockTask.class);
                    assert task != null;
                    mapListBlockTask.put(task.getNumber() - 1, task);
                }
                updateBlockTaskList();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

    private void updateCodeTaskList(){
        if(listData.size() > 0) listData.clear();
        for(int i = 0; i < mapListCodeTask.size(); i++){
            TaskItem taskItem = new TaskItem(Objects.requireNonNull(mapListCodeTask.get(i)).getName(),
                    Objects.requireNonNull(mapListCodeTask.get(i)).getIsSolved());
            listData.add(taskItem);
        }
    }

    private void updateBlockTaskList() {
        if(listData.size() > 0) listData.clear();
        for(int i = 0; i < mapListBlockTask.size(); i++){
            TaskItem taskItem = new TaskItem(Objects.requireNonNull(mapListBlockTask.get(i)).getName(),
                    Objects.requireNonNull(mapListBlockTask.get(i)).getIsSolved());
            listData.add(taskItem);
        }
    }
}