package com.example.cmp354project;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


public class CountryHistory extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    ListView lv_users;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersincountry);
        updateDisplay();
        lv_users = findViewById(R.id.lv_users);


    }

    public void updateDisplay(){
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList <HashMap<String,String>> data = new ArrayList<>();
                String selectedCountry = getIntent().getStringExtra("country");
                for(QueryDocumentSnapshot document: task.getResult())
                {
                    if(document.getString("Country").equals(selectedCountry))
                    {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("user", document.getId());
                        data.add(map);

                    }
                    int resource = R.layout.country_listview_item;
                    String[] from ={"user"};
                    int[] to = {R.id.tv_Champion};

                    SimpleAdapter adapter = new SimpleAdapter(CountryHistory.this, data, resource, from, to);
                    lv_users.setAdapter(adapter);



                }

            }
        });

    }





}
