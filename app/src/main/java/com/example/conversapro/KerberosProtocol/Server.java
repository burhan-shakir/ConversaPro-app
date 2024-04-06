package com.example.conversapro.KerberosProtocol;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

public class Server {
    private AESEncryption aes;
    private String serviceKey;

    public Server(AESEncryption aes, String serviceKey) {
        this.aes = aes;
        this.serviceKey = serviceKey;
    }

    public void accessService(String encryptedServiceTicket) {
        String ticketInfo = aes.decrypt(encryptedServiceTicket);
        String[] parts = ticketInfo.split(":");
        String serviceName = parts[0];
        String username = parts[1];
        String serviceSessionKey = parts[2];

        if (serviceKey.equals(serviceSessionKey)) {
            System.out.println("Service Ticket is valid. " + username + " is granted access to " + serviceName);
            // 进行服务相关的操作
        } else {
            System.out.println("Invalid Service Ticket.");
        }
    }
}
