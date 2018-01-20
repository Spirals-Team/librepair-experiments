package httpclient;

import org.apache.http.client.methods.HttpGet;

import java.net.MalformedURLException;

import static httpclient.Headers.fromApacheHeaders;

public class Request extends ValueType {
    public final String url;
    private final String method;
    public final Headers headers;
    public final QueryParameters queryParameters;
    public final String body;

    Request(String url, String method, Headers headers, QueryParameters queryParameters, String body) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.queryParameters = queryParameters;
        this.body = body;
    }

    public static Request toNiceRequestForYatspec(HttpGet request) {
        try {
            System.out.println("afjahsflkajsflk ajsfhkajshdflkjahsldfkjhasdklfj");
            System.out.println(request.getURI().toURL().toString());
            System.out.println(request.getMethod());
            System.out.println(fromApacheHeaders(request.getAllHeaders()));

            return new Request(request.getURI().toURL().toString(), request.getMethod(), fromApacheHeaders(request.getAllHeaders()), QueryParameters.empty(), "");
        } catch (MalformedURLException exception) {
            throw new RuntimeException("TODO make sure that then request has a proper format", exception);
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s%s%n%s%n%n%s", method, url, queryParameters, headers, body);
    }
}
