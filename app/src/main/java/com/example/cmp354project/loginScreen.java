package com.example.cmp354project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class loginScreen extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText et_Email_Input;
    EditText et_Password_Input;
    TextView tv_SelectCountry;
    Button btn_login;
    Button btn_sign_up;

    Dialog countrySelectDialog;

    FirebaseFirestore db2;
    ArrayAdapter<CharSequence> countrySelectAdapter;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        mAuth = FirebaseAuth.getInstance();
        et_Email_Input = findViewById(R.id.et_Email_Input);
        et_Password_Input = findViewById(R.id.et_Password_Input);
        tv_SelectCountry = findViewById(R.id.tv_SelectCountry);




        btn_login = findViewById(R.id.btn_login);
        btn_sign_up = findViewById(R.id.btn_sign_up);

        btn_login.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
        tv_SelectCountry.setOnClickListener(this);
        db2 = FirebaseFirestore.getInstance();


    }

    @Override
    public void onClick(View v) {


       /* if (et_Password_Input.getText().toString().isEmpty()) return;*/

        if (v.getId() == R.id.btn_login) {
            Log.d("Hello", "AAA");

            //Create a new signIn method which takes in an email address and password,
            // validates them, and then signs a user in with the signInWithEmailAndPassword method.
            mAuth.signInWithEmailAndPassword(et_Email_Input.getText().toString(), et_Password_Input.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(getApplicationContext(), LoggedIn.class);
                                i.putExtra("username", user.getEmail());
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        //----------------------------------------
        //Create a new signIn method which takes in an email address and password,
        // validates them, and then signs a user in with the signInWithEmailAndPassword method.
        else if (v.getId() == R.id.btn_sign_up) {


                mAuth.createUserWithEmailAndPassword(et_Email_Input.getText().toString(), et_Password_Input.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if(tv_SelectCountry.getText().toString().equals("Select Country")||et_Email_Input.getText().toString().equals("")|| et_Password_Input.getText().toString().equals(""))
                                    {
                                        Toast.makeText(getApplicationContext(), "One of your fields is missing or is incorrect",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        // Sign in success, update UI with the signed-in user's information
                                        Map<String, Object> accSetup = new HashMap<>();
                                        accSetup.put("Account Setup", "false");
                                        Map<String, Object> users = new HashMap<>();
                                        users.put("Email", et_Email_Input.getText().toString());
                                        users.put("Country", tv_SelectCountry.getText().toString());
                                        db2.collection(et_Email_Input.getText().toString()).document("Account Setup").set(accSetup);
                                        db2.collection("Users").document(et_Email_Input.getText().toString()).set(users);
                                        Toast.makeText(getApplicationContext(), "Create User With Email : success\nPlease login",
                                                Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        else if (v.getId() == R.id.tv_SelectCountry) {
            {
                countrySelectDialog = new Dialog(loginScreen.this);
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
                countrySelectAdapter =ArrayAdapter.createFromResource(loginScreen.this, R.array.country_array, android.R.layout.simple_spinner_item);
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
                        tv_SelectCountry.setText(countrySelectAdapter.getItem(position));
                        countrySelectDialog.dismiss();
                    }
                });
            }






        }


    }



        }


