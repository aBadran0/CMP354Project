package com.example.cmp354project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountSetup extends AppCompatActivity {

    private Button btn_saveacc;

    EditText et_summName, et_Region, et_Rank;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        btn_saveacc = findViewById(R.id.btn_saveacc);
    }



    public void onClick_save(View v) {


        Toast.makeText(this,"Account has been made !",Toast.LENGTH_SHORT).show();
        finish();

    }
}