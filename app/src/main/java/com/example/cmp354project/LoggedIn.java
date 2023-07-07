package com.example.cmp354project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class LoggedIn extends AppCompatActivity implements View.OnClickListener
{

    Button searchButton;
    EditText et_accountName;

    Spinner regionSelect;

    Button setupButton;

    Button addMatchToHistoryButton;

    TextView summonerLevel;

    ArrayAdapter<CharSequence> adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        regionSelect = findViewById(R.id.regionSelect);
        searchButton = findViewById(R.id.btn_searchSummoner);
        setupButton = findViewById(R.id.btn_setAccount);
        addMatchToHistoryButton = findViewById(R.id.btn_addMatchToHistory);

        et_accountName = findViewById(R.id.et_accountName);
    //    summonerLevel = findViewById(R.id.summonerLevel);

        searchButton.setOnClickListener(this);
        setupButton.setOnClickListener(this);
        addMatchToHistoryButton.setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.Regions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSelect.setAdapter(adapter);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_setAccount) {

            Intent setupIntent = new Intent(this, AccountSetup.class);
            startActivity(setupIntent);
        }
        else if (v.getId() == R.id.btn_addMatchToHistory)
        {
            Intent addMatch = new Intent(this, AddMatch.class);
            startActivity(addMatch);

        }


    }


}