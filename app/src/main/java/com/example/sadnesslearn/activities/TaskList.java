package com.example.sadnesslearn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sadnesslearn.R;
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
    private Map<Integer, CodeTask> mapListTask;
    private TaskListArrayAdapter adapter;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        init();
        getDataFromDB();

        listTasks.setOnItemClickListener((adapterView, view, i, l) -> {
            CodeTask task = mapListTask.get(i);
            Intent intent = new Intent(TaskList.this, SolveCodeTask.class);
            assert task != null;
            intent.putExtra("task_id", task.getId());
            intent.putExtra("task_name", task.getName());
            intent.putExtra("task_number", task.getNumber());
            intent.putExtra("task_text", task.getText());
            intent.putExtra("task_code", task.getCode());
            intent.putExtra("task_test", task.getTest());
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });
    }

    private void init(){
        Toolbar tlb_task_list = findViewById(R.id.tlb_task_list);
        setSupportActionBar(tlb_task_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        listTasks = findViewById(R.id.lv_task_taskList);
        listData = new ArrayList<>();
        adapter = new TaskListArrayAdapter(this, listData);
        listTasks.setAdapter(adapter);
        listTasks.setClickable(true);
        mDataBase = FirebaseDatabase.getInstance().getReference(Constants.TASK_KEY);
        mapListTask = new HashMap<>();
    }

    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(mapListTask.size() > 0) mapListTask.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    CodeTask task =  ds.getValue(CodeTask.class);
                    assert task != null;
                    mapListTask.put(task.getNumber() - 1, task);
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
            TaskItem taskItem = new TaskItem(Objects.requireNonNull(mapListTask.get(i)).getName(), false);
            listData.add(taskItem);
        }
    }
}