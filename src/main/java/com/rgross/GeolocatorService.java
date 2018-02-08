package com.rgross;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by Ryan on 1/26/2018.
 */
public class GeolocatorService {

    private static GeolocatorService geolocatorServiceInstance = new GeolocatorService();

    public static GeolocatorService getGeolocatorServiceInstance() {
        return geolocatorServiceInstance;
    }

    public static String getCountyFromMapQuest(String address) throws IOException, ParseException {
        String formattedAddress = address.trim().replaceAll(" ", "+");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String mapQuestUrl = "http://www.mapquestapi.com/geocoding/v1/address?key=5zAdzx8OCWt8IGn121JridDpzeEFLHIy&location=" + formattedAddress;
        HttpGet getRequest = new HttpGet(mapQuestUrl);
        CloseableHttpResponse response = httpClient.execute(getRequest);
        String json = EntityUtils.toString(response.getEntity());
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONArray array = (JSONArray) jsonObject.get("results");
        response.close();
        httpClient.close();
        JSONObject test = (JSONObject) array.get(0);
        JSONArray locations = (JSONArray) test.get("locations");
        JSONObject derp =  (JSONObject) locations.get(0);
        Object placeholder = null;
        for (Object x : derp.keySet()) {
            if (x.toString().equals("adminArea4")) {
                placeholder = x;
            }
        }
        return derp.get(placeholder).toString();
    }
}
