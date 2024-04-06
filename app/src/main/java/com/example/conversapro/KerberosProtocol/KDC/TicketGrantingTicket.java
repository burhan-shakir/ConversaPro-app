package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

public class TicketGrantingTicket {
    private String username;
    private String sessionKey; // 会话密钥通常与 TGT 一起生成

    public TicketGrantingTicket(String username, String sessionKey) {
        this.username = username;
        this.sessionKey = sessionKey;
    }

    // 通常 TGT 会包含更多信息，如有效期等，这里简化处理

    public String getUsername() {
        return username;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    // TGT 应当被加密以保护其内容，这里假设一个加密方法
    public String encrypt(AESEncryption aes) {
        String tgtData = username + ":" + sessionKey;
        return aes.encrypt(tgtData);
    }

    // 类似地，应当有一个对应的解密方法
    public static TicketGrantingTicket decrypt(String encryptedData, AESEncryption aes) {
        String decryptedData = aes.decrypt(encryptedData);
        String[] parts = decryptedData.split(":");
        return new TicketGrantingTicket(parts[0], parts[1]);
    }
}
