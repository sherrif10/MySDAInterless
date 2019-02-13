package org.threeabn.apps.mysdainterless.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by k-joseph on 20/10/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "favourite")
public class Favourite extends MySDAObject {

    @JsonProperty("user")
    @DatabaseField(columnName = "user", foreign = true, foreignAutoRefresh = true)
    private User user;

    @JsonProperty("program")
    @DatabaseField(columnName = "program", foreign = true, foreignAutoRefresh = true)
    private Program program;

    public Favourite(User user, Program program) {
        setUser(user);
        setProgram(program);
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("program")
    public Program getProgram() {
        return program;
    }

    @JsonProperty("program")
    public void setProgram(Program program) {
        this.program = program;
    }
}
