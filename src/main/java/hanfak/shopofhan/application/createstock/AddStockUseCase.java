package hanfak.shopofhan.application.createstock;

import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.application.crosscutting.StockRepository;
import hanfak.shopofhan.domain.AddStockRequest;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.stock.Stock;

import java.util.Optional;

import static hanfak.shopofhan.domain.stock.Stock.stock;

public class AddStockUseCase {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    // tODO add logger

    public AddStockUseCase(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

// TODO change AddSTockRequest to Stock object
    public void addStock(AddStockRequest request) {
        Optional<Product> product = productRepository.checkProductById(request.productId);
        if (product.isPresent()) {
            Stock stock = stock(request.stock.amount, request.stock.stockId, request.stock.stockDescription, product.get().productId);
             stockRepository.addStock(stock);
        }
    }
}
