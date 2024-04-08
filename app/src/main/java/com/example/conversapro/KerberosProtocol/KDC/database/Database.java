package com.example.conversapro.KerberosProtocol.KDC.database;

import java.util.function.Function;

public interface Database {
    // return null if user not found
    String retrieveUserPassword(String userId, Function<String, Void> callback);
}
