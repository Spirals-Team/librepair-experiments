package hanfak.shopofhan.domain;

import hanfak.shopofhan.domain.crosscutting.ValueType;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.stock.Stock;

import java.util.List;

public class ProductStockList extends ValueType {
    // TODO extract Product object
    public final Product product;
    public final List<Stock> stock;

    private ProductStockList(Product product, List<Stock> stock) {
        this.product = product;
        this.stock = stock;
    }

    public static ProductStockList productStockList(Product product, List<Stock> stock) {
        return new ProductStockList(product, stock);
    }
}
