package hanfak.shopofhan.domain.product;

import hanfak.shopofhan.domain.crosscutting.ValueType;

/**
 * Created by hanfak on 11/09/2017.
 */
public class Product extends ValueType {
    public final ProductDescription productDescription;
    public final ProductId productId;
    public final ProductName productName;

    private Product(ProductDescription productDescription, ProductId productId, ProductName productName) {
        this.productDescription = productDescription;
        this.productId = productId;
        this.productName = productName;
    }

    public static Product product(ProductDescription productDescription, ProductId productId, ProductName productName) {
        return new Product(productDescription, productId, productName);
    }
}
