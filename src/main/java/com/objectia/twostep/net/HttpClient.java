package com.objectia.twostep.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.objectia.Twostep;
import com.objectia.twostep.exception.APIException;
import com.objectia.twostep.exception.BadRequestException;
import com.objectia.twostep.exception.ConnectionException;
import com.objectia.twostep.exception.NotFoundException;
import com.objectia.twostep.exception.RequestFailedException;
import com.objectia.twostep.exception.TwostepException;
import com.objectia.twostep.exception.UnauthorizedException;
import com.objectia.twostep.model.Response;

public class HttpClient {

    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

    public static <T> Response<T> request2(String method, String uri, String accessToken, Object payload,
            Class<T> dataClass) throws TwostepException {
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

            if (method.equals("POST") || method.equals("PUT")) {
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
            int responseCode = conn.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                body = getBody(conn.getInputStream());
            } else {
                body = getBody(conn.getErrorStream());
                switch (responseCode) {
                case 400:
                    throw new BadRequestException(body);
                case 401:
                    throw new UnauthorizedException(body);
                case 403:
                    throw new RequestFailedException(body);
                case 404:
                    throw new NotFoundException(body);
                default:
                    throw new APIException(body);
                }
            }

            Type type = TypeToken.getParameterized(Response.class, dataClass).getType();
            return GSON.fromJson(body, type); 
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ConnectionException(
                    "Unable to connect to server. Please check your internet connection and try again.");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ConnectionException("XXXX");
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static <T> T request(String method, String uri, String accessToken, Object payload, Class<T> cls)
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

            if (method.equals("POST") || method.equals("PUT")) {
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
            int responseCode = conn.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                body = getBody(conn.getInputStream());
            } else {
                body = getBody(conn.getErrorStream());
                switch (responseCode) {
                case 400:
                    throw new BadRequestException(body);
                case 401:
                    throw new UnauthorizedException(body);
                case 403:
                    throw new RequestFailedException(body);
                case 404:
                    throw new NotFoundException(body);
                default:
                    throw new APIException(body);
                }
            }

            return GSON.fromJson(body, cls);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ConnectionException(
                    "Unable to connect to server. Please check your internet connection and try again.");
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static <T> T get(String uri, Class<T> cls) throws TwostepException {
        return request("GET", uri, null, null, cls);
    }

    public static <T> T post(String uri, Object params, Class<T> cls) throws TwostepException {
        return request("POST", uri, null, params, cls);
    }

    public static <T> T put(String uri, Object params, Class<T> cls) throws TwostepException {
        return request("PUT", uri, null, params, cls);
    }

    public static <T> T delete(String uri, Class<T> cls) throws TwostepException {
        return request("DELETE", uri, null, null, cls);
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
