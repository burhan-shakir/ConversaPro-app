package com.example.conversapro.KerberosProtocol.KDC;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import com.example.conversapro.KerberosProtocol.Encryption.AESEncryption;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
public class BackUpKDC {



        private Map<String, String> registeredAgents = new HashMap<>();
        private String currentEncryptedKey;

        public BackUpKDC() {

            generateAndEncryptNewKey();

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    generateAndEncryptNewKey();
                    notifyAgents();
                }
            }, 0, 10000);
        }


        private void generateAndEncryptNewKey() {
            try {

                AESEncryption.changeDefaultKey();

                currentEncryptedKey = AESEncryption.encrypt("New Key");
                System.out.println("Generated and encrypted new key: " + currentEncryptedKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        private void notifyAgents() {
            for (String agentId : registeredAgents.keySet()) {
                System.out.println("Notifying agent " + agentId + " with new encrypted key: " + currentEncryptedKey);
            }
        }

        // 通信代理注册到KDC
        public void registerAgent(String agentId) {
            registeredAgents.put(agentId, currentEncryptedKey);
            System.out.println("Agent " + agentId + " registered.");

            System.out.println("Providing agent " + agentId + " with initial encrypted key: " + currentEncryptedKey);
        }

        public static void main(String[] args) {
            BackUpKDC kdc = new BackUpKDC();

            kdc.registerAgent("agent1");
            kdc.registerAgent("agent2");
        }


}
