package com.example.conversapro.KerberosProtocol.KDC.database;

import com.example.conversapro.KerberosProtocol.KDC.database.Database;

import java.util.HashMap;
import java.util.function.Function;

public class MockDatabase implements Database {
    private final HashMap<String, String> userPasswords = new HashMap<>();

    public MockDatabase() {
        this.userPasswords.put("yuk42@mcmaster.ca", "123456");
        this.userPasswords.put("xuhuixin2003@gmail.com", "123456");
        this.userPasswords.put("b@gmail.com", "123456");
        this.userPasswords.put("loux8@mcmaster.ca", "123456");
        this.userPasswords.put("hatoumg@mcmaster.ca", "123456");
    }

    public String retrieveUserPassword(String userId, Function<String, Void> callback) {
        String password = this.userPasswords.get(userId);
        callback.apply(password);
        return password;
    }
}
