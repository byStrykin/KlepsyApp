package com.example.klepsy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.klepsy.adapters.CompleteTaskAdapter;
import com.example.klepsy.data.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CompletedTaskActivity extends AppCompatActivity {

    final Context context = this;
    public static ArrayList<Task> mainCompletedTasks = new ArrayList<Task>();
    public static CompleteTaskAdapter cTaskAdapter;
    public static DbManager cDbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_tasks);

        cDbManager = new DbManager(this);
        cDbManager.openDb();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        cTaskAdapter = new CompleteTaskAdapter(this, mainCompletedTasks);
        cUpdateData(mainCompletedTasks, cTaskAdapter);

        TextView emptyText = findViewById(R.id.empty_text_list_c);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompletedTaskActivity.this, DateActivity.class);
                startActivity(intent);
            }
        });

        ListView listView = findViewById(R.id.completedTasksListView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setEmptyView(emptyText);
        listView.setAdapter(cTaskAdapter);

    }
    public static void cUpdateData(ArrayList<Task> tasks, CompleteTaskAdapter cta) {
        tasks.clear();
        for (int i = 0; i < cDbManager.getNameTaskFromDb("true").size(); i++) {
            tasks.add(new Task(cDbManager.getNameTaskFromDb("true").get(i),
                    cDbManager.getDescTaskFromDb("true").get(i),
                    Long.parseLong(cDbManager.getCreDateFromDb("true").get(i))));
        }
        cta.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cDbManager.openDb();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cDbManager.closeDb();
    }
}