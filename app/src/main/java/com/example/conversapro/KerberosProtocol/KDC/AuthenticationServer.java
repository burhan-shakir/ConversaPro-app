package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

import java.util.HashMap;

public class AuthenticationServer {
    private HashMap<String, String> userPasswords; // Storing user names and passwords
    private AESEncryption aes;

    public AuthenticationServer(AESEncryption aes) {
        this.aes = aes;
        this.userPasswords = new HashMap<>();
        // Added preset users
        this.userPasswords.put("alice", "password123");
        this.userPasswords.put("bob", "password321");
    }

    public String authenticate(String username, String password,String clientId) {
        // Check for user password match
        if (userPasswords.containsKey(username) && userPasswords.get(username).equals(password)) {
            // If it matches, create a TGT
            String sessionKey = "SessionKeyFor" + username; // normal Session keys are  randomly generated
            TicketGrantingTicket tgt = new TicketGrantingTicket(username, sessionKey,clientId);
            return tgt.encrypt(aes);
        }
        return null;
    }
}
