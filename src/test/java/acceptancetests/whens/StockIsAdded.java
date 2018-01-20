package acceptancetests.whens;

import acceptancetests.TestState;
import acceptancetests.WhenShopOfHanIsCalled;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import httpclient.Request;
import httpclient.RequestBuilder;

import static httpclient.Header.APPLICATION_JSON;
import static httpclient.Header.CONTENT_TYPE_KEY;
import static java.lang.String.format;

public class StockIsAdded extends WhenShopOfHanIsCalled {

    public StockIsAdded(TestState testState) {
        super(testState);
    }

    public ActionUnderTest throughARequestTo(String uri, String productId, String stockId, String stockDescription, long amount) {
        //TODO Add builder to build request
        // hydra/src/test/java/sky/sns/hydra/hanfak.shopofhan.infrastructure/httpclient/TrackingApacheHttpClientFactoryTest.java:38
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeAPostRequestTo(capturedInputAndOutputs, buildRequest(uri, productId, stockId, stockDescription, amount));
    }

    private Request buildRequest(String uri, String productId, String stockId, String stockDescription, long amount) {
        return new RequestBuilder()
                .url(uri)
                .header(CONTENT_TYPE_KEY, APPLICATION_JSON)
                .method("POST")
                .body(format("{" +
                        "\"productId\" : \"%s\", " +
                        "\"stockId\" : \"%s\", " +
                        "\"stockDescription\" : \"%s\", " +
                        "\"amount\" : %s}", productId, stockId, stockDescription, amount))
                .build();
    }


    // Ignored but using a different way instead of builder to create request
    @Override
    protected Request buildRequest() {
        return null;
    }
}
