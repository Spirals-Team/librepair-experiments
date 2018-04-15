package org.linkedgeodesy.org.gazetteerjson.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.linkedgeodesy.gazetteerjson.utils.Functions;

/**
 * JSONObject to store single feature information for CGeoJON objects
 *
 * @author Florian Thiery
 */
public class GGeoJSONFeatureObject extends GGeoJSONSingleFeature {

    public GGeoJSONFeatureObject() {
        super();
    }

    /**
     * set GeoJSON single feature properties
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
     * set GeoJSON single feature properties
     *
     * @param levenshtein
     * @param normalizedlevenshtein
     * @param dameraulevenshtein
     * @param jarowinkler
     * @param searchString
     * @param gazetteerString
     */
    public void setPropertiesStringSimilarity(Double levenshtein, Double normalizedlevenshtein, Double dameraulevenshtein, Double jarowinkler, String searchString, String gazetteerString) {
        JSONObject properties = (JSONObject) super.get("properties");
        JSONObject similarity = new JSONObject();
        similarity.put("searchString", searchString);
        similarity.put("gazetteerString", gazetteerString);
        similarity.put("levenshtein", Functions.round(levenshtein, 2));
        similarity.put("normalizedlevenshtein", Functions.round(normalizedlevenshtein, 2));
        similarity.put("dameraulevenshtein", Functions.round(dameraulevenshtein, 2));
        similarity.put("jarowinkler", Functions.round(jarowinkler, 2));
        properties.put("similarity", similarity);
        super.put("properties", properties);
    }

    public void setPropertiesDistanceSimilarity(double lat1, double lon1, double lat2, double lon2) {
        JSONObject properties = (JSONObject) super.get("properties");
        JSONObject similarity = new JSONObject();
        JSONArray bboxcenter = new JSONArray();
        JSONArray point = new JSONArray();
        bboxcenter.add(lon1);
        bboxcenter.add(lat1);
        point.add(lon2);
        point.add(lat2);
        similarity.put("bboxcenter", bboxcenter);
        similarity.put("point", point);
        similarity.put("distance", Functions.round(Functions.getKilometers(lat1, lon1, lat2, lon2), 2));
        properties.put("similarity", similarity);
        super.put("properties", properties);
    }
    
    public Double getDistance() {
        JSONObject properties = (JSONObject) super.get("properties");
        JSONObject similarity = (JSONObject) properties.get("similarity");
        Double distance = (Double) similarity.get("distance");
        return distance;
    }

}
