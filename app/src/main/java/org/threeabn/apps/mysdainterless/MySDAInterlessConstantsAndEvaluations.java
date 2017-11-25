package org.threeabn.apps.mysdainterless;

import android.os.Environment;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by k-joseph on 01/11/2017.
 */

public class MySDAInterlessConstantsAndEvaluations {
    public static String  PROGRAMS_DIRECTORY = getMySDAVideosFolderPath();

    private static String getMySDAVideosFolderPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".mysdainterless";
    }

    /**
     * Supports all video file formats at https://en.wikipedia.org/wiki/Video_file_format by this file creation date
     * @param name
     * @return
     */
    public static boolean checkIfFileNameBelongsToVideoType(String name) {
        if(StringUtils.isNotBlank(name) && name.contains(".") && Arrays.asList(new String[]{".webm", ".mkv", ".flv",
                ".vob", ".ogv", ".ogg", ".drc", "gif", ".gifv", ".mng", ".avi", ".mov", ".qt", ".wmv", ".yuv", ".rm", ".rmvb",
                ".asf", ".amv", ".mp4", ".m4p", ".m4v", ".mpg", ".mp2", ".mpeg", ".mpe", ".mpv", ".m2v", ".svi", ".3gp", ".3g2",
                ".mxf", ".roq", ".nsv", ".f4v", ".f4p", ".f4a", ".f4b"}).contains(name.substring(name.lastIndexOf('.')).toLowerCase())) {
            return true;
        }
        return false;
    }

}
