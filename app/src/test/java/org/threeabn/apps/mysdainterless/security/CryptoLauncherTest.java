package org.threeabn.apps.mysdainterless.security;


import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

public class CryptoLauncherTest {

    File file = new File("/Users/k-joseph/Desktop/invoice#43-feb19.pdf");
    @Test
    @Ignore
    public void testEncryptAndDecrypt() {
        CryptoLauncher.encrypt(file);
        CryptoLauncher.dencrypt(file);
    }
}
