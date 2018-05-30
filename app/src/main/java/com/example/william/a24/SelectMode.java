package com.example.william.a24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);
    }
    public void toPractice(View view){
        Intent intent = new Intent(this, game.class);
        startActivity(intent);
    }
    public void toSingle(View view){
        Intent intent = new Intent(this, SingleMode.class);
        startActivity(intent);
    }
}
