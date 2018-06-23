package com.example.william.a24;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class game extends AppCompatActivity {
    Button num1;
    Button num2;
    Button num3;
    Button num4;
    Button prev;
    Button prevOp;
    String a;
    String b;
    String c;
    String d;
    String curNum;
    TextView sol;
    char operator;
    int count;
    boolean showall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        num1 = (Button) findViewById(R.id.button7);
        num2 = (Button) findViewById(R.id.button8);
        num3 = (Button) findViewById(R.id.button9);
        num4 = (Button) findViewById(R.id.button10);
        prev = num1; //arbitrary at this point
        prevOp = (Button) findViewById(R.id.plus); //also arbitraray
        newNumbers();
        sol = (TextView) findViewById(R.id.textView6);
        sol.setText(" ");
        showall = false;
        TextView solutions  = (TextView) findViewById(R.id.textView8);
        solutions.setText(" ");
    }

    public void pickNum(View view) {
        a = number();
        b = number();
        c = number();
        d = number();
        num1.setText(a);
        num2.setText(b);
        num3.setText(c);
        num4.setText(d);
        sol.setText(" ");
        showall = false;
        TextView solutions  = (TextView) findViewById(R.id.textView8);
        solutions.setText("");
        Button btn = (Button) findViewById(R.id.button3);
        btn.setText("SHOW SOLUTION");
    }

    public void undo(View view) {
        num1.setVisibility(View.VISIBLE);
        num2.setVisibility(View.VISIBLE);
        num3.setVisibility(View.VISIBLE);
        num4.setVisibility(View.VISIBLE);
        num1.setText(a);
        num2.setText(b);
        num3.setText(c);
        num4.setText(d);
        prev.getBackground().setColorFilter(Color.rgb(63, 81, 181), PorterDuff.Mode.MULTIPLY);
        prevOp.getBackground().setColorFilter(Color.rgb(63, 81, 181), PorterDuff.Mode.MULTIPLY);
        count = 0;
        curNum = "";
        operator = '?';
    }

    public void showSolution(View view) {
        ArrayList<String> answers = Solution.solve(a, b, c, d);
        if (showall) {
            TextView solutions  = (TextView) findViewById(R.id.textView8);
            String combined = "";
            for (int i = 0; i < answers.size(); i++) {
                combined = combined + answers.get(i) + "\n";
            }
            solutions.setText(combined);
            sol.setText("");
        } else {
            String ans;
            if (answers.size() == 0) {
                ans = "impossible";
            } else {
                ans = answers.get(0);
                Button btn = (Button) findViewById(R.id.button3);
                btn.setText("Show All Solutions");
                showall = true;
            }
            sol.setText(ans);
        }
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
        count = 0;
        curNum = "";
        operator = '?';
        sol = (TextView) findViewById(R.id.textView6);
        sol.setText(" ");
        showall = false;
        TextView solutions  = (TextView) findViewById(R.id.textView8);
        solutions.setText(" ");
        Button btn = (Button) findViewById(R.id.button3);
        btn.setText("Show Solution");
    }

    private String number() {
        return "" + ((int)(Math.random()*13) + 1);
    }

    public void clickNum(View view) {
        final Button picked;
        if (view.getId() == R.id.button7) {
            picked = num1;
        } else if (view.getId() == R.id.button8) {
            picked = num2;
        } else if (view.getId() == R.id.button9) {
            picked = num3;
        } else {
            picked = num4;
        }
        prev.getBackground().setColorFilter(Color.rgb(63, 81, 181), PorterDuff.Mode.MULTIPLY);
        picked.getBackground().setColorFilter(Color.rgb(0, 0, 100), PorterDuff.Mode.MULTIPLY);
        if (operator == '?') {
            curNum = picked.getText().toString();
            prev = picked;
        } else if (prev == picked) { // for clicking the same button after clicking the operator
            prevOp.getBackground().setColorFilter(Color.rgb(63, 81, 181), PorterDuff.Mode.MULTIPLY);
            operator = '?';
        } else {
            prevOp.getBackground().setColorFilter(Color.rgb(63, 81, 181), PorterDuff.Mode.MULTIPLY);
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
                        picked.getBackground().setColorFilter(Color.rgb(0, 160, 0), PorterDuff.Mode.MULTIPLY);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                TextView timer = findViewById(R.id.timer);
                                if (timer != null) {
                                    timer.setTextColor(Color.rgb(204, 0, 0));
                                }
                                picked.getBackground().setColorFilter(Color.rgb(63, 81, 181), PorterDuff.Mode.MULTIPLY);
                                newNumbers();
                            }
                        }, 500);
                    }
                }
            }
        }
    }
    public void clickOperator(View view) {
        prevOp.getBackground().setColorFilter(Color.rgb(63, 81, 181), PorterDuff.Mode.MULTIPLY);
        if (curNum != "") {
            if (view.getId() == R.id.plus) {
                prevOp = (Button) findViewById(R.id.plus);
                operator = '+';
            } else if (view.getId() == R.id.multiply) {
                prevOp = (Button) findViewById(R.id.multiply);
                operator = '*';
            } else if (view.getId() == R.id.minus) {
                prevOp = (Button) findViewById(R.id.minus);
                operator = '-';
            } else if (view.getId() == R.id.divide) {
                prevOp = (Button) findViewById(R.id.divide);
                operator = '/';
            }
        }
        prevOp.getBackground().setColorFilter(Color.rgb(0, 0, 100), PorterDuff.Mode.MULTIPLY);
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
