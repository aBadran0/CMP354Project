package com.example.cmp354project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class LoggedIn extends AppCompatActivity implements View.OnClickListener
{

    Button setAccount;
    EditText et_accountName;
    Spinner regionSelect;

    TextView tv_MainAccount;
    ArrayAdapter<CharSequence> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        regionSelect = findViewById(R.id.regionSelect);
        setAccount = findViewById(R.id.setAccount);
        et_accountName = findViewById(R.id.et_accountName);
        tv_MainAccount = findViewById(R.id.tv_MainAccount);

        setAccount.setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.Regions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSelect.setAdapter(adapter);



    }

    @Override
    public void onClick(View v) {

        String currentUser = tv_MainAccount.getText().toString();
        if (currentUser.equals("Currently No Account Set"))
        {
            tv_MainAccount.setText("Your account is currently " + et_accountName.getText().toString() + "In region " + regionSelect.getSelectedItem().toString());
        }


    }


}