package org.threeabn.apps.mysdainterless.settings;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.threeabn.apps.mysdainterless.utils.SettingsUtils;

import java.util.Date;
import java.util.List;

public class Settings {
    private String version;

    private Date releaseDate;

    private NextRun nextRun;

    private List<Status> statuses;

    public Settings(String version, Date releaseDate,NextRun nextRun, List<Status> statuses) {
        this.version = version;
        this.releaseDate = releaseDate;
        this.nextRun = nextRun;
        this.statuses = statuses;
    }

    public String getVersion() {
        return version;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public NextRun getNextRun() {
        return nextRun;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        try {
            return SettingsUtils.toJSONString(this).equals(SettingsUtils.toJSONString((Settings) o));
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
