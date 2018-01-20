package hanfak.shopofhan.domain.stock;

import hanfak.shopofhan.domain.crosscutting.ValueType;
import hanfak.shopofhan.domain.product.ProductId;

// TODO add product Id
public class Stock extends ValueType {
    // TODO add wrappers for each
    // TODO Change package
    public final StockAmount amount;
    public final StockId stockId;
    public final StockDescription stockDescription;
    public final ProductId productId;


    private Stock(StockAmount amount, StockId stockId, StockDescription stockDescription, ProductId productId) {
        this.amount = amount;
        this.stockId = stockId;
        this.stockDescription = stockDescription;
        this.productId = productId;
    }

    public static Stock stock(StockAmount amount, StockId stockId, StockDescription stockDescription, ProductId productId) {
        return new Stock(amount, stockId, stockDescription, productId);
    }


}
