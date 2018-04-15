package org.linkedgeodesy.org.gazetteerjson.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.linkedgeodesy.jenaext.log.JenaModelException;
import org.linkedgeodesy.jenaext.model.JenaModel;

/**
 * JSONObject to transform JSON to JSON-LD
 *
 * @author Florian Thiery
 */
public class JSONLD {
    
    public static JSONObject getJSONLDGazetteerResource(JSONObject json) throws IOException {
        try {
            JSONObject jsonld = new JSONObject();
            // read GeoJSON-LD Context
            JSONObject data = new JSONObject();
            URL obj = new URL("https://raw.githubusercontent.com/linkedgeodesy/geojson-plus-ld/master/geojson-context-lg.jsonld");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                data = (JSONObject) new JSONParser().parse(response.toString());
            }
            // get context
            JSONObject context = (JSONObject) data.get("@context");
            // get properties
            JSONObject properties = (JSONObject) json.get("properties");
            // get and transform names
            JSONObject names = (JSONObject) properties.get("names");
            JSONArray namesLD = new JSONArray();
            for (Iterator iterator = names.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                if (key.equals("prefName")) {
                    JSONObject prefName = (JSONObject) names.get("prefName");
                    properties.put("prefName", prefName.get("name") + "@" + prefName.get("lang"));
                } else {
                    JSONArray tmp = (JSONArray) names.get(key);
                    for (Object item : tmp) {
                        String thisItem = (String) item;
                        namesLD.add(thisItem + "@" + key);
                    }
                }
            }
            properties.remove("names");
            properties.put("names", namesLD);
            // transform id
            String id = (String) properties.get("@id");
            properties.remove("@id");
            // write JSONLD
            jsonld.put("@context", context);
            jsonld.put("type", json.get("type"));
            jsonld.put("id", id);
            jsonld.put("geometry", json.get("geometry"));
            jsonld.put("properties", properties);
            // output
            return jsonld;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getJSONLDGazetteerSearch(JSONObject json) throws IOException {
        try {
            JSONObject jsonld = new JSONObject();
            // read GeoJSON-LD Context
            JSONObject data = new JSONObject();
            URL obj = new URL("https://raw.githubusercontent.com/linkedgeodesy/geojson-plus-ld/master/geojson-context-lg.jsonld");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                data = (JSONObject) new JSONParser().parse(response.toString());
            }
            // get context
            JSONObject context = (JSONObject) data.get("@context");
            // get features
            JSONArray features = (JSONArray) json.get("features");
            JSONArray newfeatures = new JSONArray();
            for (Object feature : features) {
                JSONObject tmpfeature = (JSONObject) feature;
                JSONObject newfeature = (JSONObject) feature;
                JSONObject properties = (JSONObject) tmpfeature.get("properties");
                // get and transform names
                JSONObject names = (JSONObject) properties.get("names");
                JSONArray namesLD = new JSONArray();
                for (Iterator iterator = names.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    if (key.equals("prefName")) {
                        JSONObject prefName = (JSONObject) names.get("prefName");
                        properties.put("prefName", prefName.get("name") + "@" + prefName.get("lang"));
                    } else {
                        JSONArray tmp = (JSONArray) names.get(key);
                        for (Object item : tmp) {
                            String thisItem = (String) item;
                            namesLD.add(thisItem + "@" + key);
                        }
                    }
                }
                properties.remove("names");
                properties.put("names", namesLD);
                // transform id
                String id = (String) properties.get("@id");
                properties.remove("@id");
                // set new feature
                newfeature.put("type", tmpfeature.get("type"));
                newfeature.put("id", id);
                newfeature.put("geometry", tmpfeature.get("geometry"));
                newfeature.put("properties", properties);
                newfeatures.add(newfeature);
            }
            // write JSONLD
            jsonld.put("@context", context);
            jsonld.put("type", json.get("type"));
            jsonld.put("features", newfeatures);
            jsonld.put("metadata", json.get("metadata"));
            // output
            return jsonld;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static JSONObject getJSONLDChronOntologyJSON(JSONObject json) throws IOException {
        try {
            JSONObject jsonld = new JSONObject();
            // read GeoJSON-LD Context
            JSONObject data = new JSONObject();
            URL obj = new URL("https://raw.githubusercontent.com/linkedgeodesy/geojson-plus-ld/master/geojson-context-lg.jsonld");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                data = (JSONObject) new JSONParser().parse(response.toString());
            }
            // get context
            JSONObject context = (JSONObject) data.get("@context");
            // get features
            JSONArray features = (JSONArray) json.get("features");
            JSONArray newfeatures = new JSONArray();
            for (Object feature : features) {
                JSONObject tmpfeature = (JSONObject) feature;
                JSONObject newfeature = (JSONObject) feature;
                JSONObject properties = (JSONObject) tmpfeature.get("properties");
                // get and transform names
                JSONObject names = (JSONObject) properties.get("names");
                JSONArray namesLD = new JSONArray();
                for (Iterator iterator = names.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    if (key.equals("prefName")) {
                        JSONObject prefName = (JSONObject) names.get("prefName");
                        properties.put("prefName", prefName.get("name") + "@" + prefName.get("lang"));
                    } else {
                        JSONArray tmp = (JSONArray) names.get(key);
                        for (Object item : tmp) {
                            String thisItem = (String) item;
                            namesLD.add(thisItem + "@" + key);
                        }
                    }
                }
                properties.remove("names");
                properties.put("names", namesLD);
                // transform id
                String id = (String) properties.get("@id");
                properties.remove("@id");
                // set new feature
                newfeature.put("type", tmpfeature.get("type"));
                newfeature.put("gazetteeruri", id);
                newfeature.put("geometry", tmpfeature.get("geometry"));
                newfeature.put("properties", properties);
                newfeatures.add(newfeature);
            }
            // get and transform metadata
            JSONObject metadata = (JSONObject) json.get("metadata");
            JSONObject newmetadata = new JSONObject();
            newmetadata.put("coverage", metadata.get("coverage"));
            newmetadata.put("id", metadata.get("@id"));
            newmetadata.put("periodid", metadata.get("periodid"));
            JSONObject names = (JSONObject) metadata.get("names");
            JSONArray namesLD = new JSONArray();
            for (Iterator iterator = names.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                JSONArray tmp = (JSONArray) names.get(key);
                for (Object item : tmp) {
                    String thisItem = (String) item;
                    namesLD.add(thisItem + "@" + key);
                }
            }
            newmetadata.put("names", namesLD); 
            newmetadata.put("chronontology", metadata.get("@id"));
            newmetadata.put("when", metadata.get("when").toString());
            // write JSONLD
            jsonld.put("@context", context);
            jsonld.put("type", json.get("type"));
            jsonld.put("features", newfeatures);
            jsonld.put("metadata", newmetadata);
            jsonld.put("id", newmetadata.get("id"));
            // output
            return jsonld;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String getRDF(String jsonld, String format) throws IOException, JenaModelException {
        JenaModel jm = new JenaModel();
        jm.readJSONLD(jsonld);
        return jm.getModelAsRDFFormatedString(format);
    }
    
}
