package hanfak.shopofhan.domain.product;

import hanfak.shopofhan.domain.crosscutting.SingleValueType;

public class ProductName extends SingleValueType<String> {

    private ProductName(String value) {
        super(value);
    }

    public static ProductName productName(String productName) {
        if (validate(productName)) {
            return new ProductName(productName);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static boolean validate(String productName) {
        return productName.length() > 3;
    }


}
