package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

import java.time.Instant;

public class TicketGrantingTicket {
    private String username;
    private String sessionKey;
    private Instant timestamp;
    private String clientID;

    public TicketGrantingTicket(String username, String sessionKey,String clientID) {
        this.username = username;
        this.sessionKey = sessionKey;
        this.timestamp = Instant.now(); // Set the current time as the timestamp of the TGT
        this.clientID = clientID;
    }
    public String getClientID() {
        return clientID;
    }


    public Instant getTimestamp() {
        return timestamp;
    }


    public String encrypt(AESEncryption aes) {
        String tgtData = username + ":" + sessionKey + ":" + clientID + ":" + timestamp.toString();
        return aes.encrypt(tgtData);
    }



    public static TicketGrantingTicket decrypt(String encryptedData, AESEncryption aes) {
        String decryptedData = aes.decrypt(encryptedData);
        String[] parts = decryptedData.split(":");

//        String correctFormatTimestamp = parts[3]+":"+parts[4]+":"+parts[5];
        String correctFormatTimestamp = parts[3]+":"+parts[4]+":"+parts[5];

        Instant instant = Instant.parse(correctFormatTimestamp);


        return new TicketGrantingTicket(parts[0], parts[1], parts[2], instant);
    }

    public TicketGrantingTicket(String username, String sessionKey, String clientID, Instant timestamp) {
        this.username = username;
        this.sessionKey = sessionKey;
        this.clientID = clientID;
        this.timestamp = timestamp;
    }
}
