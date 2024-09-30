package com.uta.safetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;

import javax.net.ssl.HttpsURLConnection;

public class SOSActivity extends AppCompatActivity {
    Button stop;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sosactivity);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(30000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

//instantiating the LocationCallBack
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    if (locationResult == null) {
                        return;
                    }
                    //Showing the latitude, longitude and accuracy on the home screen.
                    for (Location location : locationResult.getLocations()) {
                        String LoggedInUser = Login.LoggedInUser;
                        String message = MessageFormat.format("https://us-central1-safetyapp-uta.cloudfunctions.net/addLocation?email={0}&lat={1}&long={2}",LoggedInUser, location.getLatitude(),
                                location.getLongitude());
                        HttpClient httpClient = new DefaultHttpClient();
//
                        URL url = null;
                        try {
                            url = new URL(message);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        URLConnection urlConn = null;
                        try {
                            urlConn = url.openConnection();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        HttpsURLConnection httpsConn = (HttpsURLConnection) urlConn;
                        httpsConn.setAllowUserInteraction(false);
                        httpsConn.setInstanceFollowRedirects(true);
                        try {
                            httpsConn.setRequestMethod("GET");
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        }
                        try {
                            httpsConn.connect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            int resCode = httpsConn.getResponseCode();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        try {
////                            fusedLocationProviderClient.getLooper().getThread().wait(10000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        stop = findViewById(R.id.stopbutton);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}