package com.example.william.a24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView num1= (TextView) findViewById(R.id.textView2);
        TextView num2= (TextView) findViewById(R.id.textView3);
        TextView num3= (TextView) findViewById(R.id.textView4);
        TextView num4= (TextView) findViewById(R.id.textView5);
        num1.setText(number());
        num2.setText(number());
        num3.setText(number());
        num4.setText(number());
    }

    public void pickNum(View view) {
        TextView num1= (TextView) findViewById(R.id.textView2);
        TextView num2= (TextView) findViewById(R.id.textView3);
        TextView num3= (TextView) findViewById(R.id.textView4);
        TextView num4= (TextView) findViewById(R.id.textView5);
        num1.setText(number());
        num2.setText(number());
        num3.setText(number());
        num4.setText(number());
    }

    private String number() {
        return "" + ((int)(Math.random()*13) + 1);
    }
}
