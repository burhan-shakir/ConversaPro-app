package com.example.conversapro;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String name, email, uid, password, description;
    private DatabaseReference mDatabase;
    public User(String name, String email, String password){
        this.name=name;
        this.email = email;
        this.password=password;
        this.description = "";
    }
}
