package com.example.william.a24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select);
    }
    public void toSingle(View view){
        Intent intent = new Intent(this, SingleMode.class);
        startActivity(intent);
    }
    public void toSingleHard(View view) {
        Intent intent = new Intent(this, SingleModeHard.class);
        startActivity(intent);
    }
    public void toTimeEasy(View view) {
        Intent intent = new Intent(this, TimeAttackEasy.class);
        startActivity(intent);
    }
    public void toTimeHard(View view) {
        Intent intent = new Intent(this, TimeAttackHard.class);
        startActivity(intent);
    }
}