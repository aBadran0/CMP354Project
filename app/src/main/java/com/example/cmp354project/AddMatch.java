package com.example.cmp354project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddMatch extends AppCompatActivity implements View.OnClickListener {

    EditText et_cs, et_ChampionName, et_nameOfItem, et_damage, et_accountNameSelected;
    Button btn_addItem, btn_removeItem, btn_submitMatch;
    Spinner spinner_role, spinner_winOrLoss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_match_to_history);
        et_cs = et_cs.findViewById(R.id.et_cs);
        et_ChampionName = et_ChampionName.findViewById(R.id.et_championName);
        et_nameOfItem = et_nameOfItem.findViewById(R.id.et_nameOfItem);
        et_damage = et_damage.findViewById(R.id.et_damage);
        et_accountNameSelected = et_accountNameSelected.findViewById(R.id.et_accountNameSelected);
        btn_addItem.findViewById(R.id.btn_addItem);
        btn_removeItem.findViewById(R.id.btn_removeItem);
        btn_submitMatch.findViewById(R.id.btn_submitMatch);


        btn_submitMatch.setOnClickListener(this);
        btn_addItem.setOnClickListener(this);
        btn_removeItem.setOnClickListener(this);


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

        }

    }
}
