package acceptancetests.thens;

import acceptancetests.TestState;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.stock.Stock;
import httpclient.Response;
import org.assertj.core.api.WithAssertions;

import java.util.Optional;

import static acceptancetests.AcceptanceTest.productRepository;
import static acceptancetests.AcceptanceTest.stockRepository;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;

public class Then implements WithAssertions {

    private TestState testState;
    private final CapturedInputAndOutputs capturedInputAndOutputs;

    public Then(TestState testState, CapturedInputAndOutputs capturedInputAndOutputs) {
        this.testState = testState;
        this.capturedInputAndOutputs = capturedInputAndOutputs;
    }

    public void theStatusCodeIs(int expected) throws Exception {
        StateExtractor<Integer> contentTypeExtractor = capturedInputAndOutputs ->
                ((Response) testState.get("response")).statusCode;
        assertThat(contentTypeExtractor.execute(capturedInputAndOutputs)).isEqualTo(expected);
    }

    public void theBodyIs(String expected) throws Exception {
        StateExtractor<String> contentTypeExtractor = capturedInputAndOutputs ->
                ((Response) testState.get("response")).body;
        String execute = contentTypeExtractor.execute(capturedInputAndOutputs);
        System.out.println("blah " + execute);
        assertThat(execute).isEqualTo(expected);
    }

    public void theContentTypeIs(String expected) throws Exception {
        StateExtractor<String> contentTypeExtractor = capturedInputAndOutputs ->
                ((Response) testState.get("response")).getContentType();
        assertThat(contentTypeExtractor.execute(capturedInputAndOutputs)).contains(expected);
    }


    // TODO Extract database thens to another class
    //TODO naming of method for readbility in yatspec output
    public void theProductDatabaseHasA(ProductId actualProductId, String name, String id, String description) {
        Optional<Product> product = productRepository.checkProductById(actualProductId);
        if (product.isPresent()) {
            assertThat(product.get().productName).isEqualTo(productName(name));
            assertThat(product.get().productId).isEqualTo(productId(id));
            assertThat(product.get().productDescription).isEqualTo(productDescription(description));
        } else {
            fail("Noth" +
                    "ing matches in then productDatabase");
        }
    }

    public void theStockDatabaseHasA(String stockId, String productId, String stockDescription, int amount) {
        // TODO: add to test db which inherits from JDBCStockRepository and returns then stock using then parameters
        Stock stock = stockRepository.checkStock(stockId, productId);
        assertThat(stock.amount.value).isEqualTo(amount);
        assertThat(stock.stockDescription.value).isEqualTo(stockDescription);
    }

    public void theProductDatabaseDoesnotHave(String productName, String productId) {
        Optional<Product> product = productRepository.checkProductById(productId(productId));
        assertThat(product).isEmpty();
    }
}