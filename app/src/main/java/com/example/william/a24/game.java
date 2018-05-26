package com.example.william.a24;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

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
    boolean showall;
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
        showall = false;
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

    private String number() {
        return "" + ((int)(Math.random()*13) + 1);
    }
}
