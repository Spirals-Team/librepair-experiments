package hanfak.shopofhan.application.productavailability;

import hanfak.shopofhan.application.crosscutting.ProductStockRepository;
import hanfak.shopofhan.domain.ProductStockList;
import hanfak.shopofhan.domain.product.ProductId;
import org.slf4j.Logger;

import java.util.Optional;

import static java.lang.String.format;

public class ProductStockCheckByIdUseCase {
    private final ProductStockRepository stockRepository;
    private final Logger logger;

    public ProductStockCheckByIdUseCase(ProductStockRepository stockRepository, Logger logger) {
        this.stockRepository = stockRepository;
        this.logger = logger;
    }

    public ProductStockList checkStock(ProductId productId) {
        logger.info(format("checking stock by Id '%s'", productId)); // TODO test
        Optional<ProductStockList> checkStock = stockRepository.findListOfProductStock(productId);
//        logResultOfStockCheck(checkStock);
        ProductStockList productStock = checkStock.orElseThrow(this::illegalStateException);
//        logger.info("Stock is there");

        return checkStock.get();
    }

//    private void logResultOfStockCheck(Optional<ProductStockList> checkStock) {
//        if (checkStock.isPresent()) {
//            logger.info(format("Stock checked and returned '%s'", checkStock.get()));
//        } else {
//            logger.info("Stock checked and returned nothing");
//        }
//    }
//
    private IllegalStateException illegalStateException() {
        logger.info("Stock not there");
        return new IllegalStateException("Product is not found");
    }
}
