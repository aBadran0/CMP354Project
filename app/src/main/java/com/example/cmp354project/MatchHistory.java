package com.example.cmp354project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchHistory extends AppCompatActivity /*implements View.OnClickListener */{


    Dialog champDialog;

    TextView tv_searchChamps, tv_MatchHistoryOfWho;
    ListView lv_Matches;

    ArrayAdapter<CharSequence> champListAdapter;
    ArrayAdapter<CharSequence> customChampListAdapter;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    String currentUser = "";
    DocumentReference docRef;
    int finalPosition;

    ArrayList<Match> data ;
    ArrayList<HashMap<String,String>> customdata;
    ArrayList<Integer> MatchList;

    String searchterm = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_history);
        tv_searchChamps = findViewById(R.id.tv_searchChamp);
        lv_Matches = findViewById(R.id.lv_users);
        tv_MatchHistoryOfWho = findViewById(R.id.tv_MatchHistoryOfWho);
            mAuth = FirebaseAuth.getInstance();

        if(getIntent().hasExtra("searchTerm"))
        {
            currentUser = getIntent().getStringExtra("searchTerm");
            tv_MatchHistoryOfWho.setText("Match History of " + currentUser);

            Log.d("Current user intent ", currentUser);
        }
        else {
            currentUser = mAuth.getCurrentUser().getEmail();
            tv_MatchHistoryOfWho.setText("Match History of " + currentUser);
            Log.d("Current user auth", currentUser);
        }


        tv_searchChamps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize dialog
                champDialog = new Dialog(MatchHistory.this);
                // set custom dialog
                champDialog.setContentView(R.layout.searchable_spinner);
                // set custom height and width
                champDialog.getWindow().setLayout(650, 800);
                // set transparent background
                champDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //show dialog
                champDialog.show();
                // Initialize and assign variable
                EditText et_searchChamp = champDialog.findViewById(R.id.et_searchCountry);
                ListView lv_viewChamp = champDialog.findViewById(R.id.lv_ViewCountry);
                // Initialize array adapter
                champListAdapter = ArrayAdapter.createFromResource(MatchHistory.this, R.array.Champions, android.R.layout.simple_spinner_item);
                champListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // set adapter
                lv_viewChamp.setAdapter(champListAdapter);
                et_searchChamp.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        champListAdapter.getFilter().filter(s);
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
                        tv_searchChamps.setText(champListAdapter.getItem(position));

                        // Dismiss dialog
                        champDialog.dismiss();
                        String search_term = tv_searchChamps.getText().toString();


                    }
                });
            }
        });
        updateDisplay(searchterm);
        lv_Matches.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                    finalPosition = MatchList.get(position);
                docRef = db.collection(currentUser).document("Match " + finalPosition);

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!isFinishing() && task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                                String data = document.getString("Champion");

                                // Pass the data to the next activity only if it is available
                                if (data != null) {
                                    Intent intent = new Intent(MatchHistory.this, MatchView.class);
                                    intent.putExtra("position", finalPosition);
                                    intent.putExtra("searchTerm", currentUser);

                                    startActivity(intent);

                                } else {
                                    // Handle the case when the data is null
                                }

                        } else {

                        }
                    }
                });
            }
        });
    }

    public void updateDisplay(String term) {
        db.collection(currentUser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override

            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    data = new ArrayList<>();
                    customdata = new ArrayList<>();
                    MatchList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String documentTitle = document.getId();
                        String searchTerm = tv_searchChamps.getText().toString();

                        if (!documentTitle.equals("Account Setup")) {
                            HashMap<String, String> map = new HashMap<>();
                            String ChampName = document.getString("Champion");
                            String Result = document.getString("Result");

                            if (!searchTerm.contentEquals("None") && !searchTerm.contentEquals("") ){
                                if (searchTerm.contentEquals(ChampName)) {
                                    map.put("Champion", ChampName);
                                    map.put("Result", Result);
                                    customdata.add(map);
                                    MatchList.add(Integer.parseInt(documentTitle.substring(6)));
                                    int resource = R.layout.listview_item;
                                    String[] from = {"Champion", "Result"};
                                    int[] to = {R.id.tv_Champion, R.id.tv_result};
                                    SimpleAdapter adapter =
                                            new SimpleAdapter(MatchHistory.this, customdata, resource, from, to);
                                    lv_Matches.setAdapter(adapter);
                                }}
                            else {
                                    map.put("Champion", ChampName);
                                    map.put("Result", Result);
                                    customdata.add(map);
                                    MatchList.add(Integer.parseInt(documentTitle.substring(6)));
                                int resource = R.layout.listview_item;
                                    String[] from = {"Champion", "Result"};
                                    int[] to = {R.id.tv_Champion, R.id.tv_result};
                                    SimpleAdapter adapter =
                                            new SimpleAdapter(MatchHistory.this, customdata, resource, from, to);
                                    lv_Matches.setAdapter(adapter);

                                }


                            // create and set the adapter
                        }

                    }
                } else {

                }
            }
        });


    }

    public void onClick_search(View v)
    {
        String searchTerm = tv_searchChamps.getText().toString();

        updateDisplay(searchTerm);

    }
}
