package hanfak.shopofhan.domain;

import hanfak.shopofhan.domain.crosscutting.ValueType;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.stock.Stock;

public class AddStockRequest extends ValueType {
    public final Stock stock;
    public final ProductId productId;

    private AddStockRequest(Stock stock, ProductId productId) {
        this.stock = stock;
        this.productId = productId;
    }

    public static AddStockRequest addStockRequest(Stock stock, ProductId productId) {
        return new AddStockRequest(stock, productId);
    }
}
