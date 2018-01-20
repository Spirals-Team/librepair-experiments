package hanfak.shopofhan.application.productavailability;

import hanfak.shopofhan.application.crosscutting.ProductToCheck;
import hanfak.shopofhan.application.crosscutting.StockRepository;
import hanfak.shopofhan.domain.ProductStock;
import org.slf4j.Logger;

import java.util.Optional;

import static java.lang.String.format;

@SuppressWarnings("Duplicates")
public class ProductCheckByNameUseCase {

    private final StockRepository stockRepository;
    private final Logger logger;

    public ProductCheckByNameUseCase(StockRepository stockRepository, Logger logger) {
        this.stockRepository = stockRepository;
        this.logger = logger;
    }

    public ProductStock checkStock(ProductToCheck productToCheck) {
        logger.info(format("checking stock by Name '%s'", productToCheck.getProductName()));
        Optional<ProductStock> checkStock = stockRepository.checkStockByName(productToCheck.getProductName());

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
