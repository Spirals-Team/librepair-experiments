package org.linkedgeodesy.gazetteerjson.gazetteer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.linkedgeodesy.org.gazetteerjson.json.GGeoJSONFeatureCollection;
import org.linkedgeodesy.org.gazetteerjson.json.GGeoJSONSingleFeature;

/**
 * GettyTGN Test
 *
 * @author Florian Thiery
 */
public class GettyTGNTest {

    public GettyTGNTest() {
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
     * Test of getPlaceById method, of class GettyTGN.
     */
    @Test
    public void testGettyTGNGetPlaceByIdValidation() throws Exception {
        System.out.println("TEST: testGettyTGNGetPlaceByIdValidation");
        GGeoJSONSingleFeature feature = GettyTGN.getPlaceById("7008038");
        String type = (String) feature.get("type");
        JSONObject geometryObj = (JSONObject) feature.get("geometry");
        JSONObject propertiesObj = (JSONObject) feature.get("properties");
        String id = (String) propertiesObj.get("@id");
        String gazetteerid = (String) propertiesObj.get("gazetteerid");
        String gazetteertype = (String) propertiesObj.get("gazetteertype");
        JSONObject names = (JSONObject) propertiesObj.get("names");
        // tests
        assertEquals(type,"Feature");
        assertNotSame(geometryObj, new JSONObject());
        assertEquals(id,"http://vocab.getty.edu/tgn/7008038");
        assertEquals(gazetteerid,"7008038");
        assertEquals(gazetteertype,"getty");
        assertNotSame(names,new JSONObject());
    }
    
    /**
     * Test of getPlacesByBBox method, of class GettyTGN.
     */
    @Test
    public void testGettyTGNGetPlacesByBBoxValidation() throws Exception {
        System.out.println("TEST: testGettyTGNGetPlacesByBBoxValidation");
        GGeoJSONFeatureCollection fc = GettyTGN.getPlacesByBBox("50.082665", "8.161050", "49.903887", "8.161050", "49.903887", "8.371850", "50.082665", "8.371850");
        String type = (String) fc.get("type");
        JSONArray featuresArray = (JSONArray) fc.get("features");
        JSONObject metadataObj = (JSONObject) fc.get("metadata");
        JSONObject feature0 = (JSONObject) featuresArray.get(0);
        JSONObject feature0prop = (JSONObject) feature0.get("properties");
        String id = (String) feature0prop.get("@id");
        String gazetteerid = (String) feature0prop.get("gazetteerid");
        String gazetteertype = (String) feature0prop.get("gazetteertype");
        JSONObject names = (JSONObject) feature0prop.get("names");
        JSONObject similarity = (JSONObject) feature0prop.get("similarity");
        // tests
        assertEquals(type,"FeatureCollection");
        assertNotSame(featuresArray, new JSONArray());
        assertNotSame(metadataObj, new JSONObject());
        assertNotSame(feature0, new JSONObject());
        assertNotNull(id);
        assertNotNull(gazetteerid);
        assertEquals(gazetteertype,"getty");
        assertNotSame(names, new JSONObject());
        assertNotSame(similarity, new JSONObject());
    }
    
    /**
     * Test of getPlacesByString method, of class GettyTGN.
     */
    @Test
    public void testGettyTGNGetPlacesByStringValidation() throws Exception {
        System.out.println("TEST: testGettyTGNGetPlacesByStringValidation");
        GGeoJSONFeatureCollection fc = GettyTGN.getPlacesByString("Mainz");
        String type = (String) fc.get("type");
        JSONArray featuresArray = (JSONArray) fc.get("features");
        JSONObject metadataObj = (JSONObject) fc.get("metadata");
        JSONObject feature0 = (JSONObject) featuresArray.get(0);
        JSONObject feature0prop = (JSONObject) feature0.get("properties");
        String id = (String) feature0prop.get("@id");
        String gazetteerid = (String) feature0prop.get("gazetteerid");
        String gazetteertype = (String) feature0prop.get("gazetteertype");
        JSONObject names = (JSONObject) feature0prop.get("names");
        JSONObject similarity = (JSONObject) feature0prop.get("similarity");
        // tests
        assertEquals(type,"FeatureCollection");
        assertNotSame(featuresArray, new JSONArray());
        assertNotSame(metadataObj, new JSONObject());
        assertNotSame(feature0, new JSONObject());
        assertNotNull(id);
        assertNotNull(gazetteerid);
        assertEquals(gazetteertype,"getty");
        assertNotSame(names, new JSONObject());
        assertNotSame(similarity, new JSONObject());
    }

}
