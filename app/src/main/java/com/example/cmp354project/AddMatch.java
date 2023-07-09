package com.example.cmp354project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddMatch extends AppCompatActivity implements View.OnClickListener {

    EditText et_cs, et_ChampionName, et_nameOfItem, et_damage;
    private Button btn_addItem, btn_removeItem, btn_submitMatch;
    Spinner spinner_role, spinner_winOrLoss;

    ArrayAdapter<CharSequence> roleadapter,resultadapter;
    FirebaseFirestore db;
    int index = 1;
    String itemsFinalString = "";
    ArrayList<String> items = new ArrayList<String>();

    String userEmail = "";




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
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("username");



    }

    @Override
    public void onClick(View v) {
       FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(v.getId()==R.id.btn_addItem)
        {
            if(index <= 6)
            {
                items.add(et_nameOfItem.getText().toString());
                et_nameOfItem.setText("");
                index++;
            }
            else {
                Toast.makeText(this, "You cannot add more than 6 items",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId()== R.id.btn_removeItem)
        {
            if (index > 0)
            {
                items.remove(index);
                index--;
            }
            else {
                Toast.makeText(this, "You cannot remove items from an empty inventory",Toast.LENGTH_SHORT).show();
            }

        }
        else if(v.getId() == R.id.btn_submitMatch)
        {
            for(String s : items)
            {
                itemsFinalString += s + ",";
           }

            Map<String,Object> match = new HashMap<>();
            match.put("Champion",et_ChampionName.getText().toString());
            match.put("Creep Score", et_cs.getText().toString());
            match.put("Result",spinner_winOrLoss.getSelectedItem().toString() );
            match.put("Items", itemsFinalString);
            match.put("Role", spinner_role.getSelectedItem().toString());
            match.put("Damage",et_damage.getText().toString());
            db.collection(userEmail).document("Match ").set(match);


            Toast.makeText(this, "Match has been added",Toast.LENGTH_SHORT).show();


            finish();
        }

    }
}
