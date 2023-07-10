package com.example.cmp354project;

import static android.content.ContentValues.TAG;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatchHistory extends AppCompatActivity implements AdapterView.OnItemClickListener {


    EditText et_champlist;
    Dialog champDialog;

    TextView tv_searchChamps;

    ListView lv_Matches;

    ArrayAdapter<CharSequence> champListAdapter;
    ArrayAdapter<CharSequence> champSearch;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_history);
        //  et_champlist = findViewById(R.id.et_Champlist);
        tv_searchChamps = findViewById(R.id.tv_searchChamp);
        lv_Matches = findViewById(R.id.lv_matches);


        // et_champlist.setOnClickListener(this);
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
                EditText et_searchChamp = champDialog.findViewById(R.id.et_searchChamp);
                ListView lv_viewChamp = champDialog.findViewById(R.id.lv_ViewChamp);
                // Initialize array adapter
                champListAdapter =ArrayAdapter.createFromResource(MatchHistory.this, R.array.Champions, android.R.layout.simple_spinner_item);
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
                       // champSearch = new ArrayAdapter<CharSequence>();



                        updateDisplay();

                    }
                });
            }
        });
        updateDisplay();

    }

    public void updateDisplay() {
        db.collection("test2@aus.edu")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 1;
                            ArrayList<HashMap<String, String>> data =
                                    new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentTitle = document.getId();
                                String searchTerm = tv_searchChamps.getText().toString();

                                    if(documentTitle.equals("Match " + i))
                                    {
                                        HashMap<String, String> map = new HashMap<>();
                                        String ChampName = document.getString("Champion");
                                        String Result = document.getString("Result");

                                        if(searchTerm.equals(ChampName))
                                        {
                                            map.put("Champion", ChampName);
                                            map.put("Result",Result );
                                            data.add(map);
                                        }
                                        else {
                                            map.put("Champion", ChampName);
                                            map.put("Result", Result);
                                            data.add(map);
                                        }
                                        int resource = R.layout.listview_item;
                                        String[] from = {"Champion", "Result"};
                                        int[] to = {R.id.tv_Champion, R.id.tv_result};

                                        // create and set the adapter
                                        SimpleAdapter adapter =
                                                new SimpleAdapter(MatchHistory.this, data, resource, from, to);
                                        lv_Matches.setAdapter(adapter);
                                        i++;
                                    }

                            }
                        } else {

                        }
                    }
                });


        }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, MatchView.class);


        this.startActivity(intent);
    }
}
