package org.threeabn.apps.mysdainterless;

import android.text.TextUtils;

import org.threeabn.apps.mysdainterless.modal.Program;

public class MySDAInterlessUtils {
    /**
     * Program
     * @param p
     * @param searchText
     * @return
     */
    public static boolean programsMatcher(Program p, String searchText) {
        return TextUtils.isEmpty(searchText) || (!TextUtils.isEmpty(searchText) &&
                ((!TextUtils.isEmpty(p.getName()) && p.getName().toLowerCase().contains(searchText.toLowerCase()))
                        || (!TextUtils.isEmpty(p.getCategory().name()) && p.getCategory().name().toLowerCase().contains(searchText.toLowerCase()))
                        || (!TextUtils.isEmpty(p.getSeries()) && p.getSeries().toLowerCase().contains(searchText.toLowerCase()))
                        || (!TextUtils.isEmpty(p.getEpisode()) && p.getEpisode().toLowerCase().contains(searchText.toLowerCase()))
                        || (!TextUtils.isEmpty(p.getCode()) && p.getCode().toLowerCase().contains(searchText.toLowerCase()))
                        || (!TextUtils.isEmpty(p.getDescription()) && p.getDescription().toLowerCase().contains(searchText.toLowerCase()))
                        || (!TextUtils.isEmpty(p.getParticipants()) && p.getParticipants().toLowerCase().contains(searchText.toLowerCase()))
                        || (!TextUtils.isEmpty(p.getDuration()) && p.getDuration().toLowerCase().contains(searchText.toLowerCase()))));
    }
}
