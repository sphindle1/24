package com.example.william.a24;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.CountDownTimer;
import java.util.ArrayList;

public class SingleMode extends AppCompatActivity {
    Button num1;
    Button num2;
    Button num3;
    Button num4;
    Button prev;
    TextView sol;
    String a;
    String b;
    String c;
    String d;
    String curNum;
    char operator;
    int count;
    double score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_mode);
        num1 = (Button) findViewById(R.id.button7);
        num2 = (Button) findViewById(R.id.button8);
        num3 = (Button) findViewById(R.id.button9);
        num4 = (Button) findViewById(R.id.button10);
        prev = num1; //arbitrary at this point
        newNumbers();
        score = 0;
        final TextView timer = (TextView) findViewById(R.id.timer);
        final TextView endText = new TextView(this);
        final Button restart = new Button(this);
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time: " + millisUntilFinished / 1000 + "s");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.Layout);
                layout.removeAllViews();
                endText.setText("Score: " + score);
                endText.setTextSize(36);
                endText.setId(View.generateViewId());
                restart.setId(2);
                layout.addView(endText);
                layout.addView(restart);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(layout);
                endText.setGravity(Gravity.CENTER);
                restart.setText("Retry");
                restart.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(SingleMode.this, SingleMode.class);
                        startActivity(intent);
                    }
                });
                constraintSet.connect(restart.getId(),ConstraintSet.TOP,endText.getId(),ConstraintSet.BOTTOM,0);
                constraintSet.applyTo(layout));
            }
        }.start();
    }
    public void pickNum(View view) {
        score -= 0.5;
        newNumbers();
    }
    private void newNumbers() {
        num1.setVisibility(View.VISIBLE);
        num2.setVisibility(View.VISIBLE);
        num3.setVisibility(View.VISIBLE);
        num4.setVisibility(View.VISIBLE);
        a = number();
        b = number();
        c = number();
        d = number();
        while (Solution.solve(a, b, c, d).size() == 0) {
            a = number();
            b = number();
            c = number();
            d = number();
        }
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
    private String number() {
        return "" + ((int)(Math.random()*13) + 1);
    }
    public void clickNum(View view) {
        Button picked;
        if (view.getId() == R.id.button7) {
            picked = num1;
        } else if (view.getId() == R.id.button8) {
            picked = num2;
        } else if (view.getId() == R.id.button9) {
            picked = num3;
        } else {
            picked = num4;
        }
        if (operator == '?') {
            curNum = picked.getText().toString();
            prev = picked;
        } else {
            if (operator == '/' && picked.getText().toString().equals("0")) {
                curNum = "0";
                prev = picked;
            } else {
                String n = picked.getText().toString();
                curNum = operate(curNum, n, operator);
                operator = '?';
                picked.setText(curNum);
                prev.setVisibility(View.INVISIBLE);
                prev = picked;
                count++;
                if (count == 3) {
                    if (curNum.equals("24")) {
                        score++;
                        newNumbers();
                    }
                }
            }
        }
    }
    public void clickOperator(View view) {
        if (curNum != "") {
            if (view.getId() == R.id.plus) {
                operator = '+';
            } else if (view.getId() == R.id.multiply) {
                operator = '*';
            } else if (view.getId() == R.id.minus) {
                operator = '-';
            } else if (view.getId() == R.id.divide) {
                operator = '/';
            }
        }
    }
    private String operate(String x, String y, char op) {
        if (x.indexOf('/') != -1 || y.indexOf('/') != -1) {
            return fractionOp(x, y, op);
        } else {
            int a = Integer.parseInt(x);
            int b = Integer.parseInt(y);
            if (op == '+') {
                int out = a + b;
                return "" + out;
            } else if (op == '*') {
                int out = a * b;
                return "" + out;
            } else if (op == '/') {
                if (a % b != 0) {
                    return simplify(a, b);
                } else {
                    int out = a / b;
                    return "" + out;
                }
            } else {
                int out = a - b;
                return "" + out;
            }
        }
    }
    private String fractionOp(String frac1, String frac2, char op) {
        int num1;
        int denom1;
        int num2;
        int denom2;
        if (frac1.indexOf('/') != -1) {
            num1 = Integer.parseInt(frac1.substring(0, frac1.indexOf('/')));
            denom1 = Integer.parseInt(frac1.substring(frac1.indexOf('/') + 1));
        } else {
            num1 = Integer.parseInt(frac1);
            denom1 = 1;
        }
        if (frac2.indexOf('/') != -1) {
            num2 = Integer.parseInt(frac2.substring(0, frac2.indexOf('/')));
            denom2 = Integer.parseInt(frac2.substring(frac2.indexOf('/') + 1));
        } else {
            num2 = Integer.parseInt(frac2);
            denom2 = 1;
        }
        int num;
        int denom;
        if (op == '*') {
            num = num1 * num2;
            denom = denom1 * denom2;
        } else if (op == '/') {
            num = num1 * denom2;
            denom = denom1 * num2;
        } else {
            denom = lcm(denom1, denom2);
            num1 = num1 * denom/denom1;
            num2 = num2 * denom/denom2;
            if (op == '+') {
                num = num1 + num2;
            } else {
                num = num1 - num2;
            }
        }
        return simplify(num, denom);
    }
    private String simplify(int num, int denom) {
        int factor = gcf(num, denom);
        num = num / factor;
        denom = denom / factor;
        if (denom == 1)  {
            return "" + num;
        }
        return "" + num + "/" + denom;
    }
    private int gcf(int a, int b) {
        int x = a;
        if (b < x) {
            x = b;
        }
        while (a % x != 0 || b % x != 0) {
            x--;
        }
        return x;
    }
    private int lcm(int a, int b) {
        int x = a;
        if (b > x) {
            x = b;
        }
        while (x % a != 0 || x % b != 0) {
            x++;
        }
        return x;
    }
}
