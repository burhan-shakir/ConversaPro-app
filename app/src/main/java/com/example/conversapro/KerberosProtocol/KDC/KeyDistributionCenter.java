package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

import java.util.HashMap;

public class KeyDistributionCenter {
    private AuthenticationServer authenticationServer;
    private HashMap<String, String> serviceKeys; // 存储服务和对应密钥的映射
    private AESEncryption aes;

    public KeyDistributionCenter(AuthenticationServer as, AESEncryption aes) {
        this.authenticationServer = as;
        this.aes = aes;
        this.serviceKeys = new HashMap<>();
        // Suppose some services and their keys are added
        this.serviceKeys.put("FileService", "serviceKey123");
    }
    public String getServiceKey(String serviceName) {
        return serviceKeys.getOrDefault(serviceName, null);
    }
    public String requestTGT(String username, String password) {
        return authenticationServer.authenticate(username, password);
    }

    public String grantServiceTicket(String encryptedTGT, String serviceName) {

        TicketGrantingTicket tgt = TicketGrantingTicket.decrypt(encryptedTGT, aes);
        // Decrypting TGT to Authenticate Users

        if (tgt != null && serviceKeys.containsKey(serviceName)) {
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
            String serviceTicketInfo = serviceName + ":" + tgt.getUsername() + ":" + serviceSessionKey;
            return aes.encrypt(serviceTicketInfo);
        }
        return null;
    }
}
