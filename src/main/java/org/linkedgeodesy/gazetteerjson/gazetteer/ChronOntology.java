package org.linkedgeodesy.gazetteerjson.gazetteer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jdom.JDOMException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.linkedgeodesy.org.gazetteerjson.json.CGeoJSONFeatureCollection;
import org.linkedgeodesy.org.gazetteerjson.json.CGeoJSONFeatureObject;
import org.linkedgeodesy.org.gazetteerjson.json.GGeoJSONSingleFeature;

/**
 * functions to query the ChronOntology gazetteer
 *
 * @author Florian Thiery
 */
public class ChronOntology {

    public static final String[] TYPES = {"spatiallyPartOfRegion", "isNamedAfter", "hasCoreArea"};

    public static CGeoJSONFeatureCollection getPlacesById(String id) throws IOException, ParseException, JDOMException {
        CGeoJSONFeatureCollection json = new CGeoJSONFeatureCollection();
        // init output
        // get data from chronontology
        String url = "http://chronontology.dainst.org/data/period/" + id;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        int responseCode = con.getResponseCode();
        System.out.println("ChronOntology.getPlacesById() - " + responseCode + " - " + url);
        if (con.getResponseCode() < 400) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // parse data
            JSONObject data = (JSONObject) new JSONParser().parse(response.toString());
            JSONObject resource = (JSONObject) data.get("resource");
            JSONArray spatiallyPartOfRegion = (JSONArray) resource.get("spatiallyPartOfRegion");
            JSONArray hasCoreArea = (JSONArray) resource.get("hasCoreArea");
            JSONArray isNamedAfter = (JSONArray) resource.get("isNamedAfter");
            JSONArray coverage = new JSONArray();
            if (spatiallyPartOfRegion != null) {
                for (Object tmp : spatiallyPartOfRegion) {
                    String item = (String) tmp;
                    coverage.add(item);
                }
            }
            if (hasCoreArea != null) {
                for (Object tmp : hasCoreArea) {
                    String item = (String) tmp;
                    coverage.add(item);
                }
            }
            if (isNamedAfter != null) {
                for (Object tmp : isNamedAfter) {
                    String item = (String) tmp;
                    coverage.add(item);
                }
            }
            for (String item : TYPES) {
                JSONArray spatial = (JSONArray) resource.get(item);
                if (spatial != null) {
                    JSONArray spatialURIs = new JSONArray();
                    for (Object element : spatial) {
                        String tmp = (String) element;
                        if (tmp.contains("http://gazetteer.dainst.org")) { // set https in dai gazetteer
                            tmp = tmp.replace("http://", "https://");
                            spatialURIs.add(tmp);
                        } else {
                            spatialURIs.add(tmp);
                        }
                    }
                    for (Object element : spatialURIs) {
                        String tmp = (String) element;
                        if (tmp.contains("gazetteer.dainst.org")) {
                            String[] thisID = tmp.split("/");
                            GGeoJSONSingleFeature feature = IDAIGazetteer.getPlaceById(thisID[thisID.length - 1]);
                            CGeoJSONFeatureObject featureObj = new CGeoJSONFeatureObject();
                            featureObj.setGeometry(feature.getGeometry());
                            featureObj.setProperties(feature.getProperties());
                            featureObj.setProperty("gazetteerrelation", item);
                            json.setFeature(featureObj);
                        } else if (tmp.contains("sws.geonames.org")) {
                            String[] thisID = tmp.split("/");
                            GGeoJSONSingleFeature feature = GeoNames.getPlaceById(thisID[thisID.length - 1]);
                            CGeoJSONFeatureObject featureObj = new CGeoJSONFeatureObject();
                            featureObj.setGeometry(feature.getGeometry());
                            featureObj.setProperties(feature.getProperties());
                            featureObj.setProperty("gazetteerrelation", item);
                            json.setFeature(featureObj);
                        } else if (tmp.contains("vocab.getty.edu")) {
                            String[] thisID = tmp.split("/");
                            GGeoJSONSingleFeature feature = GettyTGN.getPlaceById(thisID[thisID.length - 1]);
                            CGeoJSONFeatureObject featureObj = new CGeoJSONFeatureObject();
                            featureObj.setGeometry(feature.getGeometry());
                            featureObj.setProperties(feature.getProperties());
                            featureObj.setProperty("gazetteerrelation", item);
                            json.setFeature(featureObj);
                        } else if (tmp.contains("pleiades.stoa.org")) {
                            String[] thisID = tmp.split("/");
                            GGeoJSONSingleFeature feature = Pleiades.getPlaceById(thisID[thisID.length - 1]);
                            CGeoJSONFeatureObject featureObj = new CGeoJSONFeatureObject();
                            featureObj.setGeometry(feature.getGeometry());
                            featureObj.setProperties(feature.getProperties());
                            featureObj.setProperty("gazetteerrelation", item);
                            json.setFeature(featureObj);
                        }
                    }
                }
            }
            json.setMetadata(url.replace("/data/", "/"), (String) resource.get("id"), data, (JSONArray) resource.get("hasTimespan"), (JSONObject) resource.get("names"), coverage);
        }
        return json;
    }

}
