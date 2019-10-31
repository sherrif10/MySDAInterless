package org.threeabn.apps.mysdainterless;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by k-joseph on 01/11/2017.
 */

public class MySDAInterlessConstantsAndEvaluations {

    /**
     * Supports all video file formats at https://en.wikipedia.org/wiki/Video_file_format by this file creation date
     *
     * @param name
     * @return
     */
    public static boolean checkIfFileNameBelongsToVideoType(String name) {
        return StringUtils.isNotBlank(name) && name.contains(".") && Arrays.asList(new String[]{".webm", ".mkv", ".flv",
                ".vob", ".ogv", ".ogg", ".drc", "gif", ".gifv", ".mng", ".avi", ".mov", ".qt", ".wmv", ".yuv", ".rm", ".rmvb",
                ".asf", ".amv", ".mp4", ".m4p", ".m4v", ".mpg", ".mp2", ".mpeg", ".mpe", ".mpv", ".m2v", ".svi", ".3gp", ".3g2",
                ".mxf", ".roq", ".nsv", ".f4v", ".f4p", ".f4a", ".f4b"}).contains(name.substring(name.lastIndexOf('.')).toLowerCase());
    }

}
