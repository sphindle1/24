package com.example.william.a24;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SingleModeHard extends SingleMode {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_mode_hard);
        TextView highScore = (TextView) findViewById(R.id.highScore);
        sharedPref = SingleModeHard.this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        highScore.setText("High Score: " + sharedPref.getFloat("high_score", 0));
        num1 = (Button) findViewById(R.id.button7);
        num2 = (Button) findViewById(R.id.button8);
        num3 = (Button) findViewById(R.id.button9);
        num4 = (Button) findViewById(R.id.button10);
        prev = num1; //arbitrary at this point
        prevOp = (Button) findViewById(R.id.plus); //also arbitraray
        newNumbers();
        score = 0;
        final TextView timer = (TextView) findViewById(R.id.timer);
        final TextView endText = new TextView(this);
        final Button restart = new Button(this);
        if (cdt != null) {
            cdt.cancel();
        }
        cdt = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time: " + millisUntilFinished / 1000 + "s");
                //here you can have your logic to set text to edittext
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
                endText.setId(View.generateViewId());
                restart.setId(View.generateViewId());
                layout.addView(endText);
                layout.addView(restart);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(layout);
                endText.setGravity(Gravity.CENTER);
                restart.setText("Retry");
                restart.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(SingleModeHard.this, SingleModeHard.class);
                        startActivity(intent);
                    }
                });
                constraintSet.connect(restart.getId(),ConstraintSet.TOP,endText.getId(),ConstraintSet.BOTTOM,0);
                constraintSet.applyTo(layout);
            }
        }.start();
    }
    public void impossible(View view) {
        final Button btn = (Button) findViewById(R.id.impossible);
        if(Solution.solve(a, b, c, d).size() == 0) {
            complete();
            btn.getBackground().setColorFilter(Color.rgb(0, 160, 0), PorterDuff.Mode.MULTIPLY);
        } else {
            score--;
            btn.getBackground().setColorFilter(Color.rgb(210, 0, 0), PorterDuff.Mode.MULTIPLY);
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                btn.getBackground().setColorFilter(Color.rgb(63, 81, 181), PorterDuff.Mode.MULTIPLY);
                TextView timer = findViewById(R.id.timer);
                if (timer != null) {
                    timer.setTextColor(Color.rgb(204, 0, 0));
                }
                newNumbers();
            }
        }, 500);
    }
    protected void newNumbers() {
        num1.setVisibility(View.VISIBLE);
        num2.setVisibility(View.VISIBLE);
        num3.setVisibility(View.VISIBLE);
        num4.setVisibility(View.VISIBLE);
        a = number();
        b = number();
        c = number();
        d = number();
        num1.setText(a);
        num2.setText(b);
        num3.setText(c);
        num4.setText(d);
        TextView scoreBoard = (TextView) findViewById(R.id.scoreBoard);
        scoreBoard.setText("Score: " + score);
        count = 0;
        curNum = "";
        operator = '?';
    }
}
