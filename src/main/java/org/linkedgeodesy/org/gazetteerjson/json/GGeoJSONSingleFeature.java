package org.linkedgeodesy.org.gazetteerjson.json;

import org.json.simple.JSONObject;

/**
 * JSONObject to store gazetter search results
 *
 * @author Florian Thiery
 */
public class GGeoJSONSingleFeature extends JSONObject {

    public GGeoJSONSingleFeature() {
        super();
        super.put("type", "Feature");
        super.put("geometry", new JSONObject());
        super.put("properties", new JSONObject());
    }

    /**
     * set GeoJSON geometry
     *
     * @param geometry
     */
    public void setGeometry(JSONObject geometry) {
        super.remove("geometry");
        super.put("geometry", geometry);
    }

    /**
     * get geometry
     *
     * @return geometry json object
     */
    public JSONObject getGeometry() {
        return (JSONObject) super.get("geometry");
    }

    /**
     * set GeoJSON properties
     *
     * @param url
     * @param gazetteerid
     * @param gazetteertype
     * @param names
     */
    public void setProperties(String url, String gazetteerid, String gazetteertype, NamesJSONObject names) {
        JSONObject properties = new JSONObject();
        super.remove("properties");
        properties.put("@id", url);
        properties.put("gazetteerid", gazetteerid);
        properties.put("gazetteertype", gazetteertype);
        properties.put("names", names);
        super.put("properties", properties);
    }

    /**
     * get properties
     *
     * @return properties json object
     */
    public JSONObject getProperties() {
        return (JSONObject) super.get("properties");
    }

}
