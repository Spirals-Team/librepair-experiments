package hanfak.shopofhan.domain.product;

import hanfak.shopofhan.domain.crosscutting.SingleValueType;

public class ProductId extends SingleValueType<String> {
    private ProductId(String value) {
        super(value);
    }

    public static ProductId productId(String productId) {
        if (validate(productId)) {
            return new ProductId(productId);
        } else {
            throw new IllegalArgumentException("illegal product id: contains a space or more than 10 characters");
        }
    }

    private static boolean validate(String productId) {
        return productId.length() < 10 && !productId.contains(" ");
    }
}
