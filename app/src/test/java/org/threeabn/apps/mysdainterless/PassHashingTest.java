package org.threeabn.apps.mysdainterless;

/**
 * Created by k-joseph on 13/10/2017.
 */

//@RunWith(AndroidJUnit4.class)
public class PassHashingTest {
/*TODO fix test gradle setup
    Map<String, String> DB = new HashMap<String, String>();

    private void signup(String username, String password) {
        DB.put(username, new PassHashing(password).generateHash());
    }

    private boolean login(String username, String password) {
        Boolean isAuthenticated = false;

        String storedPasswordHash = DB.get(username);
        String hashedPassword = new PassHashing(password).generateHash();

        if(hashedPassword.equals(storedPasswordHash)){
            isAuthenticated = true;
        } else{
            isAuthenticated = false;
        }
        return isAuthenticated;
    }

    @Test
    public void testShaPassword_hashing() {
        signup("user1", "3abn_2017");
        signup("user2", "Password1");
        signup("user3", "Password1");

        Assert.assertTrue(login("user1", "3abn_2017"));
        Assert.assertTrue(login("user2", "Password1"));
        Assert.assertTrue(login("user3", "Password1"));

        Assert.assertFalse(login("user1", "Password1"));
        Assert.assertFalse(login("user2", "3abn_2017"));
        Assert.assertFalse(login("user3", "3abn_2017"));
    }*/
}
