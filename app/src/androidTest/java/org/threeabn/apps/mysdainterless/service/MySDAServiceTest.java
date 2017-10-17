package org.threeabn.apps.mysdainterless.service;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeabn.apps.mysdainterless.modal.Period;
import org.threeabn.apps.mysdainterless.orm.DBSession;

import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by k-joseph on 13/10/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MySDAServiceTest {
    MySDAService service;

    @Before
    public void init() {
        service = new MySDAService(InstrumentationRegistry.getTargetContext());
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

            Assert.assertNotNull(service.getAllPeriods());
            Assert.assertEquals(0, service.getAllPeriods().size());

            service.savePeriod(new Period(now.getTime(), now.getTime()));

            Assert.assertEquals(1, service.getAllPeriods().size());
            Assert.assertEquals(now.getTime(), ((Period) service.getAllPeriods().get(0)).getStart());
            Assert.assertEquals(now.getTime(), ((Period) service.getAllPeriods().get(0)).getEnd());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
