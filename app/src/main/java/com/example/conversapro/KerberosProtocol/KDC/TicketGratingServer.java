package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

import java.util.Arrays;
import java.util.Base64;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

public class TicketGratingServer {
    private final SecretKeySpec tgsKey;
    private final SecretKeySpec ssKey;

    public TicketGratingServer(SecretKeySpec tgsKey, SecretKeySpec ssKey) {
        this.tgsKey = tgsKey;
        this.ssKey = ssKey;
    }

    public void authenticateClientService(TGSRequest request, Function<TGSResponse, Void> callback) {
        TGSRequest.DecodedTGSRequest decodedTGSRequest = request.decode(this.tgsKey);
        if (decodedTGSRequest.timestampFromClient - decodedTGSRequest.timestampFromTGT > 1000 * 60 * 5) {
            callback.apply(null);
            return;
        }
        if(!decodedTGSRequest.userIdFromClient.equals(decodedTGSRequest.userIdFromTGT)) {
            callback.apply(null);
            return;
        }

        TGSResponse response = TGSResponse.encode(this.ssKey, decodedTGSRequest.sessionKeyFromTGT, decodedTGSRequest.userIdFromClient);
        callback.apply(response);
    }

    public static class TGSResponse {
        public String messageE;
        public String messageF;

        private TGSResponse(String messageE, String messageF) {
            this.messageE = messageE;
            this.messageF = messageF;
        }

        public static TGSResponse encode(SecretKeySpec ssKey, SecretKeySpec sessionKey, String userId) {
            try {
                SecretKeySpec csKey = AESEncryption.generateKey(128);
                String messageE = Base64.getEncoder().encodeToString(csKey.getEncoded()) + ":" + userId + ":" + System.currentTimeMillis();
                String messageF = Base64.getEncoder().encodeToString(csKey.getEncoded());

                return new TGSResponse(AESEncryption.encrypt(messageE, ssKey), AESEncryption.encrypt(messageF, sessionKey));
            } catch (Exception e) {
                return null;
            }
        }

        public DecodedTGSResponse decode(SecretKeySpec sessionKey){
            try {
                String decryptedMessageF = AESEncryption.decrypt(this.messageF, sessionKey);
                SecretKeySpec csKey = new SecretKeySpec(Base64.getDecoder().decode(decryptedMessageF), "AES");
                return new DecodedTGSResponse(this.messageE, csKey);
            } catch (Exception e) {
                return null;
            }
        }

        public static class DecodedTGSResponse {
            public String messageE;
            public SecretKeySpec csKey;

            private DecodedTGSResponse(String messageE, SecretKeySpec csKey) {
                this.messageE = messageE;
                this.csKey = csKey;
            }
        }
    }

    public static class TGSRequest {
        public String messageC1;
        public String messageC2;
        public String messageD;

        private TGSRequest(String messageC1, String messageC2, String messageD) {
            this.messageC1 = messageC1;
            this.messageC2 = messageC2;
            this.messageD = messageD;
        }

        public static TGSRequest encode(String userId, SecretKeySpec sessionKey, String tgt, String serviceID) {
            String messageD;
            try {
                messageD = AESEncryption.encrypt(userId + ":" + System.currentTimeMillis(), sessionKey);
            } catch (Exception e) {
                return null;
            }
            return new TGSRequest(serviceID, tgt, messageD);
        }

        public DecodedTGSRequest decode(SecretKeySpec tgsKey) {
            try {
                String serviceId = this.messageC1;
                String decryptedTGT = AESEncryption.decrypt(this.messageC2, tgsKey);
                String[] tgtParts = decryptedTGT.split(":");
                SecretKeySpec sessionKeyFromTGT = new SecretKeySpec(Base64.getDecoder().decode(tgtParts[0]), "AES");
                String userIdFromTGT = tgtParts[1];
                long timestampFromTGT = Long.parseLong(tgtParts[2]);

                String decryptedMessageD = AESEncryption.decrypt(this.messageD, sessionKeyFromTGT);
                String[] messageDParts = decryptedMessageD.split(":");
                String userIdFromClient = messageDParts[0];
                long timestampFromClient = Long.parseLong(messageDParts[1]);
                return new DecodedTGSRequest(serviceId, sessionKeyFromTGT, userIdFromTGT, timestampFromTGT, userIdFromClient, timestampFromClient);
            } catch (Exception e) {
                return null;
            }
        }

        public static class DecodedTGSRequest {
            public String serviceId;
            public SecretKeySpec sessionKeyFromTGT;
            public String userIdFromTGT;
            public long timestampFromTGT;
            public String userIdFromClient;
            public long timestampFromClient;

            private DecodedTGSRequest(String serviceId, SecretKeySpec sessionKeyFromTGT, String userIdFromTGT, long timestampFromTGT, String userIdFromClient, long timestampFromClient) {
                this.serviceId = serviceId;
                this.sessionKeyFromTGT = sessionKeyFromTGT;
                this.userIdFromTGT = userIdFromTGT;
                this.timestampFromTGT = timestampFromTGT;
                this.userIdFromClient = userIdFromClient;
                this.timestampFromClient = timestampFromClient;
            }
        }
    }
}
