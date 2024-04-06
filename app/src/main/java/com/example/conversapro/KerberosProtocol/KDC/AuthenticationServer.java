package com.example.conversapro.KerberosProtocol.KDC;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;

import java.util.HashMap;

public class AuthenticationServer {
    private HashMap<String, String> userPasswords; // 存储用户名和密码
    private AESEncryption aes;

    public AuthenticationServer(AESEncryption aes) {
        this.aes = aes;
        this.userPasswords = new HashMap<>();
        // 假设添加了一些用户
        this.userPasswords.put("alice", "password123");
        this.userPasswords.put("bob", "password321");
    }

    public String authenticate(String username, String password) {
        // 检查用户密码是否匹配
        if (userPasswords.containsKey(username) && userPasswords.get(username).equals(password)) {
            // 如果匹配，创建一个 TGT
            String sessionKey = "SessionKeyFor" + username; // 会话密钥通常是随机生成的
            TicketGrantingTicket tgt = new TicketGrantingTicket(username, sessionKey);
            return tgt.encrypt(aes);
        }
        return null;
    }
}
