package org.linkedgeodesy.gazetteerjson.gazetteer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
 * functions to query Getty Thesaurus of Geographic Names
 *
 * @author Florian Thiery
 */
public class GettyTGN {

    public static GGeoJSONSingleFeature getPlaceById(String id) throws IOException, ParseException {
        GGeoJSONSingleFeature json = new GGeoJSONSingleFeature();
        String url = "http://vocab.getty.edu/sparql.json";
        String queryString = "prefix ontogeo: <http://www.ontotext.com/owlim/geo#> "
                + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> "
                + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
                + "PREFIX tgn: <http://vocab.getty.edu/tgn/> "
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                + "PREFIX gvp: <http://vocab.getty.edu/ontology#> "
                + "PREFIX skosxl: <http://www.w3.org/2008/05/skos-xl#> "
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/> "
                + "SELECT DISTINCT ?place ?prefLabel ?altLabel ?lat ?long WHERE { "
                + "?place skos:inScheme tgn: . "
                + "?place dc:identifier \"" + id + "\" . "
                + "?place xl:prefLabel [skosxl:literalForm ?prefLabel] . "
                + "OPTIONAL { ?place xl:altLabel [skosxl:literalForm ?altLabel] }. "
                + "?place foaf:focus ?p. "
                + "?p geo:lat ?lat . "
                + "?p geo:long ?long . "
                + "}";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        String urlParameters = "query=" + queryString;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("GettyTGN.getPlaceById() - " + responseCode + " - " + url);
        if (responseCode < 400) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
            JSONObject results = (JSONObject) jsonObject.get("results");
            JSONArray bindings = (JSONArray) results.get("bindings");
            JSONObject binding0 = (JSONObject) bindings.get(0);
            // get URI
            JSONObject place = (JSONObject) binding0.get("place");
            String placeURI = (String) place.get("value");
            // add and get geometry
            JSONObject lat = (JSONObject) binding0.get("lat");
            JSONObject lng = (JSONObject) binding0.get("long");
            JSONObject geometry = new JSONObject();
            JSONArray point = new JSONArray();
            point.add(Double.parseDouble((String) lng.get("value")));
            point.add(Double.parseDouble((String) lat.get("value")));
            geometry.put("type", "Point");
            geometry.put("coordinates", point);
            // add and get names
            NamesJSONObject names = new NamesJSONObject();
            // get prefLabel
            JSONObject prefLabelObj0 = (JSONObject) binding0.get("prefLabel");
            String prefLabelString0 = (String) prefLabelObj0.get("value");
            String prefLabelLang0 = (String) prefLabelObj0.get("xml:lang");
            names.addPrefName(prefLabelLang0, prefLabelString0);
            for (Object item : bindings) {
                JSONObject binding = (JSONObject) item;
                JSONObject prefLabelObj = (JSONObject) binding.get("prefLabel");
                String prefLabelString = (String) prefLabelObj.get("value");
                String prefLabelLang = (String) prefLabelObj.get("xml:lang");
                names.addSingleName(prefLabelLang, prefLabelString);
            }
            // get altLabels
            for (Object item : bindings) {
                JSONObject binding = (JSONObject) item;
                JSONObject altLabelObj = (JSONObject) binding.get("altLabel");
                String altLabelString = (String) altLabelObj.get("value");
                String altLabelLang = (String) altLabelObj.get("xml:lang");
                names.addSingleName(altLabelLang, altLabelString);
            }
            json.setGeometry((geometry));
            json.setProperties(placeURI, id, "getty", names);
        }
        return json;
    }

    public static GGeoJSONFeatureCollection getPlacesByBBox(String upperleftLat, String upperleftLon, String upperrightLat, String upperrightLon,
                                                            String lowerrightLat, String lowerrightLon, String lowerleftLat, String lowerleftLon) throws IOException, ParseException {
        GGeoJSONFeatureCollection json = new GGeoJSONFeatureCollection();
        String url = "http://vocab.getty.edu/sparql.json";
        String queryString = "prefix ontogeo: <http://www.ontotext.com/owlim/geo#> "
                + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> "
                + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
                + "PREFIX tgn: <http://vocab.getty.edu/tgn/> "
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                + "PREFIX gvp: <http://vocab.getty.edu/ontology#> "
                + "PREFIX skosxl: <http://www.w3.org/2008/05/skos-xl#> "
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/> "
                + "SELECT DISTINCT ?place ?prefLabel ?altLabel ?lat ?long ?id WHERE { "
                + "?place skos:inScheme tgn: . "
                + "?place dc:identifier ?id . "
                + "?place foaf:focus [ontogeo:within(" + lowerleftLat + " " + lowerleftLon + " " + upperrightLat + " " + upperrightLon + ")]. "
                + "?place xl:prefLabel [skosxl:literalForm ?prefLabel] . "
                + "OPTIONAL { ?place xl:altLabel [skosxl:literalForm ?altLabel] }. "
                + "?place foaf:focus ?p. "
                + "?p geo:lat ?lat . "
                + "?p geo:long ?long . "
                + "}";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        String urlParameters = "query=" + queryString;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("GettyTGN.getPlacesByBBox() - " + responseCode + " - " + url);
        if (responseCode < 400) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
            JSONObject results = (JSONObject) jsonObject.get("results");
            JSONArray bindings = (JSONArray) results.get("bindings");
            // write all binding objects with same "place URI" to hashmap
            HashMap<String, JSONArray> hm = new HashMap();
            for (Object item : bindings) {
                JSONObject binding = (JSONObject) item;
                JSONObject place = (JSONObject) binding.get("place");
                String placeValue = (String) place.get("value");
                if (!hm.containsKey(placeValue)) {
                    JSONArray tmpArray = new JSONArray();
                    tmpArray.add(binding);
                    hm.put(placeValue, tmpArray);
                } else {
                    JSONArray tmpArray = hm.get(placeValue);
                    tmpArray.add(binding);
                    hm.remove(placeValue);
                    hm.put(placeValue, tmpArray);
                }
            }
            // loop through hashmap
            for (Map.Entry<String, JSONArray> entry : hm.entrySet()) {
                String key = entry.getKey();
                JSONArray value = (JSONArray) entry.getValue();
                JSONObject val0 = (JSONObject) value.get(0);
                // add and get names
                NamesJSONObject names = new NamesJSONObject();
                // get prefLabel
                JSONObject prefLabelObj0 = (JSONObject) val0.get("prefLabel");
                String prefLabelString0 = (String) prefLabelObj0.get("value");
                String prefLabelLang0 = (String) prefLabelObj0.get("xml:lang");
                names.addPrefName(prefLabelLang0, prefLabelString0);
                // get prefLabels
                for (Object item : value) {
                    JSONObject binding = (JSONObject) item;
                    JSONObject prefLabelObj = (JSONObject) binding.get("prefLabel");
                    String prefLabelString = (String) prefLabelObj.get("value");
                    String prefLabelLang = (String) prefLabelObj.get("xml:lang");
                    names.addSingleName(prefLabelLang, prefLabelString);
                }
                // get altLabels
                for (Object item : value) {
                    JSONObject binding = (JSONObject) item;
                    JSONObject altLabelObj = (JSONObject) binding.get("altLabel");
                    if (altLabelObj != null) {
                        String altLabelString = (String) altLabelObj.get("value");
                        String altLabelLang = (String) altLabelObj.get("xml:lang");
                        names.addSingleName(altLabelLang, altLabelString);
                    }
                }
                // get id
                JSONObject idObj = (JSONObject) val0.get("id");
                String id = (String) idObj.get("value");
                // add and get geometry
                JSONObject lat = (JSONObject) val0.get("lat");
                JSONObject lng = (JSONObject) val0.get("long");
                GGeoJSONFeatureObject feature = new GGeoJSONFeatureObject();
                JSONArray point = new JSONArray();
                point.add(Double.parseDouble((String) lng.get("value")));
                point.add(Double.parseDouble((String) lat.get("value")));
                JSONObject geometry = new JSONObject();
                geometry.put("type", "Point");
                geometry.put("coordinates", point);
                feature.setGeometry(geometry);
                feature.setProperties(key, id, "getty", names);
                json.setFeature(feature);
                // get distance
                JSONArray bbox = Functions.bboxCenter(Double.parseDouble(lowerrightLon), Double.parseDouble(upperleftLon), Double.parseDouble(upperleftLat), Double.parseDouble(upperrightLat));
                Double bboxlon = (Double) bbox.get(1);
                Double bboxlat = (Double) bbox.get(0);
                feature.setPropertiesDistanceSimilarity(bboxlon, bboxlat, Double.parseDouble((String) lat.get("value")), Double.parseDouble((String) lng.get("value")));
            }
            json.setMetadata("getty", upperleftLat, upperleftLon, upperrightLat, upperrightLon, lowerrightLat, lowerrightLon, lowerleftLat, lowerleftLon, null);
        }
        return json;
    }

    public static GGeoJSONFeatureCollection getPlacesByString(String searchString) throws IOException, ParseException {
        GGeoJSONFeatureCollection json = new GGeoJSONFeatureCollection();
        String url = "http://vocab.getty.edu/sparql.json";
        String queryString = "prefix ontogeo: <http://www.ontotext.com/owlim/geo#> "
                + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> "
                + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
                + "PREFIX tgn: <http://vocab.getty.edu/tgn/> "
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                + "PREFIX gvp: <http://vocab.getty.edu/ontology#> "
                + "PREFIX skosxl: <http://www.w3.org/2008/05/skos-xl#> "
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/> "
                + "SELECT DISTINCT ?place ?prefLabel ?altLabel ?lat ?long ?id WHERE { "
                + "?place skos:inScheme tgn: . "
                + "?place dc:identifier ?id . "
                + "?place luc:term '" + searchString + "' . "
                + "?place xl:prefLabel [skosxl:literalForm ?prefLabel] . "
                + "OPTIONAL { ?place xl:altLabel [skosxl:literalForm ?altLabel] }. "
                + "?place foaf:focus ?p. "
                + "?p geo:lat ?lat . "
                + "?p geo:long ?long . "
                + " } ORDER BY ASC(LCASE(STR(?Term)))";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        String urlParameters = "query=" + queryString;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("GettyTGN.getPlacesByString() - " + responseCode + " - " + url);
        if (responseCode < 400) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
            JSONObject results = (JSONObject) jsonObject.get("results");
            JSONArray bindings = (JSONArray) results.get("bindings");
            // write all binding objects with same "place URI" to hashmap
            HashMap<String, JSONArray> hm = new HashMap();
            for (Object item : bindings) {
                JSONObject binding = (JSONObject) item;
                JSONObject place = (JSONObject) binding.get("place");
                String placeValue = (String) place.get("value");
                if (!hm.containsKey(placeValue)) {
                    JSONArray tmpArray = new JSONArray();
                    tmpArray.add(binding);
                    hm.put(placeValue, tmpArray);
                } else {
                    JSONArray tmpArray = hm.get(placeValue);
                    tmpArray.add(binding);
                    hm.remove(placeValue);
                    hm.put(placeValue, tmpArray);
                }
            }
            // loop through hashmap
            for (Map.Entry<String, JSONArray> entry : hm.entrySet()) {
                String key = entry.getKey();
                JSONArray value = (JSONArray) entry.getValue();
                JSONObject val0 = (JSONObject) value.get(0);
                // add and get names
                NamesJSONObject names = new NamesJSONObject();
                // get prefLabel
                JSONObject prefLabelObj0 = (JSONObject) val0.get("prefLabel");
                String prefLabelString0 = (String) prefLabelObj0.get("value");
                String prefLabelLang0 = (String) prefLabelObj0.get("xml:lang");
                names.addPrefName(prefLabelLang0, prefLabelString0);
                // get prefLabels
                for (Object item : value) {
                    JSONObject binding = (JSONObject) item;
                    JSONObject prefLabelObj = (JSONObject) binding.get("prefLabel");
                    String prefLabelString = (String) prefLabelObj.get("value");
                    String prefLabelLang = (String) prefLabelObj.get("xml:lang");
                    names.addSingleName(prefLabelLang, prefLabelString);
                }
                // get altLabels
                for (Object item : value) {
                    JSONObject binding = (JSONObject) item;
                    JSONObject altLabelObj = (JSONObject) binding.get("altLabel");
                    if (altLabelObj != null) {
                        String altLabelString = (String) altLabelObj.get("value");
                        String altLabelLang = (String) altLabelObj.get("xml:lang");
                        names.addSingleName(altLabelLang, altLabelString);
                    }
                }
                // get id
                JSONObject idObj = (JSONObject) val0.get("id");
                String id = (String) idObj.get("value");
                // add and get geometry
                JSONObject lat = (JSONObject) val0.get("lat");
                JSONObject lng = (JSONObject) val0.get("long");
                GGeoJSONFeatureObject feature = new GGeoJSONFeatureObject();
                JSONArray point = new JSONArray();
                point.add(Double.parseDouble((String) lng.get("value")));
                point.add(Double.parseDouble((String) lat.get("value")));
                JSONObject geometry = new JSONObject();
                geometry.put("type", "Point");
                geometry.put("coordinates", point);
                feature.setGeometry(geometry);
                feature.setProperties(key, id, "getty", names);
                json.setFeature(feature);
                // get prefName
                double levenshtein = StringSimilarity.Levenshtein(searchString, prefLabelString0);
                double normalizedlevenshtein = StringSimilarity.NormalizedLevenshtein(searchString, prefLabelString0);
                double dameraulevenshtein = StringSimilarity.Damerau(searchString, prefLabelString0);
                double jarowinkler = StringSimilarity.JaroWinkler(searchString, prefLabelString0);
                feature.setPropertiesStringSimilarity(levenshtein, normalizedlevenshtein, dameraulevenshtein, jarowinkler, searchString, prefLabelString0);
            }
            json.setMetadata("getty", null, null, null, null, null, null, null, null, searchString);
        }
        return json;
    }

}
