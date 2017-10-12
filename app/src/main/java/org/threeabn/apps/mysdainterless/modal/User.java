package org.threeabn.apps.mysdainterless.modal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * TODO add a basic default user called guest
 * Created by k-joseph on 12/10/2017.
 */

@DatabaseTable(tableName = "user")
public class User extends Person {
    @DatabaseField(columnName = "category", canBeNull = false)
    private String category;

    @DatabaseField(columnName = "username", canBeNull = false)
    private String username;

    @DatabaseField(columnName = "pass_hash", canBeNull = false)
    private String passHash;

    /*TODO not sure with ormlite
    @DatabaseField(columnName = "person", foreign = true, foreignAutoRefresh = true)
    private Person person;
  */
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

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public enum UserCategory {
        ADMIN, VIEWER
    }
}
