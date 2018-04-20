package com.justinfrasier.https;

import com.justinfrasier.logger.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPS {

    public String sendGet(String url){
        try {


                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                int responseCode = connection.getResponseCode();
                if (responseCode != 200) return null;
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) response.append(inputLine);
                in.close();
                return response.toString();


        }catch (IOException e){
            Log log = new Log();
            log.writeln(e.getMessage());
            log.close();
            return null;
        }
    }
}
