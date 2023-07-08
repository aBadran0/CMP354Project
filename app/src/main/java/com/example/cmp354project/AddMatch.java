package com.example.cmp354project;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddMatch extends AppCompatActivity implements View.OnClickListener {

    EditText et_cs, et_ChampionName, et_nameOfItem, et_damage/*, et_accountNameSelected*/;
    private Button btn_addItem, btn_removeItem, btn_submitMatch;
    Spinner spinner_role, spinner_winOrLoss;

    ArrayAdapter<CharSequence> roleadapter,resultadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_match_to_history);

        et_cs = findViewById(R.id.et_cs);
        et_ChampionName = findViewById(R.id.et_championName);
        et_nameOfItem = findViewById(R.id.et_nameOfItem);
        et_damage = findViewById(R.id.et_damage);

        btn_addItem = findViewById(R.id.btn_addItem);
        btn_removeItem = findViewById(R.id.btn_removeItem);
        btn_submitMatch = findViewById(R.id.btn_submitMatch);

        spinner_role = findViewById(R.id.spinner_role);
        spinner_winOrLoss = findViewById(R.id.spinner_winOrLoss);

        btn_submitMatch.setOnClickListener(this);
        btn_addItem.setOnClickListener(this);
        btn_removeItem.setOnClickListener(this);

        roleadapter =ArrayAdapter.createFromResource(this, R.array.Roles, android.R.layout.simple_spinner_item);
        roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_role.setAdapter(roleadapter);

        resultadapter = ArrayAdapter.createFromResource(this, R.array.Result, android.R.layout.simple_spinner_item);
        resultadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_winOrLoss.setAdapter(resultadapter);



    }

    @Override
    public void onClick(View v) {
       FirebaseFirestore db = FirebaseFirestore.getInstance();
       if(v.getId()==R.id.btn_addItem)
        {

        }
        else if(v.getId()== R.id.btn_removeItem)
        {

        }
        else if(v.getId() == R.id.btn_submitMatch)
        {
            Toast.makeText(this, "Match has been added",Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
