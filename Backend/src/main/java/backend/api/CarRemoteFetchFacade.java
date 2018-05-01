/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.api;

import backend.exceptions.NotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Stanislav
 */
public class CarRemoteFetchFacade {

    String baseUrl = "http://localhost:3000/cars";

    private String fetch(String url) {

        URL address;
        try {
            address = new URL(url);
        } catch (MalformedURLException ex) {
            throw new NotFoundException("URL not found");
        }
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) address.openConnection();
        } catch (IOException ex) {
            throw new NotFoundException("Unable to connect");
        }

        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException ex) {
            throw new NotFoundException("Internal error happened!");
        }
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "server");

        Scanner scan;
        try {
            scan = new Scanner(conn.getInputStream());
        } catch (IOException ex) {
            throw new NotFoundException("Unable to connect");
        }
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }

    public String getAll() {
        String URL = baseUrl;
        return fetch(URL);
    }

    public String getByRegNo(String regNo) {
        String URL = baseUrl +"?regno=" + regNo;
        return fetch(URL);
    }
}
