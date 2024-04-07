package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

import java.time.Instant;
import java.util.HashMap;

public class KeyDistributionCenter {
    private AuthenticationServer authenticationServer;
    private HashMap<String, String> serviceKeys; // 存储服务和对应密钥的映射
    private HashMap<String, String> clientKey; // 存储服务和对应密钥的映射

    private AESEncryption aes;

    public KeyDistributionCenter(AuthenticationServer as, AESEncryption aes) {
        this.authenticationServer = as;
        this.aes = aes;
        this.serviceKeys = new HashMap<>();
        // Suppose some services and their keys are added
        this.serviceKeys.put("FileService", "serviceKey123");
        this.clientKey = new HashMap<>();
        this.clientKey.put("1", "CK123");
    }
    public String getServiceKey(String serviceName) {
        return serviceKeys.getOrDefault(serviceName, null);
    }
    public String requestTGT(String username, String password,String clientID) {
        return authenticationServer.authenticate(username, password,clientID);
    }

    public String grantServiceTicket(String encryptedTGT, String serviceName) {

        TicketGrantingTicket tgt = TicketGrantingTicket.decrypt(encryptedTGT, aes);
        // Decrypting TGT to Authenticate Users

        if (tgt != null && clientKey.containsKey(tgt.getClientID()) && serviceKeys.containsKey(serviceName)) {
            // Changing the encryption key before generating a new service ticket
            try {
                AESEncryption.changeDefaultKey();
                System.out.println("Encryption key has been updated.");
            } catch (Exception e) {
                System.err.println("Failed to update encryption key: " + e.getMessage());
                return null;
            }

            // generate Service Ticket
            String serviceSessionKey = serviceKeys.get(serviceName);
            Instant now = Instant.now();
            // Use the clientID from the tgt object
            String clientIDFromTGT = tgt.getClientID(); // Get clientID from decrypted TGT
            String serviceTicketInfo = serviceName + ":" + clientIDFromTGT + ":" + serviceSessionKey + ":" + now.toString();
            return aes.encrypt(serviceTicketInfo);
        }
        return null;
    }

}
