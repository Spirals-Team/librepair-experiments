package com.rgross.parser;


import com.rgross.exception.CountyNotFoundException;
import com.rgross.exception.InvalidLineFormatException;
import com.rgross.model.County;
import com.rgross.model.Location;
import com.rgross.parser.common.CSVParser;
import com.rgross.repository.CountyRepository;
import com.rgross.repository.LocationRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ryan_gross on 10/26/17.
 */

@Component
public class LocationParser extends CSVParser  implements com.rgross.parser.common.JSONParser {

    private final static Logger logger = LogManager.getLogger(LocationParser.class);

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CountyRepository countyRepository;

    public LocationParser(CountyRepository countyRepository, LocationRepository locationRepository) {
        this.countyRepository = countyRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public JSONObject callExternalService(String zipCode) throws ParseException, IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet getMethod = new HttpGet("http://ZiptasticAPI.com/" + zipCode);
        JSONObject jsonObject = null;
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(getMethod);
            String json = EntityUtils.toString(response.getEntity());
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(json);
        } catch (ParseException | IOException exception) {
            // Added logger here
        } finally {
            httpClient.close();
            response.close();
        }

        return jsonObject;
    }

    public Location generateLocation(String line) throws InvalidLineFormatException, CountyNotFoundException {
        String fieldSeparatorPattern = "[\"][0-9]*[\"]|[A-Z][^\"]*[,]\\s[A-Z]{2}|[0-1]$|[0][^,0-9][0-9]{0,3}";

        Pattern pattern = Pattern.compile(fieldSeparatorPattern);
        Matcher matcher = pattern.matcher(line);
        List<String> parsedFields = new ArrayList<>();
        County countyLocationResidesIn = null;

        while (matcher.find()) {
            parsedFields.add(matcher.group().replaceAll("\"", ""));
        }

        if (parsedFields.size() != 4) {
            for (String x : parsedFields) {
                System.out.println(x);
            }
            throw new InvalidLineFormatException();
        }


        String zipCode = parsedFields.get(0);
        String fipsCode = parsedFields.get(1);
        String[] cityAndState = parsedFields.get(2).split(",");
        String city = cityAndState[0].toUpperCase();
        String state = cityAndState[1].replaceAll(" ", "");
        Double percentageRate = Double.valueOf(parsedFields.get(3).trim());

        if (!countyRepository.containsCounty(fipsCode)) {
            throw new CountyNotFoundException();
        } else {
            countyLocationResidesIn = countyRepository.findByfipsNumber(fipsCode);
        }

        Location location = new Location(city, state, zipCode, countyLocationResidesIn, percentageRate);

        return location;
    }

    @Override
    public Object processResponse(JSONObject jsonResponse) throws ParseException, IOException {
        List<String> cityAndState = new ArrayList<>();

        String city = (String) jsonResponse.get("city");
        String state = (String) jsonResponse.get("state");

        cityAndState.add(city);
        cityAndState.add(state);

        return cityAndState;
    }

    // This is used for contracts, not for the CSV File. Move it.
    public List<String> getCityAndStateFromZiptastic(String zipCode) throws ParseException, IOException {
        JSONObject outputFromRequest = callExternalService(zipCode);

        return (List<String>) processResponse(outputFromRequest);
    }


    @Override
    public void processLine(String line) throws Exception {
        Location generatedLocation = generateLocation(line);
        String zipCode = generatedLocation.getZipCode();
        String fipsCode = generatedLocation.getCounty().getFipsNumber();

//        if(!locationRepository.existsByCityAndFipsCode(zipCode, fipsCode)) {
        locationRepository.save(generatedLocation);
//    }

    }




}
