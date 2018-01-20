package hanfak.shopofhan.application.crosscutting;

import hanfak.shopofhan.domain.ProductStockList;
import hanfak.shopofhan.domain.product.ProductId;

import java.util.Optional;

public interface ProductStockRepository {
    Optional<ProductStockList> findListOfProductStock(ProductId productId);
}
