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
 * IDAIGazetteer Test
 *
 * @author Florian Thiery
 */
public class IDAIGazetteerTest {

    public IDAIGazetteerTest() {
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
     * Test of getPlaceById method, of class IDAIGazetteer.
     */
    @Test
    public void testIDAIGazetteerGetPlaceByIdValidation() throws Exception {
        System.out.println("TEST: testIDAIGazetteerGetPlaceByIdValidation");
        GGeoJSONSingleFeature feature = IDAIGazetteer.getPlaceById("2181124");
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
        assertEquals(id,"https://gazetteer.dainst.org/place/2181124");
        assertEquals(gazetteerid,"2181124");
        assertEquals(gazetteertype,"dai");
        assertNotSame(names,new JSONObject());
    }
    
    /**
     * Test of getPlacesByBBox method, of class IDAIGazetteer.
     */
    @Test
    public void testIDAIGazetteerGetPlacesByBBoxValidation() throws Exception {
        System.out.println("TEST: testIDAIGazetteerGetPlacesByBBoxValidation");
        GGeoJSONFeatureCollection fc = IDAIGazetteer.getPlacesByBBox("50.082665", "8.161050", "49.903887", "8.161050", "49.903887", "8.371850", "50.082665", "8.371850");
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
        assertEquals(gazetteertype,"dai");
        assertNotSame(names, new JSONObject());
        assertNotSame(similarity, new JSONObject());
    }
    
    /**
     * Test of getPlacesByString method, of class IDAIGazetteer.
     */
    @Test
    public void testIDAIGazetteerGetPlacesByStringValidation() throws Exception {
        System.out.println("TEST: testIDAIGazetteerGetPlacesByStringValidation");
        GGeoJSONFeatureCollection fc = IDAIGazetteer.getPlacesByString("Mainz");
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
        assertEquals(gazetteertype,"dai");
        assertNotSame(names, new JSONObject());
        assertNotSame(similarity, new JSONObject());
    }

}
