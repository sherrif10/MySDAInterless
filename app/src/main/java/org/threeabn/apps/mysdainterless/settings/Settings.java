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

    private Repeat repeat;

    private OrderBy orderBy;

    private Date installationDate;

    private Integer previewSeconds;

    public Settings(String version, Date releaseDate, NextRun nextRun, List<Status> statuses, Repeat repeat, OrderBy orderBy, Date installationDate, Integer previewSeconds) {
        this.version = version;
        this.releaseDate = releaseDate;
        this.nextRun = nextRun;
        this.statuses = statuses;
        this.repeat = repeat;
        this.orderBy = orderBy;
        this.installationDate = installationDate;
        this.previewSeconds = previewSeconds;
    }

    public Settings() {}

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

    public Repeat getRepeat() {
        return repeat;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public Integer getPreviewSeconds() {
        return previewSeconds;
    }

    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
    }

    public void setNextRun(NextRun nextRun) {
        this.nextRun = nextRun;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public void setPreviewSeconds(Integer previewSeconds) {
        this.previewSeconds = previewSeconds;
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
