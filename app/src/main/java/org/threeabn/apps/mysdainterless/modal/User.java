package org.threeabn.apps.mysdainterless.modal;

/**
 * Created by k-joseph on 12/10/2017.
 */

public class User extends Person {
    private String category;

    private String username;

    private String passHash;

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
