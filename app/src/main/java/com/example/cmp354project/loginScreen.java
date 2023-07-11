package com.example.cmp354project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class loginScreen extends AppCompatActivity implements View.OnClickListener
{

     FirebaseAuth mAuth;
     EditText et_Email_Input;
     EditText et_Password_Input;
     Button btn_login;
     Button btn_sign_up;

    FirebaseFirestore db2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        mAuth = FirebaseAuth.getInstance();
        et_Email_Input= findViewById(R.id.et_Email_Input);
        et_Password_Input= findViewById(R.id.et_Password_Input);

        btn_login = findViewById(R.id.btn_login);
        btn_sign_up = findViewById(R.id.btn_sign_up);

        btn_login.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
        db2 = FirebaseFirestore.getInstance();




    }

        @Override
        public void onClick(View v)
        {


            if (et_Password_Input.getText().toString().isEmpty()) return;

            if (v.getId()==R.id.btn_login) {
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
            else if (v.getId()==R.id.btn_sign_up)
            {


                Log.d("Hi", "AAA");
                mAuth.createUserWithEmailAndPassword(et_Email_Input.getText().toString(), et_Password_Input.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("Account Setup", "false");
                                    db2.collection(et_Email_Input.getText().toString()).document("Account Setup").set(user);
                                    Toast.makeText(getApplicationContext(), "Create User With Email : success\nPlease login",
                                            Toast.LENGTH_SHORT).show();


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }


}


