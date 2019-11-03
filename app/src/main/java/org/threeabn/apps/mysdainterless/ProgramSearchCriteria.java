package org.threeabn.apps.mysdainterless;

public class ProgramSearchCriteria {
    private Object term;

    private TermCategory category;

    private Boolean favourited;

    public ProgramSearchCriteria(Object term, TermCategory category, Boolean favourited) {
        this.term = term;
        this.category = category;
        this.favourited = favourited;
    }

    public enum TermCategory {
        CATEGORY, FAVOURITE, CATEGORY_FAVOURITE, SEARCH
    }

    public Object getTerm() {
        return term;
    }

    public TermCategory getCategory() {
        return category;
    }

    public Boolean getFavourited() {
        return favourited;
    }
}
