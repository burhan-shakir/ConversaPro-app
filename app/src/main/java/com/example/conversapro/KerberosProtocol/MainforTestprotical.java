package com.example.conversapro.KerberosProtocol;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;
import com.example.conversapro.KerberosProtocol.KDC.AuthenticationServer;
import com.example.conversapro.KerberosProtocol.KDC.KeyDistributionCenter;

public class MainforTestprotical {


        public static void main(String[] args) {
            AESEncryption aes = new AESEncryption(); // 假设 AES 实例已正确设置
            AuthenticationServer as = new AuthenticationServer(aes);
            KeyDistributionCenter kdc = new KeyDistributionCenter(as, aes);

            Client client = new Client(kdc, aes, "alice", "password123");

            client.requestService("FileService");


        }

}
