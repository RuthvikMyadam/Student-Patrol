# Safety App

* Safety App is user friendly application built in Android Studio,

* It is simple to implement and easy to understand.

## Prerequisites

* Android Studio with version newer than Oreo or Oreo.
* Firebase and Firestore database.
* Twilio subscription

## Steps to run the Android project

* Clone the source code from

* Launch the source code in android studio

* Build the source

* Hit the run button
  * You can see a emulator getting opened with our application( Safety App )

## Steps to run the Backend

* This is used to run code in the cloud with no servers or containers to manage with our scalable, pay-as-you-go functions as a service (FaaS) product

* A step by step procedure to develop and deploy on google cloud (<https://cloud.google.com/functions#section-4>)

### Overview of steps for building and deploying cloud functions

* Install nodejs

* npm install -g firebase-tools

* npm install firebase-functions@latest firebase-admin@latest --save

* A firebase account needs to be created

* Once created, use the command ```firebase login``` to login to your firebase account from the terminal

* use command ```firebase init firestore``` to connect to the firestore database

* use command ```firebase init functions``` to setup functions to write the backend code

* run command ```npm run build``` to build the code

* run command ```firebase deploy``` to deploy code to the google cloud

## How to use the Application

* If you are a first time user then you need to create your account using sign up option or else you could just login directly with your username and password you created.

* Once logged in you can see a new page with add guardian, sos and logout option.

* Add guardian can be used to add the emergency contacts

* Provide the first name, last name and phone number of the emergency contact

* On click of the sos button, the live location of the user will be shared with the emergency contacts til the user hits stop button.
