package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

import java.util.Base64;
import java.util.HashMap;

import javax.crypto.spec.SecretKeySpec;

public class ChatServiceServer {
    private SecretKeySpec ssKey;
    private HashMap<String, SecretKeySpec> clients = new HashMap<>();

    public ChatServiceServer(SecretKeySpec ssKey) {
        this.ssKey = ssKey;
    }

    public String authenticate(SSRequest request) {
        SSRequest.DecodedSSRequest decodedSSRequest = request.decode(this.ssKey);
        if (decodedSSRequest.timestampFromClient - decodedSSRequest.timestampFromTGS > 1000 * 60 * 5) {
            throw new RuntimeException("Timestamps are not valid");
        }
        if (!decodedSSRequest.userIdFromClient.equals(decodedSSRequest.userIdFromTGS)) {
            throw new RuntimeException("User IDs do not match");
        }
        clients.put(decodedSSRequest.userIdFromClient, decodedSSRequest.csKey);

        try {
            return AESEncryption.encrypt("" + System.currentTimeMillis(), decodedSSRequest.csKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class SSRequest {
        public String messageE;
        public String messageG;

        private SSRequest(String messageE, String messageG) {
            this.messageE = messageE;
            this.messageG = messageG;
        }

        public static SSRequest encode(String userId, SecretKeySpec csKey, String messageE) {
            try {
                String messageG = userId + ":" + System.currentTimeMillis();
                return new SSRequest(messageE, AESEncryption.encrypt(messageG, csKey));
            } catch (Exception e) {
                return null;
            }
        }

        public DecodedSSRequest decode(SecretKeySpec ssKey) {
            try {
                String decryptedMessageE = AESEncryption.decrypt(this.messageE, ssKey);
                String[] messageEParts = decryptedMessageE.split(":");
                SecretKeySpec csKey = new SecretKeySpec(Base64.getDecoder().decode(messageEParts[0]), "AES");
                String userIdFromTGS = messageEParts[1];
                long timestampFromTGS = Long.parseLong(messageEParts[2]);

                String decryptedMessageG = AESEncryption.decrypt(this.messageG, csKey);
                String[] messageGParts = decryptedMessageG.split(":");
                String userIdFromClient = messageGParts[0];
                long timestampFromClient = Long.parseLong(messageGParts[1]);
                return new DecodedSSRequest(csKey, userIdFromTGS, timestampFromTGS, userIdFromClient, timestampFromClient);
            } catch (Exception e) {
                return null;
            }
        }

        public static class DecodedSSRequest {
            public SecretKeySpec csKey;
            public String userIdFromTGS;
            public long timestampFromTGS;
            public String userIdFromClient;
            public long timestampFromClient;

            private DecodedSSRequest(SecretKeySpec csKey, String userIdFromTGS, long timestampFromTGS, String userIdFromClient, long timestampFromClient) {
                this.csKey = csKey;
                this.userIdFromTGS = userIdFromTGS;
                this.timestampFromTGS = timestampFromTGS;
                this.userIdFromClient = userIdFromClient;
                this.timestampFromClient = timestampFromClient;
            }
        }
    }
}
