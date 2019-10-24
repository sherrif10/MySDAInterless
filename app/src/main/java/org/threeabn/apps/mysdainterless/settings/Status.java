package org.threeabn.apps.mysdainterless.settings;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.ProgramCategory;

import java.util.Date;

public class Status {
    private ProgramCategory category;
    private Integer count;
    private OrderBy orderBy;
    private Date installationDate;
    private PlayBack playBack;

    public Status(ProgramCategory category, Integer count, OrderBy orderBy, Date installationDate, PlayBack playBack) {
        this.category = category;
        this.count = count;
        this.orderBy = orderBy;
        this.installationDate = installationDate;
        this.playBack = playBack;
    }

    public Status(){}

    public ProgramCategory getCategory() {
        return category;
    }

    public Integer getCount() {
        return count;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public PlayBack getPlayBack() {
        return playBack;
    }
}