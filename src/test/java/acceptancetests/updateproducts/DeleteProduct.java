package acceptancetests.updateproducts;

import acceptancetests.AcceptanceTest;
import acceptancetests.whens.WhenAProductHasBeenDeleted;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;

@RunWith(SpecRunner.class)
public class DeleteProduct extends AcceptanceTest {
    @Test
    public void deleteAProductUsingItsUniqueId() throws Exception {
        given(aProductAlreadyExists());

        when(whenAProduct.withProductId("CTD1").isDeleted());

//        thenTheDatabaseContainsAProductWithName("Clojure the door", withProductId("CTD1"));
        andTheStatusCodeIs(200); // 204 no body returned, 202 aysnc
        andTheResponseContains("Product Deleted CTD1");
    }

    // TODO test if product not in database, usecase returns optional.empty, and  404

    private void andTheResponseContains(String repsonsebody) throws Exception {
        then.theBodyIs("Product Deleted CTD1");
    }

    private void andTheStatusCodeIs(int statusCode) throws Exception {
        then.theStatusCodeIs(statusCode);
    }

    private GivensBuilder aProductAlreadyExists() throws SQLException {
        productRepository.addProduct(product(productDescription("Book about clojure"), productId("CTD1"), productName("CLojure the door")));
        testState().interestingGivens.add("productId", "CTD1");
        return givens -> givens;
    }

    // TODO Add yatspec database recorder
    private void thenTheDatabaseContainsAProductWithName(String productName, String productId) {
        then.theProductDatabaseDoesnotHave(productName, productId);
    }

    private final WhenAProductHasBeenDeleted whenAProduct = new WhenAProductHasBeenDeleted(testState);
}

