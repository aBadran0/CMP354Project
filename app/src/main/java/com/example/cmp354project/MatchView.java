package com.example.cmp354project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class MatchView extends AppCompatActivity {

    TextView tv_Champ,tv_Result,tv_CreepScore,tv_Items;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_view);

        tv_Champ =findViewById(R.id.tv_Champion);
        tv_Result=findViewById(R.id.tv_matchResult);
        tv_CreepScore=findViewById(R.id.tv_creepScore);
        tv_Items = findViewById(R.id.tv_Items);


    }
}