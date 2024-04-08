package com.example.conversapro.KerberosProtocol.KDC.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Base64;
import java.util.function.Function;

public class KDCFirebaseDatabase implements Database{
    public String retrieveUserPassword(String userId, Function<String, Void> callback) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("users").child(Base64.getEncoder().encodeToString(userId.getBytes())).child("password").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Object value = task.getResult().getValue();
                if (value == null) {
                    callback.apply(null);
                    return;
                }
                String password = value.toString();
                callback.apply(password);
            }else{
                callback.apply(null);
            }
        });
        return null;
    }
}
