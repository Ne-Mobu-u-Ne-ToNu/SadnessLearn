package com.example.sadnesslearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sadnesslearn.classes.CodeTask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskList extends AppCompatActivity {
    private ListView listTasks;
    private List<String> listData;
    private List<CodeTask> arrayListTasks;
    private ArrayAdapter<String> adapter;
    private DatabaseReference mDataBase;
    private final String TASK_KEY = "CodeTask";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        init();
        getDataFromDB();

        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CodeTask task = arrayListTasks.get(i);
                Intent intent = new Intent(TaskList.this, SolveCodeTask.class);
                intent.putExtra("task_id", task.getId());
                intent.putExtra("task_name", task.getName());
                intent.putExtra("task_number", task.getNumber());
                intent.putExtra("task_text", task.getText());
                intent.putExtra("task_code", task.getCode());
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    private void init(){
        Toolbar tlb_task_list = findViewById(R.id.tlb_task_list);
        setSupportActionBar(tlb_task_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        listTasks = findViewById(R.id.lv_task_taskList);
        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listTasks.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference(TASK_KEY);
        arrayListTasks = new ArrayList<>();
    }

    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listData.size() > 0) listData.clear();
                if(arrayListTasks.size() > 0) arrayListTasks.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    CodeTask task =  ds.getValue(CodeTask.class);
                    assert task != null;
                    listData.add(task.getName());
                    arrayListTasks.add(task);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }
}