package hanfak.shopofhan.application.crosscutting;

import hanfak.shopofhan.domain.ProductStock;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.product.ProductName;
import hanfak.shopofhan.domain.stock.Stock;

import java.util.Optional;

public interface StockRepository {
    Optional<ProductStock> checkStockByName(ProductName productName);

    Optional<ProductStock> checkStockById(ProductId productId);

    void addStock(Stock stock);
}
