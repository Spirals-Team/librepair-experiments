package testinfrastructure;

import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.product.ProductId;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;

public class TestProductRepository implements ProductRepository {

    @SuppressWarnings("WeakerAccess")
    public TestProductRepository() {
        populateProductStockLists();
    }

    @Override
    public Optional<Product> checkProductById(ProductId productId) {
        return productLists.stream()
                .filter(product -> product.productId.equals(productId))
                .findFirst();
    }

    @Override
    public Optional<Product> addProduct(Product product) throws SQLException {
        if (checkProductById(product.productId).isPresent()) {
            return Optional.empty();
        }
        productLists.add(product);
        return Optional.of(product);
    }

    private void populateProductStockLists() {
        productLists.add(JOY_OF_JAVA_PRODUCT);
        productLists.add(SQL_THE_SEQUEL_PRODUCT);
    }

    private static final List<Product> productLists = new ArrayList<>();
    private static final Product JOY_OF_JAVA_PRODUCT = product(productDescription("Book about java"), productId("JOJ1"), productName("Joy Of Java"));
    private static final Product SQL_THE_SEQUEL_PRODUCT = product(productDescription("Book about SQL"), productId("STS1"), productName("SQL then sequel"));
}
