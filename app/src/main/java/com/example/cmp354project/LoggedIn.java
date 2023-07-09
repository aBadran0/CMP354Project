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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;


public class LoggedIn extends AppCompatActivity implements View.OnClickListener
{

    Button searchButton;
    EditText et_accountName;

    Spinner regionSelect;

    Button setupButton;

    Button addMatchToHistoryButton;


    ArrayAdapter<CharSequence> adapter;
    FirebaseFirestore db;







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


        searchButton.setOnClickListener(this);
        setupButton.setOnClickListener(this);
        addMatchToHistoryButton.setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.Regions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSelect.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        String userEmail = getIntent().getStringExtra("username");
        singleton.getInstance().setIntValue(1);

        db.collection(userEmail)
                .whereEqualTo("Account Setup", "false") // Assuming you have a field called "userId" in your documents
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve the value from the document
                            String value = document.getString("Account Setup");
                            if(value.equals("false"))
                            {

                            }
                            else
                            {
                                setupButton.setVisibility(View.INVISIBLE);

                            }

                        }
                    } else {
                        // Handle any errors
                        System.out.println("Error getting documents: " + task.getException());
                    }
                });
        db.collection(userEmail)
                .whereEqualTo("Account Setup", "true") // Assuming you have a field called "userId" in your documents
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve the value from the document
                            String value = document.getString("Account Setup");
                            if(value.equals("false"))
                            {

                            }
                            else
                            {
                                setupButton.setVisibility(View.INVISIBLE);

                            }

                        }
                    } else {
                        // Handle any errors
                        System.out.println("Error getting documents: " + task.getException());
                    }
                });










    }

    @Override
    protected void onResume() {
        super.onResume();

        db = FirebaseFirestore.getInstance();
        String userEmail = getIntent().getStringExtra("username");
        db.collection(userEmail)
                .whereEqualTo("Account Setup", "true") // Assuming you have a field called "userId" in your documents
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve the value from the document
                            String value = document.getString("Account Setup");
                            if(value.equals("false"))
                            {

                            }
                            else
                            {
                                setupButton.setVisibility(View.INVISIBLE);

                            }
                        }
                    } else {
                        // Handle any errors
                        System.out.println("Error getting documents: " + task.getException());
                    }
                });
        db.collection(userEmail)
                .whereEqualTo("Account Setup", "false") // Assuming you have a field called "userId" in your documents
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve the value from the document
                            String value = document.getString("Account Setup");
                            if(value.equals("false"))
                            {

                            }
                            else
                            {
                                setupButton.setVisibility(View.INVISIBLE);

                            }
                        }
                    } else {
                        // Handle any errors
                        System.out.println("Error getting documents: " + task.getException());
                    }
                });



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_setAccount) {


            Intent setupIntent = new Intent(this, AccountSetup.class);
            String userEmail = getIntent().getStringExtra("username");
            setupIntent.putExtra("username", userEmail);
            startActivity(setupIntent);

        }
        else if (v.getId() == R.id.btn_addMatchToHistory)
        {
            Intent addMatch = new Intent(this, AddMatch.class);
            String userEmail = getIntent().getStringExtra("username");
            addMatch.putExtra("username", userEmail);

            startActivity(addMatch);

        }
        else if(v.getId() == R.id.btn_searchSummoner)
        {
            Intent viewHistory = new Intent(this, MatchHistory.class);
            startActivity(viewHistory);
        }


    }


}