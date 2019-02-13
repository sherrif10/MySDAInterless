package org.threeabn.apps.mysdainterless.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * NOT YET USED; used property named participants on program
 * Created by k-joseph on 17/10/2017.
 * TODO ormlite @{@link com.j256.ormlite.dao.ForeignCollection} still requires a fake foreign key i hate:
 * https://stackoverflow.com/questions/10287578/create-table-with-foreign-collection-field
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "guest")
//TODO add to  service
public class Guest extends MySDAObject {
    @JsonProperty("person")
    @DatabaseField(columnName = "person", foreign = true, foreignAutoRefresh = true)
    private Person person;

    @JsonProperty("program")
    @DatabaseField(columnName = "program", foreign = true, foreignAutoRefresh = true)
    private  Program program;

    public Guest() {

    }

    public Guest(Person person, Program program) {
        setPerson(person);
        setProgram(program);
    }

    @JsonProperty("person")
    public Person getPerson() {
        return person;
    }

    @JsonProperty("person")
    public void setPerson(Person person) {
        this.person = person;
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
