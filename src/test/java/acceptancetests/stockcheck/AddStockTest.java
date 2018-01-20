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
public class AddStockTest extends AcceptanceTest {

    @Test
    public void shouldReturn200WhenStoringNewStock() throws Exception {
        given(theSystemIsRunning());
        and(aProductAlreadyExists());

        when(stockIsAdded.throughARequestTo("http://localhost:8081/stock", withProductId("CTD1"), withStockId("STD1"), withStockDescription("Single Pack"), andAmount(5)));

        thenItReturnsAStatusCodeOf(200);
        andTheResponseBodyIs("Product with id, 'CTD1', has been added.Stock of '5' items for Product with id, 'CTD1', has been added.");
        andTheDatabaseContainsAStockWithStockId("STD1", withProductId("CTD1"), withStockDescription("Single Pack"), 5);
    }

    // TODO test stock cannot be added if product is not in database

    private long andAmount(long amount) {
        return amount;
    }

    private GivensBuilder aProductAlreadyExists() throws SQLException {
        productRepository.addProduct(product(productDescription("Book about clojure"), productId("CTD1"), productName("CLojure then door")));
        return givens -> givens;
    }

    private void andTheDatabaseContainsAStockWithStockId(String stockID, String productId, String stockDescription, int amount) {
        then.theStockDatabaseHasA(stockID, productId, stockDescription, amount);
    }

    private String withStockDescription(String description) {
        return description;
    }

    private String withStockId(String name) {
        return name;
    }

    private String withProductId(String id) {
        actualProductId = ProductId.productId(id);
        return id;
    }

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

    private ProductId actualProductId;
}
