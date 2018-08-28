package jmri.jmrix.acela.configurexml;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * AcelaLightManagerXmlTest.java
 *
 * Description: tests for the AcelaLightManagerXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class AcelaLightManagerXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("AcelaLightManagerXml constructor",new AcelaLightManagerXml());
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

}

