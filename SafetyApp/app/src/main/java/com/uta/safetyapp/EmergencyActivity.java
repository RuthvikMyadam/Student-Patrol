package com.uta.safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EmergencyActivity extends AppCompatActivity {

    EditText firstname,lastname,phone;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_emergency);

        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String S = EmergencyHelp.ConstructGuardianData(Login.LoggedInUser,firstname.getText().toString().trim(),lastname.getText().toString().trim(),phone.getText().toString().trim());
                URL url = EmergencyHelp.GenerateURL(S);
                EmergencyHelp.SendData(url);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        });

    }


}

