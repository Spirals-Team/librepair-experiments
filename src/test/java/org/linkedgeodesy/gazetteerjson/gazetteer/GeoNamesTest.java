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
 * GeoNames Test
 *
 * @author Florian Thiery
 */
public class GeoNamesTest {

    public GeoNamesTest() {
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
     * Test of getPlaceById method, of class GeoNames.
     */
    @Test
    public void testGeoNamesGetPlaceByIdValidation() throws Exception {
        System.out.println("TEST: testGeoNamesGetPlaceByIdValidation");
        GGeoJSONSingleFeature feature = GeoNames.getPlaceById("2874225");
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
        assertEquals(id,"http://sws.geonames.org/2874225");
        assertEquals(gazetteerid,"2874225");
        assertEquals(gazetteertype,"geonames");
        assertNotSame(names,new JSONObject());
    }
    
    /**
     * Test of getPlacesByBBox method, of class GeoNames.
     */
    @Test
    public void testGeoNamesGetPlacesByBBoxValidation() throws Exception {
        System.out.println("TEST: testGeoNamesGetPlacesByBBoxValidation");
        GGeoJSONFeatureCollection fc = GeoNames.getPlacesByBBox("50.082665", "8.161050", "49.903887", "8.161050", "49.903887", "8.371850", "50.082665", "8.371850");
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
        assertEquals(gazetteertype,"geonames");
        assertNotSame(names, new JSONObject());
        assertNotSame(similarity, new JSONObject());
    }
    
    /**
     * Test of getPlacesByString method, of class GeoNames.
     */
    @Test
    public void testGeoNamesGetPlacesByStringValidation() throws Exception {
        System.out.println("TEST: testGeoNamesGetPlacesByStringValidation");
        GGeoJSONFeatureCollection fc = GeoNames.getPlacesByString("Mainz");
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
        assertEquals(gazetteertype,"geonames");
        assertNotSame(names, new JSONObject());
        assertNotSame(similarity, new JSONObject());
    }

}
