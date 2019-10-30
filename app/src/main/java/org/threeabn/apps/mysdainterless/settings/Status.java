package org.threeabn.apps.mysdainterless.settings;

import org.threeabn.apps.mysdainterless.modal.ProgramCategory;

import java.util.Date;

public class Status {
    private ProgramCategory category;
    private Integer count;

    public Status(ProgramCategory category, Integer count) {
        this.category = category;
        this.count = count;
    }

    public Status(){}

    public ProgramCategory getCategory() {
        return category;
    }

    public Integer getCount() {
        return count;
    }

}