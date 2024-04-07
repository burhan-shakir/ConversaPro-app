package com.example.conversapro.KerberosProtocol;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
public class Server {
    private AESEncryption aes;
    private String serviceKey;

    public Server(AESEncryption aes, String serviceKey) {
        this.aes = aes;
        this.serviceKey = serviceKey;
    }

    public boolean accessService(String encryptedServiceTicket, String clientID) {
        String ticketInfo = aes.decrypt(encryptedServiceTicket);
        String[] parts = ticketInfo.split(":");
        String serviceName = parts[0];
        String ticketClientID = parts[1];
        String serviceSessionKey = parts[2];
        Instant ticketTimestamp = Instant.parse(parts[3]+":"+parts[4]+":"+parts[5]);

        if (!serviceKey.equals(serviceSessionKey)) {
            System.out.println("Invalid Service Ticket.");
            return false;
        }

        // Verify clientID
        if (!clientID.equals(ticketClientID)) {
            System.out.println("Invalid clientID.");
            return false;
        }

        // Verify that the timestamp of the ticket is within the allowed time window,  5 minutes here
        if (ticketTimestamp.isBefore(Instant.now().minus(5, ChronoUnit.MINUTES))) {
            System.out.println("Service Ticket has expired.");
            return false;
        }

        System.out.println("Service Ticket is valid. " + ticketClientID + " is granted access to " + serviceName);

        return true;//If normal, perform the service operation and return to the service here


    }
}
