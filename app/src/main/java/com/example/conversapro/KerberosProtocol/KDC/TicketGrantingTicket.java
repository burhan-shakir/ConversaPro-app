package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

public class TicketGrantingTicket {
    private String username;
    private String sessionKey; // The session key is usually generated with the TGT

    public TicketGrantingTicket(String username, String sessionKey) {
        this.username = username;
        this.sessionKey = sessionKey;
    }

    //  Often the TGT will contain more information, such as expiration dates, etc., but this is simplified here.

    public String getUsername() {
        return username;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    // cryptographic method is assumed to protect TGT
    public String encrypt(AESEncryption aes) {
        String tgtData = username + ":" + sessionKey;
        return aes.encrypt(tgtData);
    }

    public static TicketGrantingTicket decrypt(String encryptedData, AESEncryption aes) {
        String decryptedData = aes.decrypt(encryptedData);
        String[] parts = decryptedData.split(":");
        return new TicketGrantingTicket(parts[0], parts[1]);
    }
}
