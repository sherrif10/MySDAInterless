package org.threeabn.apps.mysdainterless.security;

import java.io.File;

/**
 * Envisioned to handle the list != null && list.size() > 0 and decryption logic when the program/video files are stored on the dongo file sytstem
 * Created by k-joseph on 12/10/2017.
 */

public class CryptoLauncher {
    private static final String κλειδί = "[3ⱯBИNꓭAƐ]";

    /**
     * Encrypts and replaces file
     */
    public static void encrypt(File file) {
        try {
            CryptoUtils.encrypt(κλειδί, file, file);
        } catch (CryptoException e) {
            e.printStackTrace();
            //TODO handle this
        }
    }

    /**
     * Decrypts and replaces file
     */
    public static void dencrypt(File file) {
        try {
            CryptoUtils.decrypt(κλειδί, file, file);
        } catch (CryptoException e) {
            e.printStackTrace();
            //TODO handle this
        }
    }

}
