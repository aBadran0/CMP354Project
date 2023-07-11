package com.example.cmp354project;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class MatchView extends AppCompatActivity {

    TextView tv_Champ, tv_Role, tv_creepScore, tv_Items, tv_Result, tv_Damage;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String champName;
    String creepScore;
    String damage ;
    String items ;
    String result ;
    String role ;
    ImageView splashart;
    String summonerName = "";

    FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_view);

        tv_Champ = findViewById(R.id.tv_ChampName);
        tv_Role = findViewById(R.id.tv_Role);
        tv_creepScore = findViewById(R.id.tv_creepScore);
        tv_Items = findViewById(R.id.tv_Items);
        tv_Result = findViewById(R.id.tv_matchResult);
        tv_Damage = findViewById(R.id.tv_Damage);
        splashart = findViewById(R.id.splashArt);


        Intent getMatch = getIntent();
        int position = getMatch.getIntExtra("position",0);
        mAuth = FirebaseAuth.getInstance();


       // tv_Champ.setText(champName);
        if(getIntent().hasExtra("searchTerm"))
        {
            summonerName = getIntent().getStringExtra("searchTerm");
        }
        else {
            summonerName = mAuth.getCurrentUser().getEmail();
        }
        if(getIntent().hasExtra("Match Number"))
        {
            position = getIntent().getIntExtra("Match Number", 0);
        }



        DocumentReference docRef = db.collection(summonerName).document("Match " + position);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!isFinishing() && task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        champName = document.getString("Champion");
                        creepScore = document.getString("Creep Score");
                        damage = document.getString("Damage");
                        items = document.getString("Items");
                        role = document.getString("Role");
                        result = document.getString("Result");

                        // Pass the data to the next activity only if it is available


                    }

                }

                Picasso.get().load("https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+champName+"_0.jpg").into(splashart);
                tv_Champ.setText(champName);
                tv_creepScore.setText(creepScore);
                tv_Role.setText(role);
                tv_Items.setText(items);
                if(result.equals("Win"))
                {

                    tv_Result.setText(result);
                    tv_Result.setTextColor(Color.parseColor("#008000"));
                }
                else
                {
                    tv_Result.setText(result);
                    tv_Result.setTextColor(Color.parseColor("#FF0000"));
                }

                tv_Damage.setText(damage);

            }

        });

    }
}
