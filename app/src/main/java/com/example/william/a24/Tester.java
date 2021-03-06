package com.example.william.a24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Tester extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);
        TextView solutions = findViewById(R.id.answer);
        solutions.setText(" ");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> numadapter = ArrayAdapter.createFromResource(
                this, R.array.number_choice, R.layout.spinner_layout);
        numadapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(numadapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner2.setAdapter(numadapter);
        spinner3.setAdapter(numadapter);
        spinner4.setAdapter(numadapter);
    }

    public void solution(View view) {
        Spinner a = findViewById(R.id.spinner);
        Spinner b = findViewById(R.id.spinner2);
        Spinner c = findViewById(R.id.spinner3);
        Spinner d = findViewById(R.id.spinner4);
        String num1 = a.getSelectedItem().toString();
        String num2 = b.getSelectedItem().toString();
        String num3 = c.getSelectedItem().toString();
        String num4 = d.getSelectedItem().toString();
        TextView solutions = findViewById(R.id.answer);
        ArrayList<String> answers = Solution.solve(num1, num2, num3, num4);
        if (answers.size() == 0) {
            solutions.setText("IMPOSSIBLE");
        } else {
            String combined = "";
            for (int i = 0; i < answers.size(); i++) {
                combined = combined + answers.get(i) + "\n";
            }
            solutions.setText(combined);
        }
    }
}
