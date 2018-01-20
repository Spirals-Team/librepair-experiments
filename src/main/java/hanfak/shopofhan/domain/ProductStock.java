package hanfak.shopofhan.domain;

import hanfak.shopofhan.domain.crosscutting.ValueType;
import hanfak.shopofhan.domain.product.ProductName;

public class ProductStock extends ValueType {
    public final ProductName productName;
    public final Integer amountInStock;

    private ProductStock(ProductName productName, Integer amountInStock) {
        this.productName = productName;
        this.amountInStock = amountInStock;
    }

    public static ProductStock productStock(ProductName productName, Integer amountInStock){
        return new ProductStock(productName, amountInStock);
    }
}
