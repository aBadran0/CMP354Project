package com.example.cmp354project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;


public class LoggedIn extends AppCompatActivity implements View.OnClickListener {

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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("League.gg");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


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
        DocumentReference docRef = db.collection(userEmail).document("Account Setup");


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.get("Number of matches") != null) {
                            Long L = (Long) document.get("Number of matches");
                            singleton.getInstance().setIntValue(L.intValue() + 1);

                        } else {
                            singleton.getInstance().setIntValue(1);
                        }

                        if (document.getString("Account Setup").equals("true")) {
                            setupButton.setVisibility(View.INVISIBLE);

                        }

                    }
                } else {

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = FirebaseFirestore.getInstance();
        String userEmail = getIntent().getStringExtra("username");
        DocumentReference docRef = db.collection(userEmail).document("Account Setup");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.getString("Account Setup").equals("true")) {
                            setupButton.setVisibility(View.INVISIBLE);
                        }

                    }
                } else {

                }
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

        } else if (v.getId() == R.id.btn_addMatchToHistory) {
            Intent addMatch = new Intent(this, AddMatch.class);
            String userEmail = getIntent().getStringExtra("username");
            addMatch.putExtra("username", userEmail);

            startActivity(addMatch);

        } else if (v.getId() == R.id.btn_searchSummoner) {
            Intent viewHistory = new Intent(this, MatchHistory.class);
            String userEmail = et_accountName.getText().toString();
            viewHistory.putExtra("username", userEmail);
            startActivity(viewHistory);

        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_about) {


        } else if (item.getItemId() == R.id.menu_matchHistory)
        {
            Intent viewHistory = new Intent(this, MatchHistory.class);
            String userEmail = getIntent().getStringExtra("username");
            viewHistory.putExtra("username", userEmail);


            startActivity(viewHistory);
        }
        return super.onOptionsItemSelected(item);
    }
}