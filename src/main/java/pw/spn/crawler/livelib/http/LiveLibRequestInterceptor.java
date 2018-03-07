package pw.spn.crawler.livelib.http;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class LiveLibRequestInterceptor implements Interceptor {
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String HEADER_USER_AGENT_VALUE = String
            .format("LiveLib/%1$s/%2$d (%3$s; Android %4$s; API %5$s)", "3.0.6", 15030006, "Nexus 1", "3.4b5", 21);
    private static final String LOGIN = "andyll";
    private static final String PASSWORD = "and7mpp4ss";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Builder requestBuilder = original.newBuilder();
        HttpUrl.Builder httpUrlBuilder = original.url().newBuilder();
        requestBuilder.header(HEADER_USER_AGENT, HEADER_USER_AGENT_VALUE);
        httpUrlBuilder.addQueryParameter(LOGIN, PASSWORD);
        Request request = requestBuilder.url(httpUrlBuilder.build()).build();
        return chain.proceed(request);
    }

}
