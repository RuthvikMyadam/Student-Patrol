package com.uta.safetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText name,email,password,phone;
    Button signUpButton;
    TextView loginButton;
    FirebaseAuth firebaseAuthentication;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.fullName);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        signUpButton = findViewById(R.id.signUpButton);
        loginButton = findViewById(R.id.createText);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuthentication = FirebaseAuth.getInstance();


        if(firebaseAuthentication.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_email = email.getText().toString().trim();
                String s_password = password.getText().toString().trim();

                if(TextUtils.isEmpty(s_email)){
                    email.setError("Please enter a valid email address");
                    return;
                }
                if(TextUtils.isEmpty(s_password)){
                    password.setError("Enter a valid password");
                    return;
                }

                if(s_password.length() < 6) {
                    password.setError("Password needs to be atleast 6 characters long");
                    return;
                }

                progressBar.setVisibility(view.VISIBLE);

                firebaseAuthentication.createUserWithEmailAndPassword(s_email,s_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "New Account Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));



                        }
                        else {
                            Toast.makeText(SignUp.this, "Error was found!", Toast.LENGTH_SHORT).show();


                        }
                    }
                });


            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });



    }
}