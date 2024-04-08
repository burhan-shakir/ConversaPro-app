package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;
import com.example.conversapro.KerberosProtocol.KDC.database.Database;

import java.util.Base64;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

public class AuthenticationServer {

    private final Database database;
    private final SecretKeySpec tgsKey;

    public AuthenticationServer(Database database, SecretKeySpec tgsKey) {
        this.database = database;
        this.tgsKey = tgsKey;
    }

    public void authenticateClient(String userId, Function<ASMessage, Void> callback) {
        this.database.retrieveUserPassword(userId, (password) -> {
            if (password == null) {
                callback.apply(null);
                return null;
            }

            try {
                SecretKeySpec sessionKey = AESEncryption.generateKey(256);
                callback.apply(ASMessage.encode(password, sessionKey, this.tgsKey, userId));
            } catch (Exception e) {
                callback.apply(null);
            }
            return null;
        });
    }

    public static class ASMessage {
        public String messageA;
        public String tgt;

        private ASMessage(String messageA, String tgt) {
            this.messageA = messageA;
            this.tgt = tgt;
        }

        static ASMessage encode(String password, SecretKeySpec sessionKey, SecretKeySpec tgsKey, String userId) {
            try {
                SecretKeySpec userKey = AESEncryption.generateKeyFromPassword(password);
                byte[] sessionKeyBytes = sessionKey.getEncoded();
                String messageA = AESEncryption.encrypt(Base64.getEncoder().encodeToString(sessionKeyBytes), userKey);
                String messageB = AESEncryption.encrypt(Base64.getEncoder().encodeToString(sessionKeyBytes) + ":" + userId + ":" + System.currentTimeMillis(), tgsKey);
                return new ASMessage(messageA, messageB);
            } catch (Exception e) {
                return null;
            }
        }

        public DecodedASMessage decode(String password) {
            try {
                SecretKeySpec userKey = AESEncryption.generateKeyFromPassword(password);
                String decryptedMessageA = AESEncryption.decrypt(this.messageA, userKey);
                SecretKeySpec sessionKey = new SecretKeySpec(Base64.getDecoder().decode(decryptedMessageA), "AES");
                return new DecodedASMessage(sessionKey, this.tgt);
            } catch (Exception e) {
                return null;
            }
        }

        public static class DecodedASMessage {
            public SecretKeySpec sessionKey;
            public String tgt;

            private DecodedASMessage(SecretKeySpec sessionKey, String tgt) {
                this.sessionKey = sessionKey;
                this.tgt = tgt;
            }
        }
    }
}
