package com.objectia.twostep.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.objectia.Twostep;
import com.objectia.twostep.exception.APIConnectionException;
import com.objectia.twostep.exception.APIException;
import com.objectia.twostep.exception.TwostepException;
import com.objectia.twostep.model.Error;
import com.objectia.twostep.model.Response;;

public class HttpClient {

    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

    public static Response get(String uri) throws TwostepException {
        return request("GET", uri, null, null);
    }

    public static Response post(String uri, Object params) throws TwostepException {
        return request("POST", uri, null, params);
    }

    public static Response put(String uri, Object params) throws TwostepException {
        return request("PUT", uri, null, params);
    }

    public static Response delete(String uri) throws TwostepException {
        return request("DELETE", uri, null, null);
    }


    public static Response request(String method, String uri, String accessToken, Object payload)
            throws TwostepException {
        HttpsURLConnection conn = null;
        try {
            URL url = new URL(uri);

            conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod(method);
            if (accessToken != null) {
                conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            }
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", Twostep.userAgent);

            conn.setConnectTimeout(Twostep.connectTimeout);
            conn.setReadTimeout(Twostep.readTimeout);
            conn.setUseCaches(false);

            if (payload != null) {
                // Add payload
                conn.setDoOutput(true);
                String json = GSON.toJson(payload);
                byte buffer[] = json.getBytes("UTF-8");
                conn.setFixedLengthStreamingMode(buffer.length);
                OutputStream os = conn.getOutputStream();
                os.write(buffer);
                os.flush();
                os.close();
            }

            String body = null;
            int statusCode = conn.getResponseCode();
            if (statusCode >= 200 && statusCode < 300) {
                body = getBody(conn.getInputStream());
            } else {
                body = getBody(conn.getErrorStream());
                Error err = GSON.fromJson(body, Error.class);
                throw new APIException(err.getStatus(), err.getMessage());
            }

            return new Response(statusCode, body, conn.getHeaderFields());
        } catch (IOException ex) {
            throw new APIConnectionException(
                    "Unable to connect to server. Please check your internet connection and try again.");
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static String getBody(InputStream is) throws IOException {
        //\A is the beginning of the stream boundary
        Scanner scanner = new Scanner(is, "UTF-8");
        scanner.useDelimiter("\\A");
        String body = scanner.next();
        scanner.close();
        is.close();
        return body;
    }
}
