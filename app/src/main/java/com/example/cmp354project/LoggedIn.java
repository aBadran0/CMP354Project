package com.example.cmp354project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;


public class LoggedIn extends AppCompatActivity implements View.OnClickListener {

    Button searchButton;
    EditText et_accountName;

    Spinner regionSelect;

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


        searchButton = findViewById(R.id.btn_searchSummoner);
        addMatchToHistoryButton = findViewById(R.id.btn_addMatchToHistory);

        et_accountName = findViewById(R.id.et_accountName);


        searchButton.setOnClickListener(this);
        addMatchToHistoryButton.setOnClickListener(this);


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
                            singleton.getInstance().setIntValue(L.intValue()+1);

                        } else {
                            singleton.getInstance().setIntValue(1);
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


    }


    @Override
    public void onClick(View v) {

         if (v.getId() == R.id.btn_addMatchToHistory) {
            Intent addMatch = new Intent(this, AddMatch.class);
            startActivity(addMatch);

        } else if (v.getId() == R.id.btn_searchSummoner) {
             CollectionReference reference = db.collection(et_accountName.getText().toString());
             reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                 @Override
                 public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                     if(queryDocumentSnapshots.isEmpty()){

                         Toast.makeText(getApplicationContext(), "This user does not exist",
                                 Toast.LENGTH_LONG).show();
                     }
                     else {
                         Intent viewHistory = new Intent(getApplicationContext(), MatchHistory.class);
                         viewHistory.putExtra("searchTerm", et_accountName.getText().toString());
                         startActivity(viewHistory);

                     }
                 }
             });





         }



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_about) {


        } else if (item.getItemId() == R.id.menu_matchHistory)
        {
            Intent viewHistory = new Intent(this, MatchHistory.class);

            startActivity(viewHistory);
        }
        return super.onOptionsItemSelected(item);
    }
}