package com.example.klepsy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.klepsy.R;
import com.example.klepsy.Task;
import com.example.klepsy.TaskActivity;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater layoutInflater;
    ArrayList<Task> objects;
    TaskAdapter adapter = this;

    public TaskAdapter(Context context, ArrayList<Task> listTask) {
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
            view = layoutInflater.inflate(R.layout.list_item, null);
        }


        Task task = getTask(position);

        ((TextView) view.findViewById(R.id.textbox_name)).setText(task.getName());

        ImageButton completeTask = (ImageButton) view.findViewById(R.id.completeButtonTask);
        ImageButton deleteTask = (ImageButton) view.findViewById(R.id.deleteButtonTask);

        deleteTask.setTag(position);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskActivity.maDbManager.removeOne(task);
                TaskActivity.updateData(TaskActivity.mainTasks, TaskActivity.maTaskAdapter);
                adapter.notifyDataSetChanged();
            }
        });
        completeTask.setTag(position);
        completeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskActivity.maDbManager.setCompleteTask(task, "true");
                TaskActivity.updateData(TaskActivity.mainTasks, TaskActivity.maTaskAdapter);
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }



    Task getTask(int position) {
        return ((Task) getItem(position));
    }




}
