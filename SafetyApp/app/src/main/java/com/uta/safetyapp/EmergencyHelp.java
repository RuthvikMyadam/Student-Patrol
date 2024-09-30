package com.uta.safetyapp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EmergencyHelp {
    public static String ConstructGuardianData(String loggedInUser, String firstname, String lastname, String phone) {
        String s = (new StringBuilder())
                .append("https://us-central1-safetyapp-uta.cloudfunctions.net/addGuardian?useremail=")
                .append(loggedInUser)
                .append("&firstname=")
                .append(firstname)
                .append("&lastname=")
                .append(lastname)
                .append("&phone=")
                .append(phone).toString();
        return s;
    }

    public static URL GenerateURL(String s) {
        URL url = null;//);// new URL("https","us-central1-safetyapp-uta.cloudfunctions.net/addGuardian",443,);
        try {
            url = new URL(s);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return url;
        }
    }

    public static int SendData(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return 400;
        }
    }


}
