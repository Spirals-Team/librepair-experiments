package acceptancetests.whens;

import acceptancetests.TestState;
import acceptancetests.WhenShopOfHanIsCalled;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import httpclient.Request;
import httpclient.RequestBuilder;

import static httpclient.Header.APPLICATION_JSON;
import static httpclient.Header.CONTENT_TYPE_KEY;
import static java.lang.String.format;

public class ANewProductIsAdded extends WhenShopOfHanIsCalled {

    public ANewProductIsAdded(TestState testState) {
        super(testState);
    }

    public ActionUnderTest throughARequestTo(String uri, String id, String name, String description) {
        //TODO Add builder to build request
        // hydra/src/test/java/sky/sns/hydra/hanfak.shopofhan.infrastructure/httpclient/TrackingApacheHttpClientFactoryTest.java:38
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeAPostRequestTo(capturedInputAndOutputs, buildRequest(uri, id, name, description));
    }

    private Request buildRequest(String uri, String id, String name, String description) {
        return new RequestBuilder()
                .url(uri)
                .header(CONTENT_TYPE_KEY, APPLICATION_JSON)
                .method("POST")
                .body(format("{\"productName\" : \"%s\", " +
                        "\"productId\" : \"%s\", " +
                        "\"productDescription\" : \"%s\"}", name, id, description))
                .build();
    }

    // Ignored but using a different way instead of builder to create request
    @Override
    protected Request buildRequest() {
        return null;
    }
}
