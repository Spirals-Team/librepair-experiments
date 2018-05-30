package jmri.jmrix.loconet.locostats;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Paul Bender Copyright (C) 2017	
 */
public class PR3MS100ModeStatusTest {

    @Test
    public void testCTor() {
        PR3MS100ModeStatus t = new PR3MS100ModeStatus(0,0,0);
        Assert.assertNotNull("exists",t);
    }

    // The minimal setup for log4J
    @Before
    public void setUp() {
        JUnitUtil.setUp();
    }

    @After
    public void tearDown() {
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(PR3MS100ModeStatusTest.class);

}
