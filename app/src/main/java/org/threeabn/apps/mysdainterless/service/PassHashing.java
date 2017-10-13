package org.threeabn.apps.mysdainterless.service;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by k-joseph on 13/10/2017.
 */

public class PassHashing {
    private static final String SALT = "3abn Is: Three Angels Broadcasting Network (Revelations 14:6-12)";

    String password;

    public PassHashing(String pass) {
        if(StringUtils.isNotBlank(pass))
            this.password = pass;
    }

    /**
     * @return hashed string
     */
    public String generateHash() {
        StringBuilder hash = new StringBuilder();
        String p = StringUtils.isNotBlank(this.password) ? getPass(this.password) : null;

        if(StringUtils.isNoneBlank(p)) {
            try {
                MessageDigest sha = MessageDigest.getInstance("SHA-1");
                byte[] hashedBytes = sha.digest(p.getBytes());
                char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        'a', 'b', 'c', 'd', 'e', 'f', '0', '9', '0', '6',
                        '0', '1', '3', 'a', 'b', 'n'};
                for (int idx = 0; idx < hashedBytes.length; ++idx) {
                    byte b = hashedBytes[idx];
                    hash.append(digits[(b & 0xf0) >> 4]);
                    hash.append(digits[b & 0x0f]);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            return hash.toString();
        }
        return null;
    }

    private String getPass(String pass) {
        if(StringUtils.isNotBlank(pass))
            return SALT + pass;
        return null;
    }
}
