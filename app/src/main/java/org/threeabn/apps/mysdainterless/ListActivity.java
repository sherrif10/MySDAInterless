package org.threeabn.apps.mysdainterless;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class ListActivity extends MySDAActivity {

    ArrayList<File> fileList = new ArrayList<File>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        LinearLayout view = (LinearLayout) findViewById(R.id.list_programs_view);
        File root;
        //getting SDcard root path
        root = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath());
        getVideoFile(root);

        for (int i = 0; i < fileList.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(fileList.get(i).getName());

            System.out.println(fileList.get(i).getName());

            if (fileList.get(i).isDirectory()) {
                textView.setTextColor(Color.parseColor("#FF0000"));
            }
            view.addView(textView);
        }

    }

    public ArrayList<File> getVideoFile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    fileList.add(listFile[i]);
                    getVideoFile(listFile[i]);

                } else {
                    /*
                    * AVI (Audio Video Interleave), FLV (Flash Video Format), WMV (Windows Media Video), MOV (Apple QuickTime Movie) and MP4 (Moving Pictures Expert Group 4)
                    */
                    if (listFile[i].getName().toLowerCase().endsWith(".mp4") || listFile[i].getName().toLowerCase().endsWith(".avi")
                            || listFile[i].getName().toLowerCase().endsWith(".flv") || listFile[i].getName().toLowerCase().endsWith(".wmv")
                            || listFile[i].getName().toLowerCase().endsWith(".mov")) {
                        fileList.add(listFile[i]);
                    }
                }

            }
        }
        return fileList;
    }
}
