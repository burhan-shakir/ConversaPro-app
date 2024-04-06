package com.example.conversapro.KerberosProtocol;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;
import com.example.conversapro.KerberosProtocol.KDC.AuthenticationServer;
import com.example.conversapro.KerberosProtocol.KDC.KeyDistributionCenter;

import org.junit.Test;

public class MainforTestprotical {

        @Test
        public  void main() {


            AESEncryption aes = new AESEncryption();
            AuthenticationServer as = new AuthenticationServer(aes);
            KeyDistributionCenter kdc = new KeyDistributionCenter(as, aes);
            Client client = new Client(kdc, aes, "alice", "password123");
            client.requestService("FileService");


// First communication: The client communicates with the Authentication Server (AS).
// The client sends a username and password request Ticket-Granting Ticket (TGT).
//
// Second communication: the client communicates with the Ticket Granting Server (TGS).
// The client uses the TGT obtained from the AS to request a Service Ticket to access a specific service.
//
// Third communication: the client communicates with the Service Provider (Server).
// The client requests the service using the Service Ticket obtained from the TGS.




        }

}
