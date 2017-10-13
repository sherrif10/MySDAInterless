package org.threeabn.apps.mysdainterless.service;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    @Ignore//TODO fix
    public void test_dbSetup() {
        service.createDBSchemas();
    }
}
