package com.example.klepsy.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.klepsy.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Locale;

public class TimeActivity extends AppCompatActivity {

    Button pause_resume, exit_activity; // кнопочки
    TextView text_timer, debug_text;
    CircularProgressIndicator cpi;
    private CountDownTimer mcountDownTimer;

    private boolean work_time_now = true;

    private boolean mTimerRunning;
    public int work_time, break_time, big_break_time, big_break_before, actual_circle = 0, cond = 0;
    private long mTimeLeftInMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);

        text_timer = (TextView) findViewById(R.id.text_timer);
        pause_resume = (Button) findViewById(R.id.pause_resume_button);
        exit_activity = (Button) findViewById(R.id.exit_activity_button);
        cpi = (CircularProgressIndicator) findViewById(R.id.circ_prog);
        debug_text = (TextView) findViewById(R.id.debug_text);

        cpi.setIndeterminate(false);


        Intent intent = getIntent();

        work_time = intent.getIntExtra("work_time", 0) + 1000; // время работы
        break_time = intent.getIntExtra("break_time", 0) + 1000; // время перерыва
        big_break_time = intent.getIntExtra("big_break_time", 0) + 1000; // время большого перерыва
        big_break_before = intent.getIntExtra("big_break_before", 0); // большой перерыв будет после N цикла "работа-отдых"
        mTimeLeftInMillis = work_time - 1000;
        updateCountDownText();

        pause_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        exit_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manag = getSupportFragmentManager();
                dialogExit dExit = new dialogExit();
                dExit.show(manag, "ExitAct");
            }
        });
    }

    private void pauseTimer() {
        mcountDownTimer.cancel();
        mTimerRunning = false;
        pause_resume.setText("ПРОДОЛЖИТЬ");
    }

    private void startTimer() {
        mcountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                final MediaPlayer doundStop = MediaPlayer.create(TimeActivity.this, R.raw.ustrt);
                if (work_time_now) {
                    actual_circle++;
                    if (actual_circle == big_break_before & big_break_before != 0) {
                        mTimeLeftInMillis = big_break_time;
                        actual_circle = 0;
                        cond = 2;
                    } else {
                        mTimeLeftInMillis = break_time;
                        cond = 1;
                    }
                    work_time_now = false;
                    doundStop.start();
                } else {

                    mTimeLeftInMillis = work_time;
                    work_time_now = true;
                    cond = 0;
                    doundStop.start();
                }
                startTimer();
            }

        }.start();

        mTimerRunning = true;
        pause_resume.setText("ПАУЗА");

    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),
                "%02d:%02d", minutes, seconds);
        if (cond == 0) {
            cpi.setProgressCompat((int) ((double) (mTimeLeftInMillis) / ((double) (work_time - 1000)) * 100), true);
        } else if (cond == 1) {
            cpi.setProgressCompat((int) ((double) (mTimeLeftInMillis) / ((double) (break_time - 1000)) * 100), true);
        } else if (cond == 2) {
            cpi.setProgressCompat((int) ((double) (mTimeLeftInMillis) / ((double) (big_break_time - 1000)) * 100), true);
        }
//        switch (cond) {/            case (0):
//                debug_text.setText(Integer.toString((int) ((double) (mTimeLeftInMillis) / ((double) (work_time - 1000)) * 100)));
//                cpi.setProgressCompat((int) ((double) (mTimeLeftInMillis) / ((double) (work_time - 1000)) * 100), true);
//            case (1):
//                cpi.setProgressCompat((int) ((double) (mTimeLeftInMillis) / ((double) (break_time - 1000)) * 100), true);
//            case (2):
//        }

        text_timer.setText(timeLeftFormatted);
    }
    @Override
    public void onBackPressed() {
        FragmentManager manag = getSupportFragmentManager();
        dialogExit dExit = new dialogExit();
        dExit.show(manag, "ExitAct");
    }
    public void escapeActivity() {
        pauseTimer();

        finish();
    }

}
