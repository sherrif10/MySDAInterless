package org.threeabn.apps.mysdainterless;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Arrays;

/**
 * Created by k-joseph on 01/11/2017.
 */

public class MySDAInterlessConstantsAndEvaluations {
    public static String  PROGRAMS_DIRECTORY = getMySDAVideosFolderPath();

    public static String PROGRAMS_CSV_FILENAME = "programs.csv";

    private static String getMySDAVideosFolderPath() {
        File mySdaStorageLocation = new File(getDataDirectory().getAbsolutePath() + File.separator + "programs");
        if(!mySdaStorageLocation.exists()) {
            mySdaStorageLocation.mkdirs();
        }
        return mySdaStorageLocation.getAbsolutePath();
    }

    /*
     * TODO improve hard cording here
     */
    public static File getDataDirectory() {
        //TODO change back to "/sdcard/.mysdainterless"
        return new File("/sdcard/mysdainterless");
    }
    /**
     * Supports all video file formats at https://en.wikipedia.org/wiki/Video_file_format by this file creation date
     * @param name
     * @return
     */
    public static boolean checkIfFileNameBelongsToVideoType(String name) {
        return StringUtils.isNotBlank(name) && name.contains(".") && Arrays.asList(new String[]{".webm", ".mkv", ".flv",
                ".vob", ".ogv", ".ogg", ".drc", "gif", ".gifv", ".mng", ".avi", ".mov", ".qt", ".wmv", ".yuv", ".rm", ".rmvb",
                ".asf", ".amv", ".mp4", ".m4p", ".m4v", ".mpg", ".mp2", ".mpeg", ".mpe", ".mpv", ".m2v", ".svi", ".3gp", ".3g2",
                ".mxf", ".roq", ".nsv", ".f4v", ".f4p", ".f4a", ".f4b"}).contains(name.substring(name.lastIndexOf('.')).toLowerCase());
    }

    public static File getProgramFile(String path) {
        File program = new File(path);
        if(!program.exists()) {
            return null;
        }
        return null;
    }

}
