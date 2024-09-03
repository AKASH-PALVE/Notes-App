package com.droidvisuals.notesappfirebase;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    static void showToast(Context context,String message){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReferenceForNotes(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        return FirebaseFirestore.getInstance().collection("notes").document(currentUser.getUid()).collection("my_notes");
    }

    static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
        // Error 1 : changed from toString to toDate
    }

    /*
    static String timestampToString(Timestamp timestamp) {
        try {
            Date date = timestamp.toDate();  // Converts Firebase's Timestamp to a Date object
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";  // Return an empty string or a default value if there’s an error
        }
    }
    */

    /*
    public static String timestampToString(Long timestamp) {
        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }
    */



}
