package acceptancetests.stockcheck;

import acceptancetests.AcceptanceTest;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import hanfak.shopofhan.domain.product.ProductId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.sql.SQLException;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;

@RunWith(SpecRunner.class)
public class AddProductTest extends AcceptanceTest {

    private ProductId actualProductId;

    @Test
    public void shouldReturnStockAmountForItem() throws Exception {
        given(theSystemIsRunning());
        when(aNewProductIsAdded.throughARequestTo("http://localhost:8081/products", withProductId("CTD1"), withProductName("Clojure then door"), andProductDescription("Book about Clojure")));
        thenItReturnsAStatusCodeOf(200);
        andTheResponseBodyIs("Product with id, 'CTD1', has been added.");
        andTheDatabaseContainsAProductWithName("Clojure then door", withProductId("CTD1"), andProductDescription("Book about Clojure"));
    }

    @Test
    public void shouldReturnProductAlreadyExists() throws Exception {
        given(theSystemIsRunning());
        and(aProductAlreadyExists());
        when(aNewProductIsAdded.throughARequestTo("http://localhost:8081/products", withProductId("STS1"), withProductName("SQL then sequel"), andProductDescription("Book about SQL")));
        thenItReturnsAStatusCodeOf(404);
        andTheResponseBodyIs("Product with id, 'STS1', has not been added, as it already exists.");
    }

    private GivensBuilder aProductAlreadyExists() throws SQLException {
        productRepository.addProduct(product(productDescription("Book about SQL"), productId("STS1"), productName("SQL then sequel")));
        return givens -> givens;
    }

    private void andTheDatabaseContainsAProductWithName(String name, String id, String description) {
        then.theProductDatabaseHasA(actualProductId, name, id, description);
    }

    private String andProductDescription(String description) {
        return description;
    }

    private String withProductName(String name) {
        return name;
    }

    private String withProductId(String id) {
        actualProductId = ProductId.productId(id);
        return id;
    }

    // TODO SAD path
    // techinical failure

    private GivensBuilder theSystemIsRunning() throws IOException {
        testState().interestingGivens.add("productName", "Joy Of Java");
        // TODO add parts of products to interestinggivens
        return givens -> givens;
    }

    private void andTheResponseBodyIs(String expected) throws Exception {
        then.theBodyIs(expected);
    }

    private void thenItReturnsAStatusCodeOf(int expected) throws Exception {
        then.theStatusCodeIs(expected);
    }

}