package com.example.sadnesslearn.classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sadnesslearn.R;
import java.util.List;

public class TaskListArrayAdapter extends ArrayAdapter<TaskItem> {
    public TaskListArrayAdapter(Context context, List<TaskItem> list){
        super(context, R.layout.task_list_item, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TaskItem item = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item, parent, false);
        }

        TextView tv_task_item = convertView.findViewById(R.id.tv_task_list_item);
        CheckBox cb_task_item = convertView.findViewById(R.id.cb_task_list_item);

        tv_task_item.setText(item.getName());
        cb_task_item.setChecked(item.getIsSolved());

        return convertView;
    }
}
