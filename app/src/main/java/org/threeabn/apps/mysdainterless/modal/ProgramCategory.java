package org.threeabn.apps.mysdainterless.modal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum ProgramCategory {
    ALL("Categories: All"),
    NONE("None"),
    PREACHING_AND_TEACHING("Preaching & Teaching"),
    HEALTH_AND_COOKING("Health & Cooking"),
    MUSIC("Music"),
    FAMILY_ISSUES_AND_INTERCITY("Family issues & Intercity"),
    KIDS("Kids"),
    INTERCITY_D2D("Intercity D2D");// TODO vs FAMILY_ISSUES_AND_INTERCITY

    private String displayName;

    ProgramCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static List<String> getNames(List<ProgramCategory> categories) {
        List<String> names = new ArrayList<String>();

        for (ProgramCategory c : (categories != null ? categories : Arrays.asList(ProgramCategory.values()))) {
            names.add(c.name());
        }
        return names;
    }

    public static List<String> displayNames() {
        List<String> names = new ArrayList<>();
        for (ProgramCategory category : values()) {
            names.add(category.getDisplayName());
        }
        return names;
    }

    public static ProgramCategory valueOfFromDisplayName(String displayName) {
        for (ProgramCategory category : values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        return null;
    }
}
