package com.example.conversapro.KerberosProtocol;

import static org.junit.Assert.assertEquals;

import com.example.conversapro.KerberosProtocol.KDC.database.MockDatabase;

import org.junit.Test;

public class MainForTestProtocol {
    @Test
    public void authenticationSuccessful() {
        Server server = new Server(new MockDatabase());
        Client client = new Client(server);
        client.authenticateWithChatService(null, "xuhuixin2003@gmail.com", "123456", (success) -> {
            assertEquals(true, success);
            return null;
        });
    }

    @Test
    public void authenticationFailed() {
        Server server = new Server(new MockDatabase());
        Client client = new Client(server);
        client.authenticateWithChatService(null, "xuhuixin2003@gmail.com", "1234567", (success) -> {
            assertEquals(false, success);
            return null;
        });
    }
}
