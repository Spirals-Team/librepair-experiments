package org.linkedgeodesy.gazetteerjson.gazetteer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.linkedgeodesy.gazetteerjson.utils.Functions;
import org.linkedgeodesy.gazetteerjson.utils.StringSimilarity;
import org.linkedgeodesy.org.gazetteerjson.json.GGeoJSONFeatureCollection;
import org.linkedgeodesy.org.gazetteerjson.json.GGeoJSONFeatureObject;
import org.linkedgeodesy.org.gazetteerjson.json.GGeoJSONSingleFeature;
import org.linkedgeodesy.org.gazetteerjson.json.NamesJSONObject;

/**
 * functions to query Pleiades Places
 *
 * @author Florian Thiery
 */
public class Pleiades {

    public static GGeoJSONSingleFeature getPlaceById(String id) throws IOException, ParseException {
        GGeoJSONSingleFeature json = new GGeoJSONSingleFeature();
        String uri = "http://peripleo.pelagios.org/peripleo/places/http:%2F%2Fpleiades.stoa.org%2Fplaces%2F" + id;
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Pleiades.getPlaceById() - " + responseCode + " - " + url);
        if (responseCode < 400) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
            // get geom
            JSONObject geo_bounds = (JSONObject) jsonObject.get("geo_bounds");
            JSONObject geometry = new JSONObject();
            String min_lon = String.valueOf((Double) geo_bounds.get("min_lon"));
            String max_lon = String.valueOf((Double) geo_bounds.get("max_lon"));
            String min_lat = String.valueOf((Double) geo_bounds.get("min_lat"));
            String max_lat = String.valueOf((Double) geo_bounds.get("max_lat"));
            // choose if point of bbox
            if (min_lon.equals(max_lon) && min_lat.equals(max_lat)) {
                JSONArray point = new JSONArray();
                point.add((Double) geo_bounds.get("min_lon"));
                point.add((Double) geo_bounds.get("min_lat"));
                geometry.put("type", "Point");
                geometry.put("coordinates", point);
            } else {
                JSONArray polygonOut = new JSONArray();
                JSONArray polygon = new JSONArray();
                JSONArray point1 = new JSONArray();
                point1.add((Double) geo_bounds.get("min_lon"));
                point1.add((Double) geo_bounds.get("max_lat"));
                JSONArray point2 = new JSONArray();
                point2.add((Double) geo_bounds.get("max_lon"));
                point2.add((Double) geo_bounds.get("max_lat"));
                JSONArray point3 = new JSONArray();
                point3.add((Double) geo_bounds.get("max_lon"));
                point3.add((Double) geo_bounds.get("min_lat"));
                JSONArray point4 = new JSONArray();
                point4.add((Double) geo_bounds.get("min_lon"));
                point4.add((Double) geo_bounds.get("min_lat"));
                polygon.add(point1);
                polygon.add(point2);
                polygon.add(point3);
                polygon.add(point4);
                polygon.add(point1);
                polygonOut.add(polygon);
                geometry.put("type", "Polygon");
                geometry.put("coordinates", polygonOut);
            }
            // get and add prefName
            NamesJSONObject names = new NamesJSONObject();
            String prefName = (String) jsonObject.get("title");
            names.addPrefName("unknown", prefName);
            // get and add alternative names
            JSONArray namesArray = (JSONArray) jsonObject.get("names");
            for (Object item : namesArray) {
                String tmp = (String) item;
                names.addSingleName("unknown", tmp);
            }
            json.setGeometry(geometry);
            json.setProperties("https://pleiades.stoa.org/places/" + id, id, "pleiades", names);
        }
        return json;
    }

    public static GGeoJSONFeatureCollection getPlacesByBBox(String upperleftLat, String upperleftLon, String upperrightLat, String upperrightLon,
                                                            String lowerrightLat, String lowerrightLon, String lowerleftLat, String lowerleftLon) throws IOException, ParseException {
        GGeoJSONFeatureCollection json = new GGeoJSONFeatureCollection();
        String uri = "http://peripleo.pelagios.org/peripleo/search?bbox=" + upperleftLon + "," + lowerleftLon + "," + upperrightLat + "," + upperleftLat + "&types=place&limit=10000";
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Pleiades.getPlacesByBBox() - " + responseCode + " - " + url);
        if (responseCode < 400) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
            JSONArray items = (JSONArray) jsonObject.get("items");
            for (Object item : items) {
                JSONObject tmp = (JSONObject) item;
                String identifier = (String) tmp.get("identifier");
                String[] identifierSplit = identifier.split("/");
                if (identifier.contains("pleiades.stoa")) {
                    GGeoJSONFeatureObject feature = new GGeoJSONFeatureObject();
                    // get and add prefName
                    NamesJSONObject names = new NamesJSONObject();
                    String prefName = (String) tmp.get("title");
                    names.addPrefName("unknown", prefName);
                    // get and add alternative names
                    JSONArray namesArray = (JSONArray) tmp.get("names");
                    if (namesArray != null) {
                        for (Object item2 : namesArray) {
                            String tmp2 = (String) item2;
                            names.addSingleName("unknown", tmp2);
                        }
                    }
                    // get geom
                    JSONObject geo_bounds = (JSONObject) tmp.get("geo_bounds");
                    JSONObject geometry = new JSONObject();
                    String min_lon = String.valueOf((Double) geo_bounds.get("min_lon"));
                    String max_lon = String.valueOf((Double) geo_bounds.get("max_lon"));
                    String min_lat = String.valueOf((Double) geo_bounds.get("min_lat"));
                    String max_lat = String.valueOf((Double) geo_bounds.get("max_lat"));
                    // choose if point of bbox
                    boolean pointBool = false;
                    if (min_lon.equals(max_lon) && min_lat.equals(max_lat)) {
                        JSONArray point = new JSONArray();
                        point.add((Double) geo_bounds.get("min_lon"));
                        point.add((Double) geo_bounds.get("min_lat"));
                        geometry.put("type", "Point");
                        geometry.put("coordinates", point);
                        pointBool = true;
                    } else {
                        JSONArray polygonOut = new JSONArray();
                        JSONArray polygon = new JSONArray();
                        JSONArray point1 = new JSONArray();
                        point1.add((Double) geo_bounds.get("min_lon"));
                        point1.add((Double) geo_bounds.get("max_lat"));
                        JSONArray point2 = new JSONArray();
                        point2.add((Double) geo_bounds.get("max_lon"));
                        point2.add((Double) geo_bounds.get("max_lat"));
                        JSONArray point3 = new JSONArray();
                        point3.add((Double) geo_bounds.get("max_lon"));
                        point3.add((Double) geo_bounds.get("min_lat"));
                        JSONArray point4 = new JSONArray();
                        point4.add((Double) geo_bounds.get("min_lon"));
                        point4.add((Double) geo_bounds.get("min_lat"));
                        polygon.add(point1);
                        polygon.add(point2);
                        polygon.add(point3);
                        polygon.add(point4);
                        polygon.add(point1);
                        polygonOut.add(polygon);
                        geometry.put("type", "Polygon");
                        geometry.put("coordinates", polygonOut);
                    }
                    feature.setGeometry(geometry);
                    feature.setProperties(identifier, identifierSplit[identifierSplit.length - 1], "pleiades", names);
                    // get distance
                    JSONArray bbox = Functions.bboxCenter(Double.parseDouble(lowerrightLon), Double.parseDouble(upperleftLon), Double.parseDouble(upperleftLat), Double.parseDouble(upperrightLat));
                    Double bboxlon = (Double) bbox.get(1);
                    Double bboxlat = (Double) bbox.get(0);
                    feature.setPropertiesDistanceSimilarity(bboxlon, bboxlat, (Double) geo_bounds.get("min_lat"), (Double) geo_bounds.get("min_lon"));
                    // set feature
                    if (pointBool || feature.getDistance() < 50.0) {
                        json.setFeature(feature);
                    }
                }
            }
            json.setMetadata("pleiades", upperleftLat, upperleftLon, upperrightLat, upperrightLon, lowerrightLat, lowerrightLon, lowerleftLat, lowerleftLon, null);
        }
        return json;
    }

    public static GGeoJSONFeatureCollection getPlacesByString(String searchString) throws IOException, ParseException {
        GGeoJSONFeatureCollection json = new GGeoJSONFeatureCollection();
        String uri = "http://peripleo.pelagios.org/peripleo/search?query=" + searchString + "&types=place&limit=250";
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Pleiades.getPlacesByString() - " + responseCode + " - " + url);
        if (responseCode < 400) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
            JSONArray items = (JSONArray) jsonObject.get("items");
            for (Object item : items) {
                JSONObject tmp = (JSONObject) item;
                String identifier = (String) tmp.get("identifier");
                String[] identifierSplit = identifier.split("/");
                if (identifier.contains("pleiades.stoa")) {
                    GGeoJSONFeatureObject feature = new GGeoJSONFeatureObject();
                    // get and add prefName
                    NamesJSONObject names = new NamesJSONObject();
                    String prefName = (String) tmp.get("title");
                    names.addPrefName("unknown", prefName);
                    // get and add alternative names
                    JSONArray namesArray = (JSONArray) tmp.get("names");
                    if (namesArray != null) {
                        for (Object item2 : namesArray) {
                            String tmp2 = (String) item2;
                            names.addSingleName("unknown", tmp2);
                        }
                    }
                    // get geom
                    JSONObject geo_bounds = (JSONObject) tmp.get("geo_bounds");
                    JSONObject geometry = new JSONObject();
                    String min_lon = String.valueOf((Double) geo_bounds.get("min_lon"));
                    String max_lon = String.valueOf((Double) geo_bounds.get("max_lon"));
                    String min_lat = String.valueOf((Double) geo_bounds.get("min_lat"));
                    String max_lat = String.valueOf((Double) geo_bounds.get("max_lat"));
                    // choose if point of bbox
                    if (min_lon.equals(max_lon) && min_lat.equals(max_lat)) {
                        JSONArray point = new JSONArray();
                        point.add((Double) geo_bounds.get("min_lon"));
                        point.add((Double) geo_bounds.get("min_lat"));
                        geometry.put("type", "Point");
                        geometry.put("coordinates", point);
                    } else {
                        JSONArray polygonOut = new JSONArray();
                        JSONArray polygon = new JSONArray();
                        JSONArray point1 = new JSONArray();
                        point1.add((Double) geo_bounds.get("min_lon"));
                        point1.add((Double) geo_bounds.get("max_lat"));
                        JSONArray point2 = new JSONArray();
                        point2.add((Double) geo_bounds.get("max_lon"));
                        point2.add((Double) geo_bounds.get("max_lat"));
                        JSONArray point3 = new JSONArray();
                        point3.add((Double) geo_bounds.get("max_lon"));
                        point3.add((Double) geo_bounds.get("min_lat"));
                        JSONArray point4 = new JSONArray();
                        point4.add((Double) geo_bounds.get("min_lon"));
                        point4.add((Double) geo_bounds.get("min_lat"));
                        polygon.add(point1);
                        polygon.add(point2);
                        polygon.add(point3);
                        polygon.add(point4);
                        polygon.add(point1);
                        polygonOut.add(polygon);
                        geometry.put("type", "Polygon");
                        geometry.put("coordinates", polygonOut);
                    }
                    feature.setGeometry(geometry);
                    feature.setProperties(identifier, identifierSplit[identifierSplit.length - 1], "pleiades", names);
                    // string similarity
                    double levenshtein = StringSimilarity.Levenshtein(searchString, prefName);
                    double normalizedlevenshtein = StringSimilarity.NormalizedLevenshtein(searchString, prefName);
                    double dameraulevenshtein = StringSimilarity.Damerau(searchString, prefName);
                    double jarowinkler = StringSimilarity.JaroWinkler(searchString, prefName);
                    feature.setPropertiesStringSimilarity(levenshtein, normalizedlevenshtein, dameraulevenshtein, jarowinkler, searchString, prefName);
                    // set feature
                    json.setFeature(feature);
                }
            }
            json.setMetadata("pleiades", null, null, null, null, null, null, null, null, searchString);
        }
        return json;
    }

}
