package org.linkedgeodesy.gazetteerjson.config;

import org.linkedgeodesy.gazetteerjson.config.GazetteerJSONProperties;
import org.linkedgeodesy.gazetteerjson.config.POM_gazetteerjson;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testing Class
 * @author thiery
 */
public class POM_gazetteerjsonTest {
    
    public POM_gazetteerjsonTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testPOMInfoNotNull() throws Exception {
        System.out.println("testPOMInfoNotNull");
        JSONObject info = POM_gazetteerjson.getInfo();
        assertNotNull(info);
    }
    
    @Test
    public void testLoadPomInfoAndPackagingIsJAR() throws Exception {
        System.out.println("testLoadPomInfoAndPackagingIsJAR");
        String packaging = GazetteerJSONProperties.getPropertyParam("packaging");
        assertEquals("jar",packaging);
    }
    
}
