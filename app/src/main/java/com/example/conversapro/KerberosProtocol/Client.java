package com.example.conversapro.KerberosProtocol;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;
import com.example.conversapro.KerberosProtocol.KDC.KeyDistributionCenter;

public class Client {
    private KeyDistributionCenter kdc;
    private AESEncryption aes;
    private String username;
    private String password;
    private String clientID="1";

    public Client(KeyDistributionCenter kdc, AESEncryption aes, String username, String password) {
        this.kdc = kdc;
        this.aes = aes;
        this.username = username;
        this.password = password;

    }

    public boolean requestService(String serviceName) {
        String encryptedTGT = kdc.requestTGT(username, password,clientID);
        if (encryptedTGT == null) {
            System.out.println("Authentication failed.");
            return false;
        }
        String encryptedServiceTicket = kdc.grantServiceTicket(encryptedTGT, serviceName);
        if (encryptedServiceTicket == null) {
            System.out.println("Failed to obtain service ticket.");
            return false;
        }
        Server server = new Server(aes, kdc.getServiceKey(serviceName));
        server.accessService(encryptedServiceTicket,"1");
        return true;
    }
}
