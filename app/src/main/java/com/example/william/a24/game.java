package com.example.william.a24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class game extends AppCompatActivity {
    TextView num1;
    TextView num2;
    TextView num3;
    TextView num4;
    TextView sol;
    String a;
    String b;
    String c;
    String d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        num1= (TextView) findViewById(R.id.textView2);
        num2= (TextView) findViewById(R.id.textView3);
        num3= (TextView) findViewById(R.id.textView4);
        num4= (TextView) findViewById(R.id.textView5);
        sol = (TextView) findViewById(R.id.textView6);
        a = number();
        b = number();
        c = number();
        d = number();
        num1.setText(a);
        num2.setText(b);
        num3.setText(c);
        num4.setText(d);
        sol.setText(" ");
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
    }

    public void showSolution(View view) {
        String ans = Solution.solve(a, b, c, d);
        sol.setText(ans);
    }

    private String number() {
        return "" + ((int)(Math.random()*13) + 1);
    }
}
