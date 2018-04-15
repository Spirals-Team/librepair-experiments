package org.linkedgeodesy.gazetteerjson.gazetteer;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.linkedgeodesy.org.gazetteerjson.json.CGeoJSONFeatureCollection;

/**
 * ChronOntology Test
 *
 * @author Florian Thiery
 */
public class ChronOntologyTest {

    public ChronOntologyTest() {
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

    /**
     * Test of getPlacesById method, of class ChronOntology.
     */
    @Test
    public void testIDAIGazetteerGetPlaceByIdValidation() throws Exception {
        System.out.println("TEST: testIDAIGazetteerGetPlaceByIdValidation");
        CGeoJSONFeatureCollection fc = ChronOntology.getPlacesById("EfFq8qCFODK8");
        JSONObject metadataObj = (JSONObject) fc.get("metadata");
        String id = (String) metadataObj.get("@id");
        String periodid = (String) metadataObj.get("periodid");
        JSONObject chronontology = (JSONObject) metadataObj.get("chronontology");
        assertEquals(id,"http://chronontology.dainst.org/period/EfFq8qCFODK8");
        assertEquals(periodid,"EfFq8qCFODK8");
        assertNotSame(chronontology,new JSONObject());
    }

}
