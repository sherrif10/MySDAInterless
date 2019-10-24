package org.threeabn.apps.mysdainterless.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class FilesUtils {

    public static void write(String file, String content) throws IOException {
        OutputStream os = new FileOutputStream(new File(file));
        os.write(content.getBytes(), 0, content.length());
        os.close();
    }

    public static String read(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = br.readLine();
        }
        br.close();
        return sb.toString();
    }
}

