package com.example.cmp354project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class AccountSetup extends AppCompatActivity implements View.OnClickListener {

    private Button btn_saveacc;

    EditText et_summName, et_Rank;
    Spinner spinnerRegion;

    ArrayAdapter<CharSequence> Accadapter;


    FirebaseFirestore db;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        btn_saveacc = findViewById(R.id.btn_saveacc);
        spinnerRegion = findViewById(R.id.spinnerRegion);
        et_summName = findViewById(R.id.et_SummonerName);
        et_Rank = findViewById(R.id.et_Rank);
        Accadapter = ArrayAdapter.createFromResource(this, R.array.Regions, android.R.layout.simple_spinner_item);

        Accadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(Accadapter);
        btn_saveacc.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();




    }



    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("username");

        if(v.getId() == R.id.btn_saveacc)
        {
            db = FirebaseFirestore.getInstance();
            String accountSetup = "Account Setup";

            String sumName = et_summName.getText().toString();
            DocumentReference docIdRef = db.collection("summoners").document(sumName);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            Toast.makeText(AccountSetup.this,"You already have an account with this name",Toast.LENGTH_SHORT).show();
                        } else {

                            String Rank = et_Rank.getText().toString();

                            //class member

                            Map<String,Object> sum = new HashMap<>();

                            sum.put("Summoner Name",sumName);
                            sum.put("Region",spinnerRegion.getSelectedItem().toString());
                            sum.put("Rank",Rank );
                            db.collection(userEmail).document("Summoner").set(sum);



                            Toast.makeText(AccountSetup.this,"Account has been made !",Toast.LENGTH_SHORT).show();
                            Map<String,Object> accSetup = new HashMap<>();
                            accSetup.put("Account Setup", "true");
                            db.collection(userEmail).document(accountSetup).set(accSetup);


                            finish();



                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });



        }



    }

}