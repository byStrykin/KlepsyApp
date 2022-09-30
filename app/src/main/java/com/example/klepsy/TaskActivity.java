package com.example.klepsy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.klepsy.adapters.TaskAdapter;
import com.example.klepsy.data.DbManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;


public class TaskActivity extends AppCompatActivity {
    Button addTask, chistka, frag3;
    ImageButton deleteTask;
    TextView emptyText, textTitle, probochny;
    Toolbar toolbar;
    final Context context = this;
    String[] catNames = {"Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
            "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
            "Китти", "Масяня", "Симба"};
    public static ArrayList<Task> mainTasks = new ArrayList<Task>();
    public static TaskAdapter maTaskAdapter;
    public static DbManager maDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // менеджер базы данных
        maDbManager = new DbManager(this);
        maDbManager.openDb();
        // постоянная тёмная тема
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // адаптер
        maTaskAdapter = new TaskAdapter(this, mainTasks);
        maTaskAdapter.notifyDataSetChanged();
//        fillData();
        updateData(mainTasks, maTaskAdapter);
        emptyText = findViewById(R.id.empty_text_view);
        chistka = findViewById(R.id.button2);
        chistka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maDbManager.removeAll();
                updateData(mainTasks, maTaskAdapter);
            }
        });
        ListView listView = findViewById(R.id.listView1);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setEmptyView(emptyText);
        listView.setAdapter(maTaskAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                LayoutInflater layoutInflater = LayoutInflater.from(context);
                                                View promptsView = layoutInflater.inflate(R.layout.prompt, null);
                                                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                                                mDialogBuilder.setView(promptsView);
                                                final TextView mTextViewTitle = (TextView) promptsView.findViewById(R.id.textTitle);
                                                mTextViewTitle.setText("Редактирование задачи");
                                                final EditText nameTaskFromET = (EditText) promptsView.findViewById(R.id.editTaskName);
                                                final EditText descTaskFromET = (EditText) promptsView.findViewById(R.id.editTaskDescription);
                                                nameTaskFromET.setText(mainTasks.get(position).getName());
                                                descTaskFromET.setText(maDbManager.getDesc(mainTasks.get(position).getName()));
                                                mDialogBuilder
                                                        .setCancelable(false)
                                                        .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                maDbManager.updateToDb(nameTaskFromET.getText().toString(),
                                                                        descTaskFromET.getText().toString());
                                                                updateData(mainTasks, maTaskAdapter);
                                                            }
                                                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });
                                                AlertDialog alertDialog = mDialogBuilder.create();

                                                alertDialog.show();
                                            }
                                        }
        );


        addTask = findViewById(R.id.button1);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptsView = layoutInflater.inflate(R.layout.prompt, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                mDialogBuilder.setView(promptsView);
                final TextView mTextViewTitle = (TextView) promptsView.findViewById(R.id.textTitle);
                mTextViewTitle.setText("Создание задачи");
                final EditText nameTaskFromET = (EditText) promptsView.findViewById(R.id.editTaskName);
                final EditText descriptionTaskFromET = (EditText) promptsView.findViewById(R.id.editTaskDescription);
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                maDbManager.insertToDb(nameTaskFromET.getText().toString(),
                                        descriptionTaskFromET.getText().toString(),
                                        new Date().getTime());
                                updateData(mainTasks, maTaskAdapter);
                                maTaskAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = mDialogBuilder.create();

                alertDialog.show();
            }
        });
        frag3 = findViewById(R.id.button3);
        frag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskActivity.this, CompletedTaskActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        maDbManager.openDb();
        updateData(mainTasks, maTaskAdapter);
    }


    public int findTaskPosition (ArrayList<HashMap<String, String>> lisst, String name) {
        for (int i = 0; i < lisst.size(); i++) {
            if (Objects.equals((lisst.get(i)).get("Name"), name)) {
                return i;
            }
        }
        return 0;

    }

//    void fillData() {
//        for (String catName : catNames) {
//            dbManager.insertToDb(catName, "", new Date().getTime());
//            mainTasks.add(new Task(catName, ""));
//        }
//    }

    public static void updateData(ArrayList<Task> tasks, TaskAdapter taskAdapter) {
        tasks.clear();
        for (int i = 0; i < maDbManager.getNameTaskFromDb("false").size(); i++) {
            tasks.add(new Task(maDbManager.getNameTaskFromDb("false").get(i),
                    maDbManager.getDescTaskFromDb("false").get(i),
                    Long.parseLong(maDbManager.getCreDateFromDb("false").get(i))));
        }
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        maDbManager.closeDb();
    }
}
