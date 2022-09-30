package com.example.klepsy.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.klepsy.R;

public class LobbyAct extends AppCompatActivity {

    Switch aSwitch;
    private boolean isHide = true;
    public int work_time1 = 1, break_time, big_break_time, big_break_before;

    EditText circleKol, bigBreakMinutes, workMin, breakMin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);

        circleKol = (EditText) findViewById(R.id.circle);
        bigBreakMinutes = (EditText) findViewById(R.id.BigBreakMinutes);
        workMin = (EditText) findViewById(R.id.MinutesWork);
        breakMin = (EditText) findViewById(R.id.MinutesBreak);


        Button start_timer = (Button) findViewById(R.id.start_timer_button);
        start_timer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((breakMin.getText().toString().equals("") |
                        workMin.getText().toString().equals("")) & isHide) |
                        ((circleKol.getText().toString().equals("") |
                                workMin.getText().toString().equals("") |
                                breakMin.getText().toString().equals("") |
                                bigBreakMinutes.getText().toString().equals("")) & !isHide)) {
                    Toast.makeText(getApplicationContext(),
                            "Введите значения во все поля", Toast.LENGTH_SHORT).show();
                }
                else if (isHide){

                    if (integerCondNot(breakMin) | integerCondNot(workMin)) {
                        Toast.makeText(getApplicationContext(),
                                "Введите значения от 1 до 120", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(LobbyAct.this, TimeActivity.class);
                        intent.putExtra("work_time", editTextToInt(workMin) * 60000);
                        intent.putExtra("break_time", editTextToInt(breakMin) * 60000);
                        startActivity(intent);
                    }
                }
                else {
                    if (integerCondNot(circleKol) | integerCondNot(workMin) |
                            integerCondNot(breakMin) | integerCondNot(bigBreakMinutes)) {
                        Toast.makeText(getApplicationContext(),
                                "Введите значения от 1 до 120", Toast.LENGTH_SHORT).show();
                }
                    else {
                        Intent intent = new Intent(LobbyAct.this, TimeActivity.class);
                        intent.putExtra("work_time", editTextToInt(workMin) * 60000);
                        intent.putExtra("break_time", editTextToInt(breakMin) * 60000);
                        intent.putExtra("big_break_time", editTextToInt(bigBreakMinutes) * 60000);
                        intent.putExtra("big_break_before", editTextToInt(circleKol));
                        startActivity(intent);
                    }
                }
            }
        });

        aSwitch = (Switch) findViewById(R.id.switch1);

        circleKol.setVisibility(View.INVISIBLE);
        bigBreakMinutes.setVisibility(View.INVISIBLE);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getApplicationContext(), Boolean.toString(isHide), Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    circleKol.setVisibility(View.VISIBLE);
                    bigBreakMinutes.setVisibility(View.VISIBLE);
                    isHide = false;
                }
                else {
                    circleKol.setVisibility(View.INVISIBLE);
                    bigBreakMinutes.setVisibility(View.INVISIBLE);
                    isHide = true;
                    circleKol.getText().clear();
                    bigBreakMinutes.getText().clear();
                }

            }
        });



    }
    private boolean integerCondNot(EditText et) {
        int n = Integer.parseInt(et.getText().toString());
        return n < 1 | n > 120;
    }

    private int editTextToInt(EditText et) {
        return Integer.parseInt(et.getText().toString());
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
    }
    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
    }
}