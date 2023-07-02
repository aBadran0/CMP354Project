package com.example.cmp354project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.util.IOUtils;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;


public class LoggedIn extends AppCompatActivity implements View.OnClickListener
{

    Button searchButton;
    EditText et_accountName;
    Spinner regionSelect;

    TextView summonerLevel;

    ArrayAdapter<CharSequence> adapter;

    String tempString = "";


    //UPDATEE EVERY DAY
    String api_key = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        regionSelect = findViewById(R.id.regionSelect);
        searchButton = findViewById(R.id.searchButton);
        et_accountName = findViewById(R.id.et_accountName);
        summonerLevel = findViewById(R.id.summonerLevel);

        searchButton.setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.Regions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSelect.setAdapter(adapter);



    }

    @Override
    public void onClick(View v) {

        String userName = et_accountName.getText().toString();
        final URL[] api_url = {null};


        new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    api_url[0] = new URL("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + userName + "?api_key=" + api_key);
                    HttpURLConnection huc = (HttpURLConnection) api_url[0].openConnection();
                    InputStream inputStream = huc.getInputStream();
                    String tempString = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        summonerLevel.setText(tempString);




    }

}