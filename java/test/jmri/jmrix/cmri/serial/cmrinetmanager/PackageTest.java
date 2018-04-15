package jmri.jmrix.cmri.serial.cmrinetmanager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    CMRInetManagerActionTest.class,
    CMRInetManagerFrameTest.class,
    CMRInetMetricsActionTest.class,
    CMRInetMetricsFrameTest.class,
    BundleTest.class
})

/**
 * Tests for the jmri.jmrix.cmri.serial.cmrinetmanager package
 *
 * @author  Chuck Catania	Copyright (C) 2017, 2018
 */
public class PackageTest{

    // Main entry point
    static public void main(String[] args) {
        org.junit.runner.Result result = org.junit.runner.JUnitCore
                 .runClasses(PackageTest.class);
        for(org.junit.runner.notification.Failure fail: result.getFailures()) {
            log.error(fail.toString());
        }
        //junit.textui.TestRunner.main(testCaseName);
        if (result.wasSuccessful()) {
            log.info("Success");
        }
    }

    private final static Logger log = LoggerFactory.getLogger(PackageTest.class);

}
