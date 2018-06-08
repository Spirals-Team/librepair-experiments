package cn.productai.apiv2.lib;

import cn.productai.api.core.enums.HttpMethod;
import cn.productai.apiv2.exceptions.PAIException;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Map;

public class Http {

    private static final Logger logger = LogManager.getLogger(Http.class);
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static String request(HttpMethod httpMethod,
                                 String url,
                                 Map<String, String> headers,
                                 String json,
                                 String fileFieldName, File file) throws PAIException {
        // Set URL
        Request.Builder request = new Request.Builder()
                .url(url);

        // Set headers
        if (headers != null) {
            headers.forEach(request::addHeader);
        }

        // Set body
        RequestBody body = null;
        if (json != null) {
            body = RequestBody.create(JSON, json);
        }

        if (fileFieldName != null && file != null) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                            fileFieldName,
                            file.getName(),
                            RequestBody.create(MediaType.parse("text/csv"), file))
                    .build();
        }

        if (httpMethod.equals(HttpMethod.POST)) {
            request.post(body);
        }

        if (httpMethod.equals(HttpMethod.PUT)) {
            request.put(body);
        }

        if (httpMethod.equals(HttpMethod.PATCH)) {
            request.patch(body);
        }

        if (httpMethod.equals(HttpMethod.DELETE)) {
            if (body != null) {
                request.delete(body);
            } else {
                request.delete();
            }
        }

        try {
            Response response = client.newCall(request.build()).execute();
            String resBody = null;
            if (response.body() != null) {
                resBody = response.body().string();
            }
            if (response.code() >= 400) {
                logger.error("Request error, response code is "
                        + response.code() + ", response body: " + resBody);
                throw new PAIException("Request fail, response: " + resBody);
            }

            return resBody;
        } catch (Exception e) {
            logger.error("Http error", e.getMessage());
            throw new PAIException(e);
        }
    }


    public static String request(HttpMethod httpMethod,
                                 String url,
                                 Map<String, String> headers, String json) throws PAIException {
        return request(httpMethod, url, headers, json, null, null);
    }

    public static String request(HttpMethod httpMethod,
                                 String url,
                                 Map<String, String> headers) throws PAIException {
        return request(httpMethod, url, headers, null, null, null);
    }

    public static String request(HttpMethod httpMethod,
                                 String url,
                                 Map<String, String> headers, String fileFieldName, File file) throws PAIException {
        return request(httpMethod, url, headers, null, fileFieldName, file);
    }
}
