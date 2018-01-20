package acceptancetests.whens;

import acceptancetests.TestState;
import acceptancetests.WhenShopOfHanIsCalled;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import httpclient.Request;
import httpclient.RequestBuilder;

public class WhenAProductHasBeenDeleted extends WhenShopOfHanIsCalled {

    private String productId;

    public WhenAProductHasBeenDeleted(TestState testState) {
        super(testState);
    }

    public ActionUnderTest isDeleted() {
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeADeleteRequestTo(capturedInputAndOutputs, buildRequest());
    }

    @Override
    protected Request buildRequest() {
        return new RequestBuilder()
                .url("http://localhost:8081/product/id/" + productId)
                .method("DELETE")
                .body("")
                .build();
    }

    public WhenAProductHasBeenDeleted withProductId(String productId) {
        this.productId = productId;
        return this;
    }
}
