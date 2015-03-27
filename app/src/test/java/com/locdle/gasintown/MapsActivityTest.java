package com.locdle.gasintown;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * Created by loc on 3/27/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, emulateSdk = 18)

public class MapsActivityTest {

    @Test
    public void testSomething() throws Exception {
        assertTrue(true);
    }
}
