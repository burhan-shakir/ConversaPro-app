package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

import java.util.HashMap;

public class AuthenticationServer {
    private static HashMap<String, String> userPasswords= new HashMap<>();; // Storing user names and passwords
    private AESEncryption aes;

    public AuthenticationServer(AESEncryption aes) {
        this.aes = aes;
        // Added preset users
        this.userPasswords.put("yuk42@mcmaster.ca", "123456");
        this.userPasswords.put("xuhuixin2003@gmail.com", "123456");
        this.userPasswords.put("b@gmail.com", "123456");
        this.userPasswords.put("loux8@mcmaster.ca", "123456");
        this.userPasswords.put("hatoumg@mcmaster.ca", "123456");


    }

    public static HashMap<String, String> getUserPasswords(String key) {
        if (key=="123"){
            return userPasswords;
        }
        return null;
    }

    public String authenticate(String username, String password, String clientId) {
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
