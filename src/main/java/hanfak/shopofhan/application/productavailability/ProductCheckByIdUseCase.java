package hanfak.shopofhan.application.productavailability;

import hanfak.shopofhan.application.crosscutting.StockRepository;
import hanfak.shopofhan.domain.ProductStock;
import hanfak.shopofhan.domain.product.ProductId;
import org.slf4j.Logger;

import java.util.Optional;

import static java.lang.String.format;

@SuppressWarnings("Duplicates")
public class ProductCheckByIdUseCase {

    private final StockRepository stockRepository;
    private final Logger logger;

    public ProductCheckByIdUseCase(StockRepository stockRepository, Logger logger) {
        this.stockRepository = stockRepository;
        this.logger = logger;
    }
    // TODO more details in logs ie product details etc
    //TODO test when name passes instead of
    public ProductStock checkStock(ProductId productId) {
        logger.info(format("checking stock by Id '%s'", productId));
        Optional<ProductStock> checkStock = stockRepository.checkStockById(productId);

        /*
        * Can throw error, or bubble up the optional to the webservice which will the decide on the response, thus no
        * if statement here
        **/
        ProductStock productStock = checkStock.orElseThrow(this::illegalStateException);
        logger.info(format("Stock checked and returned '%s'", checkStock.get()));

        return productStock;
    }

    private IllegalStateException illegalStateException() {
        logger.info("Stock checked and returned nothing");
        return new IllegalStateException("Product is not found");
    }
}
