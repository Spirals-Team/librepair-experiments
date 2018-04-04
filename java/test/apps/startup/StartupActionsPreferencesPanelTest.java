package apps.startup;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Paul Bender Copyright (C) 2017	
 */
public class StartupActionsPreferencesPanelTest {

    @Test
    public void testCTor() {
        StartupActionsPreferencesPanel t = new StartupActionsPreferencesPanel();
        Assert.assertNotNull("exists",t);
    }

    // The minimal setup for log4J
    @Before
    public void setUp() {
        JUnitUtil.setUp();        jmri.util.JUnitUtil.initStartupActionsManager();
    }

    @After
    public void tearDown() {
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(StartupActionsPreferencesPanelTest.class);

}
