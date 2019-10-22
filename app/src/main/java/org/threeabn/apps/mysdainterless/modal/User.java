package org.threeabn.apps.mysdainterless.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.threeabn.apps.mysdainterless.security.PassHashing;

/**
 * TODO add a basic default user called guest
 * Created by k-joseph on 12/10/2017.
 * TODO perhaps could extend Person
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "user")
public class User extends MySDAObject {

    @JsonProperty("category")
    @DatabaseField(columnName = "category", canBeNull = false)
    private String category;

    @JsonProperty("username")
    @DatabaseField(columnName = "username", canBeNull = false, unique = true)
    private String username;

    @JsonProperty("password")
    @DatabaseField(columnName = "password", canBeNull = false)
    private String password;

    @JsonProperty("person")
    @DatabaseField(columnName = "person", foreign = true, foreignAutoRefresh = true)
    private Person person;

    public User() {
    }

    public User(String username, String password, UserCategory category, Person person) {
        setUsername(username);
        setPassword(password);
        setCategory(category);
        setPerson(person);
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(UserCategory category) {
        this.category = category.name();
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = new PassHashing(password).generateHash();
    }

    @JsonProperty("person")
    public Person getPerson() {
        return person;
    }

    @JsonProperty("person")
    public void setPerson(Person person) {
        this.person = person;
    }

    public enum UserCategory {
        ADMIN, VIEWER
    }
}
