package org.linkedgeodesy.org.gazetteerjson.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * JSONObject to store gazetter search results
 *
 * @author Florian Thiery
 */
public class GGeoJSONFeatureCollection extends JSONObject {

    public GGeoJSONFeatureCollection() {
        super();
        super.put("type", "FeatureCollection");
        super.put("features", new JSONArray());
        super.put("metadata", new JSONObject());
    }

    /**
     * set GeoJSON features
     *
     * @param geometry
     */
    public void setFeature(JSONObject feature) {
        JSONArray features = (JSONArray) super.get("features");
        features.add(feature);
        super.remove("features");
        super.put("features", features);
    }

    /**
     * get features
     *
     * @return features json object
     */
    public JSONObject getFeatures() {
        return (JSONObject) super.get("features");
    }

    /**
     * set metadata
     *
     * @param periodid
     * @param chronontology
     * @param when
     * @param names
     */
    public void setMetadata(String type, String upperleftLat, String upperleftLon, String upperrightLat, String upperrightLon,
                            String lowerrightLat, String lowerrightLon, String lowerleftLat, String lowerleftLon, String searchstring) {
        JSONObject metadata = new JSONObject();
        super.remove("metadata");
        metadata.put("gazetteertype", type);
        metadata.put("upperleftLat", upperleftLat);
        metadata.put("upperleftLon", upperleftLon);
        metadata.put("upperrightLat", upperrightLat);
        metadata.put("upperrightLon", upperrightLon);
        metadata.put("lowerrightLat", lowerrightLat);
        metadata.put("lowerrightLon", lowerleftLat);
        metadata.put("lowerrightLon", lowerrightLon);
        metadata.put("lowerleftLat", lowerleftLat);
        metadata.put("lowerleftLon", lowerleftLon);
        metadata.put("searchstring", searchstring);
        super.put("metadata", metadata);
    }

    /**
     * get metadata
     *
     * @return metadata json object
     */
    public JSONObject getMetadata() {
        return (JSONObject) super.get("metadata");
    }

}
