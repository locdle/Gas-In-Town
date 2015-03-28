package com.locdle.gasintown;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by locle on 3/28/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, emulateSdk = 18)
public class MapsActivityTest {
    @Test
    public void testSomething() throws Exception{
        Assert.assertTrue(true);
    }
}
