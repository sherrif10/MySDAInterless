package org.threeabn.apps.mysdainterless.modal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by k-joseph on 17/10/2017.
 * TODO ormlite @{@link com.j256.ormlite.dao.ForeignCollection} still requires a fake foreign key i hate:
 * https://stackoverflow.com/questions/10287578/create-table-with-foreign-collection-field
 */

@DatabaseTable(tableName = "guest")
//TODO add to  service
public class Guest extends MySDAObject {
    @DatabaseField(columnName = "person", foreign = true, foreignAutoRefresh = true)
    private Person person;
    @DatabaseField(columnName = "program", foreign = true, foreignAutoRefresh = true)
    private  Program program;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
