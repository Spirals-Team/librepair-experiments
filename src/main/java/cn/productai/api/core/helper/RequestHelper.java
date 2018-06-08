package cn.productai.api.core.helper;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.internal.HttpResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

public class RequestHelper {
    private static HashMap<Integer, String> _httpMethodDics = EnumHelper.toHashMap(HttpMethod.class);
    private static Charset _charset = Charset.forName("UTF-8");

    private static <T extends BaseResponse> HttpURLConnection createRequest(BaseRequest<T> request) throws Exception {

        String api = request.getApiUrl();

        if (request.getRequestMethod() == HttpMethod.GET && !request.getQueryString().isEmpty()) {
            URI uri = new URI(request.getApiUrl());
            if (uri.getQuery() != null && !uri.getQuery().isEmpty()) {
                api = api + "&" + request.getQueryString();
            } else {
                api = api + "?" + request.getQueryString();
            }
        }

        URL url = new URL(api);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(request.getRequestMethodHeader());
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", request.getContentTypeHeader());
        connection.setRequestProperty("User-Agent", request.getUserAgent());

        if (request.getHeaders() != null && request.getHeaders().size() > 0) {
            for (String key : request.getHeaders().keySet()) {
                connection.setRequestProperty(key, request.getHeaders().get(key));
            }
        }

        connection.connect();

        return connection;
    }

    private static void writeRequestParas(HttpURLConnection connection, String query) throws IOException {
        byte[] buffer = query.getBytes(_charset);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(buffer, 0, buffer.length);
        outputStream.flush();
        outputStream.close();
    }

    private static void writeRequestParas(HttpURLConnection connection, byte[] buffer) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(buffer, 0, buffer.length);
        outputStream.flush();
        outputStream.close();
    }

    private static byte[] readStreamBytes(HttpURLConnection connection) throws IOException {
        InputStream inputStream;
        if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String lines;
        StringBuilder sb = new StringBuilder("");
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), _charset);
            sb.append(lines);
        }
        reader.close();

        return sb.toString().getBytes(_charset);
    }

    public static <T extends BaseResponse> HttpResponse getResponse(BaseRequest<T> request) throws Exception {
        HttpResponse httpResponse;

        HttpURLConnection connection = createRequest(request);
        if (request.getRequestMethod() != HttpMethod.GET) {
            if (request.getQueryBytes() != null) {
                writeRequestParas(connection, request.getQueryBytes());
            } else {
                writeRequestParas(connection, request.getQueryString());
            }
        }
        int statusCode = connection.getResponseCode();

        byte[] bytes = readStreamBytes(connection);

        httpResponse = new HttpResponse();
        httpResponse.setStatusCode(statusCode);
        httpResponse.setResponseBytes(bytes);
        httpResponse.setHeaders(connection.getHeaderFields());

        return httpResponse;
    }
}
