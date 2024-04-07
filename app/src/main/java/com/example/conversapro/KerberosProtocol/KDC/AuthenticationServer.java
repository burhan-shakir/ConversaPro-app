package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class AuthenticationServer {
    private HashMap<String, String> userPasswords; // Storing user names and passwords
    private AESEncryption aes;
    FirebaseAuth mAuth;
    Boolean login;

    public AuthenticationServer(AESEncryption aes) {
        this.aes = aes;
        this.userPasswords = new HashMap<>();
        // Added preset users
        this.userPasswords.put("alice", "password123");
        this.userPasswords.put("bob", "password321");
        mAuth = FirebaseAuth.getInstance();
    }
    public Boolean login(String email, String password,String clientId) {
        login = false;
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                login = true;
            }
        });
        return login;
    }
}
