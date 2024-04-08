package com.example.conversapro.KerberosProtocol;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;
import com.example.conversapro.KerberosProtocol.KDC.AuthenticationServer;
import com.example.conversapro.KerberosProtocol.KDC.ChatServiceServer;
import com.example.conversapro.KerberosProtocol.KDC.TicketGratingServer;
import com.example.conversapro.KerberosProtocol.KDC.database.Database;
import com.example.conversapro.KerberosProtocol.KDC.database.KDCFirebaseDatabase;

import java.util.Base64;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

public class Client {
    private final Server server;
    private SecretKeySpec sessionKey = null;
    private SecretKeySpec csKey = null;
    private String email = null;

    private static Client INSTANCE;

    private static final String sharedPrefFile = "com.example.conversapro.keys";

    Client(Server server) {
        this.server = server;
    }

    public static Client getInstance() {
        if (INSTANCE == null) {
            Database database = new KDCFirebaseDatabase();
            Server server = new Server(database);
            INSTANCE = new Client(server);
        }

        return INSTANCE;
    }

    public String getUid() {
        return Base64.getEncoder().encodeToString(this.email.getBytes());
    }

    public boolean isLoggedIn() {
        return this.sessionKey != null && this.csKey != null && this.email != null;
    }

    public void readKeys(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String sessionKey = sharedPreferences.getString("sessionKey", null);
        String csKey = sharedPreferences.getString("csKey", null);
        String email = sharedPreferences.getString("email", null);
        if (sessionKey != null && csKey != null && email != null) {
            this.sessionKey = new SecretKeySpec(Base64.getDecoder().decode(sessionKey), "AES");
            this.csKey = new SecretKeySpec(Base64.getDecoder().decode(csKey), "AES");
            this.email = email;
        }
    }

    private void saveKeys(Context context) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sessionKey", Base64.getEncoder().encodeToString(this.sessionKey.getEncoded()));
        editor.putString("csKey", Base64.getEncoder().encodeToString(this.csKey.getEncoded()));
        editor.putString("email", this.email);
        editor.apply();
    }

    public void logout(Context context) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("sessionKey");
        editor.remove("csKey");
        editor.remove("email");
        this.sessionKey = null;
        this.csKey = null;
        this.email = null;
        editor.apply();
    }

    public void authenticateWithChatService(Context context, String email, String password, Function<Boolean, Void> callback) {
        this.server.authenticationServer.authenticateClient(email, (asMessage) -> {
            AuthenticationServer.ASMessage.DecodedASMessage decodedASMessage = asMessage.decode(password);
            if (decodedASMessage == null) {
                callback.apply(false);
                return null;
            }
            this.sessionKey = decodedASMessage.sessionKey;
            TicketGratingServer.TGSRequest tgsRequest = TicketGratingServer.TGSRequest.encode(email, decodedASMessage.sessionKey, decodedASMessage.tgt, "chat");
            server.ticketGratingServer.authenticateClientService(tgsRequest, (tgsResponse) -> {
                if (tgsResponse == null) {
                    callback.apply(false);
                    return null;
                }
                TicketGratingServer.TGSResponse.DecodedTGSResponse decodedTGSResponse = tgsResponse.decode(decodedASMessage.sessionKey);
                this.csKey = decodedTGSResponse.csKey;
                ChatServiceServer.SSRequest ssRequest = ChatServiceServer.SSRequest.encode(email, decodedTGSResponse.csKey, decodedTGSResponse.messageE);
                String response = server.chatServiceServer.authenticate(ssRequest);
                try {
                    AESEncryption.decrypt(response, decodedTGSResponse.csKey);
                    this.email = email;
                    this.saveKeys(context);
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
