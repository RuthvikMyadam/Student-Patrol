import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

admin.initializeApp();

export const accountCreate = functions.auth.user().onCreate((user) => {
  const userDoc = {
    "email": user.email,
    "displayName": user.displayName,
    "phoneNumber": user.phoneNumber,
    "id": user.uid,
  };
  admin.firestore().collection("users")
      .doc(user.email!)
      .set(userDoc).then((writeResult) => {
        console.log("User Created result:", writeResult);
        return user;
      }).catch((err) => {
        console.log(err);
        return err;
      });
});

export const addLocation = functions.https
    .onRequest(async (request, response) => {
    // Push the new message into Firestore using the Firebase Admin SDK.
      const writeResult = await admin.firestore().collection("locations")
          .add(
              {
                "email": request.query.email,
                "lat": request.query.lat,
                "long": request.query.long,
                "notified": false,
              });
      // Send back a message that we've successfully written the message
      response.json({result: `location with ID: ${writeResult.id} added.`});
    });


export const notifyGuardian = functions.firestore
    .document("/locations/{documentId}")
    .onCreate(async (snap, context) => {
    // https://firebase.google.com/docs/functions/firestore-events
      console.log(snap.data());
      admin.firestore().collection("emergencycontacts")
          .where("userEmail", "==", snap.data().email)
          .get()
          .then((querySnapshot) => {
            querySnapshot.forEach((doc) => {
              console.log(doc.id, " => ", doc.data());
              admin
                  .firestore()
                  .collection("messages")
                  .add({
                    to: doc.data().phone,
                    body: "https://www.google.com/maps/search/?api=1&query=" + snap.data().lat + "," + snap.data().long,
                  })
                  .then(() => console.log("Queued message for delivery!"))
                  .catch((reason) => {
                    console.log("message sending failed with reason:" + reason);
                  });
            });
          })
          .catch((error) => {
            console.log("Error getting documents: ", error);
          });
      return;
    });


export const addGuardian = functions.https
    .onRequest(async (request, response) => {
    // Push the new message into Firestore using the Firebase Admin SDK.
      console.log(request.query);
      const writeResult = await admin.firestore()
          .collection("emergencycontacts")
          .add(
              {
                "userEmail": request.query.useremail,
                "firstName": request.query.firstname,
                "lastName": request.query.lastname,
                "phone": request.query.phone,
              });
      response.json({
        result: `emergencycontact with ID: ${writeResult.id} 
      added for ${request.query.useremail}`,
      });
    });
