package com.example.sadnesslearn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.Tasks.BlockTask;
import com.example.sadnesslearn.classes.Tasks.CodeTask;
import com.example.sadnesslearn.classes.Constants;
import com.example.sadnesslearn.classes.SettingsHelper;
import com.example.sadnesslearn.classes.TaskItem;
import com.example.sadnesslearn.classes.TaskListArrayAdapter;
import com.example.sadnesslearn.classes.TaskSolved;
import com.example.sadnesslearn.classes.Tasks.Task;
import com.example.sadnesslearn.classes.UserAuthentification;
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
    private Map<Integer, Task> mapListTask;
    private Map<Integer, Boolean> mapListTaskSolved;
    private static String taskType;
    private Intent intent;
    private TaskListArrayAdapter adapter;
    private DatabaseReference mDataBase;
    private DatabaseReference mDataBaseSolved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingsHelper.themeExists(this)) {
            setTheme(SettingsHelper.getThemeFromPrefs(this));
        }
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
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        listTasks = findViewById(R.id.lv_task_taskList);
        listData = new ArrayList<>();
        adapter = new TaskListArrayAdapter(this, listData);
        listTasks.setAdapter(adapter);
        mapListTaskSolved = new HashMap<>();
        mapListTask = new HashMap<>();
    }

    private void initCode(){
        mDataBase = FirebaseDatabase.getInstance().getReference(Constants.CODE_TASK_KEY);
        mDataBaseSolved = FirebaseDatabase.getInstance().getReference(Constants.USERS_KEY)
                .child(UserAuthentification.getUID()).child(Constants.CODE_TASK_KEY);
    }

    private void initBlock() {
        mDataBase = FirebaseDatabase.getInstance().getReference(Constants.BLOCK_TASK_KEY);
        mDataBaseSolved = FirebaseDatabase.getInstance().getReference(Constants.USERS_KEY)
                .child(UserAuthentification.getUID()).child(Constants.BLOCK_TASK_KEY);
    }

    private void goSolveTask(String taskType) {
        listTasks.setOnItemClickListener((adapterView, view, i, l) -> {
            Task task = mapListTask.get(i);

            assert task != null;
            intent.putExtra("task_id", task.id);
            intent.putExtra("task_name", task.getName());
            intent.putExtra("task_text", task.getText());
            intent.putExtra("task_code", task.code);
            intent.putExtra("task_test", task.test);
            intent.putExtra("task_number", task.number);

            if (taskType.equals("block")) {
                intent.putExtra("task_options", ((BlockTask) Objects.requireNonNull(mapListTask.get(i))).options);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });
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
        getTaskSolvedFromDB(taskType);
        goSolveTask(taskType);
    }

    private void getTaskSolvedFromDB(String taskType) {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(mapListTaskSolved.size() > 0) mapListTaskSolved.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    TaskSolved task =  ds.getValue(TaskSolved.class);
                    assert task != null;
                    mapListTaskSolved.put(task.getNumber() - 1, task.getIsSolved());
                }

                getTaskFromDB(taskType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBaseSolved.addValueEventListener(vListener);
        adapter.notifyDataSetChanged();
    }

    private void getTaskFromDB(String taskType){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(mapListTask.size() > 0) mapListTask.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Task task = null;
                    switch (taskType) {
                        case "code":
                            task =  ds.getValue(CodeTask.class);
                            break;
                        case "block":
                            task =  ds.getValue(BlockTask.class);
                            break;
                    }

                    assert task != null;
                    task.setName(SettingsHelper.getLocaleFromPreferences(TaskList.this));
                    task.setText(SettingsHelper.getLocaleFromPreferences(TaskList.this));

                    if (mapListTaskSolved.get(task.number - 1) != null &&
                            !Boolean.FALSE.equals(mapListTaskSolved.get(task.number - 1))) {
                        task.setIsSolved(true);
                    }

                    mapListTask.put(task.number - 1, task);
                }
                updateTaskList();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }


    private void updateTaskList(){
        if(listData.size() > 0) listData.clear();
        for(int i = 0; i < mapListTask.size(); i++){
            TaskItem taskItem = new TaskItem(Objects.requireNonNull(mapListTask.get(i)).getName(),
                    Objects.requireNonNull(mapListTask.get(i)).getIsSolved());
            listData.add(taskItem);
        }
    }
}