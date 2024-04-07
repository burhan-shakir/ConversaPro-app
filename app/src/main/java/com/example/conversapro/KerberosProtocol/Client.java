package com.example.conversapro.KerberosProtocol;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;
import com.example.conversapro.KerberosProtocol.KDC.AuthenticationServer;
import com.example.conversapro.KerberosProtocol.KDC.ChatServiceServer;
import com.example.conversapro.KerberosProtocol.KDC.TicketGratingServer;

import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

public class Client {
    private final Server server;
    private SecretKeySpec sessionKey = null;
    private SecretKeySpec csKey = null;

    public Client(Server server) {
        this.server = server;
    }

    public void authenticateWithChatService(String userId, String password, Function<Boolean, Void> callback) {
        this.server.authenticationServer.authenticateClient(userId, (asMessage) -> {
            AuthenticationServer.ASMessage.DecodedASMessage decodedASMessage = asMessage.decode(password);
            if (decodedASMessage == null) {
                callback.apply(false);
                return null;
            }
            this.sessionKey = decodedASMessage.sessionKey;
            TicketGratingServer.TGSRequest tgsRequest = TicketGratingServer.TGSRequest.encode(userId, decodedASMessage.sessionKey, decodedASMessage.tgt, "chat");
            server.ticketGratingServer.authenticateClientService(tgsRequest, (tgsResponse) -> {
                if (tgsResponse == null) {
                    callback.apply(false);
                    return null;
                }
                TicketGratingServer.TGSResponse.DecodedTGSResponse decodedTGSResponse = tgsResponse.decode(decodedASMessage.sessionKey);
                this.csKey = decodedTGSResponse.csKey;
                ChatServiceServer.SSRequest ssRequest = ChatServiceServer.SSRequest.encode(userId, decodedTGSResponse.csKey, decodedTGSResponse.messageE);
                String response = server.chatServiceServer.authenticate(ssRequest);
                try {
                    AESEncryption.decrypt(response, decodedTGSResponse.csKey);
                    callback.apply(true);
                } catch (Exception e) {
                    callback.apply(false);
                }
                return null;
            });
            return null;
        });
    }
}
