package com.example.cmp354project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

    Button searchButton, btn_searchForCountries;
    EditText et_accountName;


    Button addMatchToHistoryButton;
    TextView tv_userCurrentlySignedIn, tv_searchCountryInLoggedIn;


    FirebaseFirestore db;
    AlertDialog.Builder builder;
    FirebaseAuth mAuth;

    ArrayAdapter<CharSequence> countrySelectAdapter;
    Dialog countrySelectDialog;
    String countrySelected = "";


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
        btn_searchForCountries = findViewById(R.id.btn_searchForCountries);

        et_accountName = findViewById(R.id.et_accountName);
        tv_userCurrentlySignedIn = findViewById(R.id.tv_userCurrentlySignedIn);
        tv_searchCountryInLoggedIn = findViewById(R.id.tv_searchCountryInLoggedIn);
        tv_searchCountryInLoggedIn.setOnClickListener(this);
        btn_searchForCountries.setOnClickListener(this);


        searchButton.setOnClickListener(this);
        addMatchToHistoryButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        tv_userCurrentlySignedIn.setText("You are currently signed in as user " + mAuth.getCurrentUser().getEmail());


        db = FirebaseFirestore.getInstance();
        String userEmail = getIntent().getStringExtra("username");
        DocumentReference docRef = db.collection(userEmail).document("Account Setup");
        builder = new AlertDialog.Builder(this);


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
                    if (queryDocumentSnapshots.isEmpty()) {

                        Toast.makeText(getApplicationContext(), "This user does not exist",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Intent viewHistory = new Intent(getApplicationContext(), MatchHistory.class);
                        viewHistory.putExtra("searchTerm", et_accountName.getText().toString());
                        startActivity(viewHistory);

                    }
                }
            });


        } else if (v.getId() == R.id.tv_searchCountryInLoggedIn)   {
            countrySelectDialog = new Dialog(LoggedIn.this);
            // set custom dialog
            countrySelectDialog.setContentView(R.layout.searchable_spinner);
            // set custom height and width
            countrySelectDialog.getWindow().setLayout(650, 800);
            // set transparent background
            countrySelectDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //show dialog
            countrySelectDialog.show();
            // Initialize and assign variable
            EditText et_searchChamp = countrySelectDialog.findViewById(R.id.et_searchCountry);
            ListView lv_viewChamp = countrySelectDialog.findViewById(R.id.lv_ViewCountry);
            // Initialize array adapter
            countrySelectAdapter =ArrayAdapter.createFromResource(LoggedIn.this, R.array.country_array, android.R.layout.simple_spinner_item);
            countrySelectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // set adapter
            lv_viewChamp.setAdapter(countrySelectAdapter);
            et_searchChamp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    countrySelectAdapter.getFilter().filter(s);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            lv_viewChamp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // when item selected from list
                    // set selected item on textView
                    // Dismiss dialog
                    tv_searchCountryInLoggedIn.setText(countrySelectAdapter.getItem(position));
                    countrySelected = countrySelectAdapter.getItem(position).toString();
                    countrySelectDialog.dismiss();
                }
            });
        } else if (v.getId() == R.id.btn_searchForCountries) {
            Intent countriesListView = new Intent(getApplicationContext(), CountryHistory.class);
            if (countrySelected.equals("")) {
                Toast.makeText(getApplicationContext(), "Please select a country",
                        Toast.LENGTH_LONG).show();

            } else {
                countriesListView.putExtra("country", countrySelected);
                startActivity(countriesListView);
            }
        }
    }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){

            if (item.getItemId() == R.id.menu_about) {
                builder.setMessage("This application is designed to create a manual League of Legends match history. Users can input their matches through the 'Add match' " +
                        "feature, they can view their history through the 'View match history' feature, and they can view other users matches by searching with the users email. You can also search for users based on their country using the Select country feature.");
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setTitle("About");
                dialog.show();


            } else if (item.getItemId() == R.id.menu_matchHistory) {
                Intent viewHistory = new Intent(this, MatchHistory.class);

                startActivity(viewHistory);
            }
            return super.onOptionsItemSelected(item);
        }
    }