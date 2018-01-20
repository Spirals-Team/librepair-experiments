package acceptancetests.whens;

import acceptancetests.TestState;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import httpclient.Request;
import httpclient.RequestBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

import static acceptancetests.AcceptanceTest.APPLICATION_NAME;
import static httpclient.Header.CONTENT_TYPE_KEY;
import static httpclient.Header.TEXT_PLAIN;
import static java.lang.String.format;

public class Whens {
    private static final String CALLER = "Shop User";
    private TestState testState;

    public Whens(TestState testState) {
        this.testState = testState;
    }

    public ActionUnderTest aGetRequestTo(String uri) {
        //TODO Add builder to build request
        // hydra/src/test/java/sky/sns/hydra/hanfak.shopofhan.infrastructure/httpclient/TrackingApacheHttpClientFactoryTest.java:38
        Request request = new RequestBuilder()
                .url(uri)
                .header(CONTENT_TYPE_KEY, TEXT_PLAIN)
                .method("GET")
                .body("")
                .build();
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeARequestTo(capturedInputAndOutputs, request);
    }

    private CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, Request request) throws IOException {
        capturedInputAndOutputs.add(format("Request from %s to %s",CALLER, APPLICATION_NAME), request);
        testState.add("request", request); //Need???
        HttpGet httpGet = new HttpGet(request.url);
        System.out.println("request = " + httpGet);
        CloseableHttpResponse execute = HttpClientBuilder.create().build().execute(httpGet);
        httpclient.Response domainResponse = httpclient.Response.fromApacheResponse(execute);
        testState.add("response", domainResponse);
        capturedInputAndOutputs.add(format("Response from %s to %s", APPLICATION_NAME, CALLER), domainResponse);
        return capturedInputAndOutputs;
    }
}
