package com.example.klepsy.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.klepsy.R;

public class probkich extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probkich);
        Intent intent = getIntent();

        TextView text1 = (TextView) findViewById(R.id.textView2);
        TextView text2 = (TextView) findViewById(R.id.textView4);
        TextView text3 = (TextView) findViewById(R.id.textView5);
        TextView text4 = (TextView) findViewById(R.id.textView6);

        int insId = getIntent().getIntExtra("work_time", 0);
        text1.setText(Integer.toString(insId));

    }
}