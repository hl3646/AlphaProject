package com.example.alphaproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBRef
{
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance("https://alphaproject-eb884-default-rtdb.europe-west1.firebasedatabase.app/");
    public static DatabaseReference refMessage = FBDB.getReference("Message");
}
