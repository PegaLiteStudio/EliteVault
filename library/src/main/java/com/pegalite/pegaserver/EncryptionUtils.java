package com.pegalite.pegaserver;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_CIPHER = "AES/CBC/PKCS7Padding";
    private static final String AES_KEY = "WE^2345R(*D&^FT#";
    private static final String AES_IV = "EJBSDU&^{;S465&6";

    public static String encrypt(String input) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CIPHER);
        SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes(), AES_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(AES_IV.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    public static String decrypt(String encryptedInput) throws Exception {
        byte[] encryptedBytes = Base64.decode(encryptedInput, Base64.DEFAULT);

        Cipher cipher = Cipher.getInstance(AES_CIPHER);
        SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes(), AES_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(AES_IV.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
