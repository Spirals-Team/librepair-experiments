package org.linkedgeodesy.org.gazetteerjson.json;

import org.json.simple.JSONObject;

/**
 * JSONObject to store single feature information for CGeoJON objects
 *
 * @author Florian Thiery
 */
public class CGeoJSONFeatureObject extends GGeoJSONSingleFeature {

    public CGeoJSONFeatureObject() {
        super();
    }

    /**
     * set GeoJSON single feature properties
     *
     * @param url
     * @param gazetteerid
     * @param gazetteertype
     * @param names
     * @param gazetteerrelation
     */
    public void setProperties(String url, String gazetteerid, String gazetteertype, String gazetteerrelation) {
        JSONObject properties = new JSONObject();
        super.remove("properties");
        properties.put("@id", url);
        properties.put("gazetteerid", gazetteerid);
        properties.put("gazetteertype", gazetteertype);
        properties.put("gazetteerrelation", gazetteerrelation);
        super.put("properties", properties);
    }
    
    /**
     * set GeoJSON single feature properties
     * @param properties 
     */
    public void setProperties(JSONObject properties) {
        super.remove("properties");
        super.put("properties", properties);
    }
    
    /**
     * set GeoJSON single feature properties 
     * @param key
     * @param value 
     */
    public void setProperty(String key, String value) {
        JSONObject obj = super.getProperties();
        obj.put(key, value);
        super.put("properties", obj);
    }

}
