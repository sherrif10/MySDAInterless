package org.threeabn.apps.mysdainterless.security;

import java.io.File;

/**
 * Envisioned to handle the encryption and decryption logic when the program/video files are stored on the dongo file sytstem
 * Created by k-joseph on 12/10/2017.
 */

public class CryptoLauncher {

    public static void encrypt(File inputFile, String key, File encryptedFile) {
        try {
            CryptoUtils.encrypt(key, inputFile, encryptedFile);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
    }

    public static void dencrypt(File encryptedFile, String key, File dencryptedFile) {
        try {
            CryptoUtils.decrypt(key, encryptedFile, dencryptedFile);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
    }
}
