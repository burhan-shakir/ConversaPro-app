package com.example.conversapro;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String name, email, password, uid, description;
    private DatabaseReference mDatabase;
    public User(String name, String email, String password){
        this.name=name;
        this.email = email;
        this.password=password;
        this.description = "";
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).setValue(this.name);
        mDatabase.child("users").child(uid).setValue(this.email);
        mDatabase.child("users").child(uid).setValue(this.password);
        mDatabase.child("users").child(uid).setValue(description);
    }
}
