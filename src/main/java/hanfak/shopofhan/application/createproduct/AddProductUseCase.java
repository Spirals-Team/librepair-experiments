package hanfak.shopofhan.application.createproduct;

import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.domain.product.Product;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.Optional;

import static java.lang.String.format;

public class AddProductUseCase {
    private ProductRepository productRepository;
    private Logger logger;

    public AddProductUseCase(ProductRepository productRepository, Logger logger) {
        this.productRepository = productRepository;
        this.logger = logger;
    }

    public void addProduct(Product product) throws SQLException {
        Optional<Product> storedProduct = productRepository.addProduct(product); // TODO make this void return
        storedProduct.orElseThrow(this::illegalStateException);
        logger.info(format("Product '%s' added", storedProduct.get().productId));
    }

    private IllegalStateException illegalStateException() {
        logger.info("Product was not added");
        return new IllegalStateException("Product was not added");
    }
}
