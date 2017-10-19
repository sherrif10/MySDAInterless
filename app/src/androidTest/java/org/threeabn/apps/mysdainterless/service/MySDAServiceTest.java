package org.threeabn.apps.mysdainterless.service;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeabn.apps.mysdainterless.modal.Period;
import org.threeabn.apps.mysdainterless.modal.Person;
import org.threeabn.apps.mysdainterless.modal.User;

import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by k-joseph on 13/10/2017.
 */
//TODO include test data in xml format
@RunWith(AndroidJUnit4.class)
public class MySDAServiceTest {
    MySDAService service;

    @Before
    public void init() {
        service = new MySDAService(InstrumentationRegistry.getTargetContext());
        service.emptyDatabase();
    }

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Assert.assertEquals("org.threeabn.apps.mysdainterless", appContext.getPackageName());
    }

    //TODO is the db state preserved on the device
    @Test
    public void test_dbSetup() {
        try {
            Calendar now = Calendar.getInstance();
            Period p = new Period(now.getTime(), now.getTime());

            Assert.assertNotNull(service.getAllPeriods());
            Assert.assertEquals(0, service.getAllPeriods().size());

            service.savePeriod(p);

            Assert.assertEquals(1, service.getAllPeriods().size());
            Assert.assertEquals(now.getTime(), ((Period) service.getAllPeriods().get(0)).getStart());
            Assert.assertEquals(now.getTime(), ((Period) service.getAllPeriods().get(0)).getEnd());
            Assert.assertEquals(p, service.getPeriodById(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_login() {
        try {
            Person p = new Person("Joseph", "Kaweesi", Person.PersonCategory.GUEST);

            service.savePerson(p);

            User kjose = new User("k-joseph", "test123", User.UserCategory.VIEWER, p);

            Assert.assertFalse(service.checkIfLoggedIn());

            service.saveUser(kjose);

            Assert.assertNotNull(service.getPersonByUuid(p.getUuid()));

            User authenticatedUser = service.authenticateUser("k-joseph", "test123");

            Assert.assertNull(service.authenticateUser("k-joseph", "test"));
            Assert.assertNotNull(authenticatedUser);
            Assert.assertEquals(kjose.getUsername(), authenticatedUser.getUsername());
            Assert.assertEquals(kjose.getPassword(), authenticatedUser.getPassword());
            Assert.assertEquals(kjose.getUuid(), authenticatedUser.getUuid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_jackson_convertObjectToJsonString_and_convertJsonStringToObject() {
        Person p = new Person("Joseph", "Kaweesi", Person.PersonCategory.GUEST);
        String s = service.convertObjectToJsonString(p);

        Assert.assertTrue(s.contains("\"firstName\":\"Joseph\""));
        Assert.assertTrue(s.contains("\"lastName\":\"Kaweesi\""));
        Assert.assertEquals(((Person) service.convertJsonStringToObject(s, Person.class)).getUuid(), p.getUuid());
    }
}
