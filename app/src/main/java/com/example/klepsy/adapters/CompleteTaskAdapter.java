package com.example.klepsy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.klepsy.CompletedTaskActivity;
import com.example.klepsy.R;
import com.example.klepsy.Task;

import java.util.ArrayList;

public class CompleteTaskAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    ArrayList<Task> objects;
    CompleteTaskAdapter adapter = this;

    public CompleteTaskAdapter(Context context, ArrayList<Task> listTask) {
        ctx = context;
        objects = listTask;
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_complete_item, null);
        }

        Task task = getTask(position);

        ((TextView) view.findViewById(R.id.textbox_name_comp)).setText(task.getName());

        ImageButton returnTask = (ImageButton) view.findViewById(R.id.returnButtonTask_comp);
        ImageButton deleteTask = (ImageButton) view.findViewById(R.id.deleteButtonTask_comp);

        deleteTask.setTag(position);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletedTaskActivity.cDbManager.removeOne(task);
                CompletedTaskActivity.cUpdateData(CompletedTaskActivity.mainCompletedTasks,
                        CompletedTaskActivity.cTaskAdapter);
                adapter.notifyDataSetChanged();
            }
        });
        returnTask.setTag(position);
        returnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletedTaskActivity.cDbManager.setCompleteTask(task, "false");
                CompletedTaskActivity.cUpdateData(CompletedTaskActivity.mainCompletedTasks,
                        CompletedTaskActivity.cTaskAdapter);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    Task getTask(int position) {
        return ((Task) getItem(position));
    }

}
