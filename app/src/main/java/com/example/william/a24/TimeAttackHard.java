package com.example.william.a24;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeAttackHard extends SingleModeHard {
    long timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack_hard);
        TextView highScore = (TextView) findViewById(R.id.highScore);
        sharedPref = TimeAttackHard.this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        highScore.setText("High Score: " + sharedPref.getFloat("high_score", 0));
        max = 13;
        num1 = (Button) findViewById(R.id.button7);
        num2 = (Button) findViewById(R.id.button8);
        num3 = (Button) findViewById(R.id.button9);
        num4 = (Button) findViewById(R.id.button10);
        prev = num1; //arbitrary at this point
        prevOp = (Button) findViewById(R.id.plus); //also arbitraray
        newNumbers();
        score = 0;
        timeCount = 60000;
        createCountdownTimer();
    }
    private void createCountdownTimer() {
        if (cdt != null)
            cdt.cancel();
        final TextView timer = (TextView) findViewById(R.id.timer);
        final TextView endText = new TextView(this);
        final Button restart = new Button(this);
        cdt = new CountDownTimer(timeCount, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time: " + millisUntilFinished / 1000 + "s");
                timeCount = millisUntilFinished;
            }

            public void onFinish() {
                double hs = sharedPref.getFloat("high_score", 0);
                if (score > hs) {
                    hs = score;
                    editor.putFloat("high_score", score);
                    editor.apply();
                }
                ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.Layout);
                layout.removeAllViews();
                endText.setText("Score: " + score + "\nHigh Score: " + hs);
                endText.setTextSize(36);
                endText.setTextColor(Color.WHITE);
                endText.setId(View.generateViewId());
                restart.setId(View.generateViewId());
                layout.addView(endText);
                layout.addView(restart);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(layout);
                restart.setText("Retry");
                restart.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        /*Intent intent = new Intent(TimeAttackHard.this, TimeAttackHard.class);
                        startActivity(intent);*/
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                constraintSet.connect(restart.getId(), ConstraintSet.TOP, endText.getId(), ConstraintSet.BOTTOM, 0);
                constraintSet.applyTo(layout);
                //endText.setGravity(Gravity.CENTER);
            }
        }.start();
    }
    @Override
    protected void complete() {
        final TextView timer = (TextView) findViewById(R.id.timer);
        timer.setTextColor(Color.rgb(0, 160, 0));
        score++;
        timeCount += 5000;
        createCountdownTimer();
    }
}
