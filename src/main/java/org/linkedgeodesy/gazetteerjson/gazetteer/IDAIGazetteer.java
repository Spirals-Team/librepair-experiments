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
 * functions to query the iDAI gazetteer
 *
 * @author Florian Thiery
 */
public class IDAIGazetteer {

    public static GGeoJSONSingleFeature getPlaceById(String id) throws IOException, ParseException {
        GGeoJSONSingleFeature json = new GGeoJSONSingleFeature();
        String uri = "https://gazetteer.dainst.org/place/" + id;
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Accept", "application/vnd.geo+json");
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("IDAIGazetteer.getPlaceById() - " + responseCode + " - " + url);
        if (responseCode < 400) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
            JSONObject properties = (JSONObject) jsonObject.get("properties");
            NamesJSONObject names = new NamesJSONObject();
            // set prefName
            JSONObject prefName = (JSONObject) properties.get("prefName");
            String titlePN = (String) prefName.get("title");
            String langPN = (String) prefName.get("language");
            names.addPrefName(langPN, titlePN);
            // set alter names
            JSONArray dainames = (JSONArray) properties.get("names");
            if (dainames != null) {
                for (Object item : dainames) {
                    JSONObject tmp = (JSONObject) item;
                    String titleTmp = (String) tmp.get("title");
                    String langTmp = (String) tmp.get("language");
                    names.addSingleName(langTmp, titleTmp);
                }
            }
            json.setGeometry((JSONObject) jsonObject.get("geometry"));
            json.setProperties(uri, id, "dai", names);
        }
        return json;
    }

    public static GGeoJSONFeatureCollection getPlacesByBBox(String upperleftLat, String upperleftLon, String upperrightLat, String upperrightLon,
                                                            String lowerrightLat, String lowerrightLon, String lowerleftLat, String lowerleftLon) throws IOException, ParseException {
        GGeoJSONFeatureCollection json = new GGeoJSONFeatureCollection();
        String searchurl = "https://gazetteer.dainst.org/search.json";
        String urlParameters = "?";
        urlParameters += "polygonFilterCoordinates=" + upperleftLon + "&polygonFilterCoordinates=" + upperleftLat + "&polygonFilterCoordinates=" + upperrightLon + "&polygonFilterCoordinates=" + upperrightLat;
        urlParameters += "&polygonFilterCoordinates=" + lowerrightLon + "&polygonFilterCoordinates=" + lowerrightLat + "&polygonFilterCoordinates=" + lowerleftLon + "&polygonFilterCoordinates=" + lowerleftLat;
        urlParameters += "&q=*";
        urlParameters += "&fq=types:populated-place";
        searchurl += urlParameters;
        URL url = new URL(searchurl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("IDAIGazetteer.getPlacesByBBox() - " + responseCode + " - " + url);
        if (responseCode < 400) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // create G-GeoJSON
            JSONObject resultObject = (JSONObject) new JSONParser().parse(response.toString());
            JSONArray result = (JSONArray) resultObject.get("result");
            for (Object item : result) {
                JSONObject tmp = (JSONObject) item;
                NamesJSONObject names = new NamesJSONObject();
                // set prefName
                JSONObject prefName = (JSONObject) tmp.get("prefName");
                String titlePN = (String) prefName.get("title");
                String langPN = (String) prefName.get("language");
                names.addPrefName(langPN, titlePN);
                // get names
                JSONArray dainames = (JSONArray) tmp.get("names");
                if (dainames != null) {
                    for (Object item2 : dainames) {
                        JSONObject tmp2 = (JSONObject) item2;
                        String langTmp = (String) tmp2.get("language");
                        String titleTmp = (String) tmp2.get("title");
                        names.addSingleName(langTmp, titleTmp);
                    }
                }
                // get geometry
                JSONObject prefLocation = (JSONObject) tmp.get("prefLocation");
                GGeoJSONFeatureObject feature = new GGeoJSONFeatureObject();
                if (prefLocation != null) {
                    JSONArray coordinatesPrefLocation = (JSONArray) prefLocation.get("coordinates");
                    Double lon = (Double) coordinatesPrefLocation.get(0);
                    Double lat = (Double) coordinatesPrefLocation.get(1);
                    JSONObject geometryObject = new JSONObject();
                    geometryObject.put("type", "Point");
                    geometryObject.put("coordinates", coordinatesPrefLocation);
                    feature.setGeometry(geometryObject);
                    feature.setProperties((String) tmp.get("@id"), (String) tmp.get("gazId"), "dai", names);
                    json.setFeature(feature);
                    // get distance
                    JSONArray bbox = Functions.bboxCenter(Double.parseDouble(lowerrightLon), Double.parseDouble(upperleftLon), Double.parseDouble(upperleftLat), Double.parseDouble(upperrightLat));
                    Double bboxlon = (Double) bbox.get(1);
                    Double bboxlat = (Double) bbox.get(0);
                    feature.setPropertiesDistanceSimilarity(bboxlon, bboxlat, lat, lon);
                }
                json.setMetadata("dai", upperleftLat, upperleftLon, upperrightLat, upperrightLon, lowerrightLat, lowerrightLon, lowerleftLat, lowerleftLon, null);
            }
        }
        return json;
    }

    public static GGeoJSONFeatureCollection getPlacesByString(String searchString) throws IOException, ParseException {
        GGeoJSONFeatureCollection json = new GGeoJSONFeatureCollection();
        String searchurl = "https://gazetteer.dainst.org/search.json";
        String urlParameters = "?";
        urlParameters += "q=" + searchString + "&fq=types:populated-place";
        searchurl += urlParameters;
        URL url = new URL(searchurl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("IDAIGazetteer.getPlacesByString() - " + responseCode + " - " + url);
        if (responseCode < 400) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // create G-GeoJSON
            JSONObject resultObject = (JSONObject) new JSONParser().parse(response.toString());
            JSONArray result = (JSONArray) resultObject.get("result");
            for (Object item : result) {
                JSONObject tmp = (JSONObject) item;
                GGeoJSONFeatureObject feature = new GGeoJSONFeatureObject();
                // get names
                NamesJSONObject names = new NamesJSONObject();
                // set prefName
                JSONObject prefName = (JSONObject) tmp.get("prefName");
                String titlePN = (String) prefName.get("title");
                String langPN = (String) prefName.get("language");
                names.addPrefName(langPN, titlePN);
                // get names
                JSONArray dainames = (JSONArray) tmp.get("names");
                if (dainames != null) {
                    for (Object item2 : dainames) {
                        JSONObject tmp2 = (JSONObject) item2;
                        String langTmp = (String) tmp2.get("language");
                        String titleTmp = (String) tmp2.get("title");
                        names.addSingleName(langTmp, titleTmp);
                    }
                }
                // get geometry
                JSONObject prefLocation = (JSONObject) tmp.get("prefLocation");
                if (prefLocation != null) {
                    JSONArray coordinatesPrefLocation = (JSONArray) prefLocation.get("coordinates");
                    JSONObject geometryObject = new JSONObject();
                    geometryObject.put("type", "Point");
                    geometryObject.put("coordinates", coordinatesPrefLocation);
                    feature.setGeometry(geometryObject);
                    feature.setProperties((String) tmp.get("@id"), (String) tmp.get("gazId"), "dai", names);
                    json.setFeature(feature);
                }
                // get prefName
                double levenshtein = StringSimilarity.Levenshtein(searchString, (String) prefName.get("title"));
                double normalizedlevenshtein = StringSimilarity.NormalizedLevenshtein(searchString, (String) prefName.get("title"));
                double dameraulevenshtein = StringSimilarity.Damerau(searchString, (String) prefName.get("title"));
                double jarowinkler = StringSimilarity.JaroWinkler(searchString, (String) prefName.get("title"));
                feature.setPropertiesStringSimilarity(levenshtein, normalizedlevenshtein, dameraulevenshtein, jarowinkler, searchString, (String) prefName.get("title"));
                // set metadata
                json.setMetadata("dai", null, null, null, null, null, null, null, null, searchString);
            }
        }
        return json;
    }

}
