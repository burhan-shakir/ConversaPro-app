package com.example.conversapro.KerberosProtocol;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;
import com.example.conversapro.KerberosProtocol.KDC.AuthenticationServer;
import com.example.conversapro.KerberosProtocol.KDC.ChatServiceServer;
import com.example.conversapro.KerberosProtocol.KDC.Database;
import com.example.conversapro.KerberosProtocol.KDC.MockDatabase;
import com.example.conversapro.KerberosProtocol.KDC.TicketGratingServer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.spec.SecretKeySpec;

public class Server {

    public AuthenticationServer authenticationServer;
    public TicketGratingServer ticketGratingServer;
    public ChatServiceServer chatServiceServer;

    public Server(Database database) {
        try {
            SecretKeySpec tgsKey = AESEncryption.generateKey(128);
            SecretKeySpec ssKey = AESEncryption.generateKey(128);
            authenticationServer = new AuthenticationServer(database, tgsKey);
            ticketGratingServer = new TicketGratingServer(tgsKey, ssKey);
            chatServiceServer = new ChatServiceServer(ssKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
