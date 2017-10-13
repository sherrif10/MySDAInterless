package org.threeabn.apps.mysdainterless.modal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.threeabn.apps.mysdainterless.service.PassHashing;

/**
 * TODO add a basic default user called guest
 * Created by k-joseph on 12/10/2017.
 * TODO perhaps could extend Person
 */
@DatabaseTable(tableName = "user")
public class User extends MySDAObject {
    @DatabaseField(columnName = "category", canBeNull = false)
    private String category;

    @DatabaseField(columnName = "username", canBeNull = false)
    private String username;

    @DatabaseField(columnName = "password", canBeNull = true)
    private String password;

    @DatabaseField(columnName = "person", foreign = true, foreignAutoRefresh = true)
    private Person person;

    public String getCategory() {
        return category;
    }

    public void setCategory(UserCategory category) {
        this.category = category.name();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new PassHashing(password).generateHash();
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public enum UserCategory {
        ADMIN, VIEWER
    }
}
