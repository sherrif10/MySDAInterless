package org.threeabn.apps.mysdainterless.api;

import junit.framework.Assert;

import org.junit.Test;
import org.threeabn.apps.mysdainterless.MySDAInterlessConstantsAndEvaluations;

/**
 * Created by k-joseph on 01/11/2017.
 */

public class MySDAInterlessMiscTest {

    @Test
    public void test_checkIfFileNameBelongsToVideoType() {
        Assert.assertTrue(MySDAInterlessConstantsAndEvaluations.checkIfFileNameBelongsToVideoType("LittleRichard.mp4"));
        Assert.assertTrue(MySDAInterlessConstantsAndEvaluations.checkIfFileNameBelongsToVideoType("LittleRichard.flv"));
        Assert.assertTrue(MySDAInterlessConstantsAndEvaluations.checkIfFileNameBelongsToVideoType("LittleRichard.wmv"));
        Assert.assertFalse(MySDAInterlessConstantsAndEvaluations.checkIfFileNameBelongsToVideoType("test.png"));
    }

}
