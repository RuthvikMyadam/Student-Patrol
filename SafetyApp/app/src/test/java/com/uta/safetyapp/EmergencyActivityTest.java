package com.uta.safetyapp;

import junit.framework.TestCase;

import java.net.URL;

public class EmergencyActivityTest extends TestCase {



    public void testConstructGuardianData() {
        String ExpectedResult = "https://us-central1-safetyapp-uta.cloudfunctions.net/addGuardian?useremail=test@gmail.com&firstname=b&lastname=c&phone=d";
       String ActualResult = EmergencyHelp.ConstructGuardianData("test@gmail.com","b","c","d");
        assertNotNull(ActualResult);
        assertEquals(ExpectedResult,ActualResult);
    }

    public void testGenerateURL() {
        String GuardianURL = "https://us-central1-safetyapp-uta.cloudfunctions.net/addGuardian?useremail=test@gmail.com&firstname=b&lastname=c&phone=d";
        URL ActualResult = EmergencyHelp.GenerateURL(GuardianURL);
        assertEquals(GuardianURL,ActualResult.toString());

    }

    public void testSendData() {
        String GuardianURL = "https://us-central1-safetyapp-uta.cloudfunctions.net/addGuardian?useremail=test@gmail.com&firstname=b&lastname=c&phone=d";
        URL GuardianData = EmergencyHelp.GenerateURL(GuardianURL);
       int ActualResults = EmergencyHelp.SendData(GuardianData);
       assertEquals(200,ActualResults);


    }
}