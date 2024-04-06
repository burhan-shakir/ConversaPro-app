package com.example.conversapro.KerberosProtocol.Encryption;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.KeyGenerator;
import java.security.SecureRandom;
import java.util.Base64;

public class AESEncryption {
    private static final String AES = "AES";
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static SecretKeySpec simukey;

    static {
        try {
            simukey = generateKey(128);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void changeDefaultKey() throws Exception {
        simukey = generateKey(128);
    }

    // Generating Keys
    public static SecretKeySpec generateKey(int n) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(n, SecureRandom.getInstanceStrong());
        byte[] keyBytes = keyGenerator.generateKey().getEncoded();
        return new SecretKeySpec(keyBytes, AES);
    }

    // encrypted
    public static String encrypt(String data, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        byte[] ivBytes = new byte[cipher.getBlockSize()];
        new SecureRandom().nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        byte[] encryptedIVAndText = new byte[ivBytes.length + encryptedBytes.length];
        System.arraycopy(ivBytes, 0, encryptedIVAndText, 0, ivBytes.length);
        System.arraycopy(encryptedBytes, 0, encryptedIVAndText, ivBytes.length, encryptedBytes.length);
        return Base64.getEncoder().encodeToString(encryptedIVAndText);
    }
    public static String encrypt(String data) {
        try {
            return encrypt(data,simukey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // declassification
    public static String decrypt(String encryptedData, SecretKeySpec key) throws Exception {
        byte[] encryptedIVAndTextBytes = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        byte[] ivBytes = new byte[cipher.getBlockSize()];
        System.arraycopy(encryptedIVAndTextBytes, 0, ivBytes, 0, ivBytes.length);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        int encryptedSize = encryptedIVAndTextBytes.length - ivBytes.length;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedIVAndTextBytes, ivBytes.length, encryptedBytes, 0, encryptedSize);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    public static String decrypt(String encryptedData) {
        try {
            return decrypt(encryptedData,simukey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // example
    public static void main(String[] args) throws Exception {
        // Generating Keys
        SecretKeySpec key = generateKey(128);

        System.out.println(key.getEncoded());
        // encrypted
        String data = "Hello, World!";
        String encryptedData = encrypt(data, key);
        System.out.println("Encrypted data: " + encryptedData);

        // declassification
        String decryptedData = decrypt(encryptedData, key);
        System.out.println("Decrypted data: " + decryptedData);
    }
}
