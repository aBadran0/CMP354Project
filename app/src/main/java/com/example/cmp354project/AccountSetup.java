package com.example.cmp354project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AccountSetup extends AppCompatActivity {

    private Button btn_saveacc;

    EditText et_summName, et_Rank;
    Spinner spinnerRegion;

    ArrayAdapter<CharSequence> Accadapter;

    CollectionReference Summoners;

    FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        btn_saveacc = findViewById(R.id.btn_saveacc);
        spinnerRegion = findViewById(R.id.spinnerRegion);
        Accadapter = ArrayAdapter.createFromResource(this, R.array.Regions, android.R.layout.simple_spinner_item);

        Accadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(Accadapter);

        db = FirebaseFirestore.getInstance();

    }



    public void onClick_save(View v) {

      String sumName = et_summName.getText().toString();
        String Rank = et_Rank.getText().toString();
        String Region = spinnerRegion.toString();
        TextView textView = (TextView)spinnerRegion.getSelectedView();
        String rank = textView.getText().toString();


        //class member
        db.collection("Summoners");

        Map<String,Object> sum = new HashMap<>();

        sum.put("Summoner Name",sumName);
        sum.put("Region",spinnerRegion.getSelectedItem().toString());
        sum.put("Rank",Rank );

        Summoners.document(sumName).set(sum);

        Toast.makeText(this,"Account has been made !",Toast.LENGTH_SHORT).show();
        finish();

    }
}