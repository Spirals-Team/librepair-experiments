package com.revature.project2.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GoogleAPI {

    private String placeKey = "AIzaSyBniCKg_o2aLYssJITJucR5XSFzVYxLWXk";
    private String distanceKey = "AIzaSyDUrMbVr7eSLHkhxtPbLLv_7Vad5RMB7x8";
    private String mapsKey = "AIzaSyC6jRrVgkpuZMzcTqQPW_XSPE8RFML6oOQ";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/google/prediction")
    public ResponseEntity<String> getLocPredictions(@RequestBody String input) {
        return restTemplate.exchange(
                "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + input + "&key=" + placeKey,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});
    }

    @PostMapping("/google/location")
    public ResponseEntity<String> getLocInfo(@RequestBody String query) {
        return restTemplate.exchange(
                "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + query + "&key=" + placeKey,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});
    }

    @PostMapping("/google/details")
    public ResponseEntity<String> getLocDetails(@RequestBody String placeId) {
        return restTemplate.exchange(
                "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeId + "&key=" + placeKey,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});
    }

    @PostMapping("/google/distance")
    public ResponseEntity<String> getDistanceInfo(@RequestBody String[] placeIds) {
        StringBuilder queryStr = new StringBuilder("origins=place_id:").append(placeIds[0]).append("&destinations=");
        for(int i=1; i<placeIds.length; i++) {
            queryStr.append("place_id:").append(placeIds[i]);
            if(i<placeIds.length-1)
                queryStr.append("|");
        }

        return restTemplate.exchange(
                "https://maps.googleapis.com/maps/api/distancematrix/json??units=imperial&" + queryStr + "&key=" + distanceKey,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});
    }

    @PostMapping("/google/map")
    public ResponseEntity<String> getMapUrl(@RequestBody String placeId) {
        return new ResponseEntity(
                "{\"url\":\"https://www.google.com/maps/embed/v1/place?q=place_id:" + placeId + "&key=" + mapsKey + "\"}",
                HttpStatus.OK);
    }
}
