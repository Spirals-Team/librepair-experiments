package hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityname;

import hanfak.shopofhan.application.crosscutting.ProductToCheck;
import hanfak.shopofhan.domain.crosscutting.ValueType;
import hanfak.shopofhan.domain.product.ProductName;

import static hanfak.shopofhan.domain.product.ProductName.productName;

public class ProductAvailabilityByNameRequest extends ValueType implements ProductToCheck {
    private final ProductName productName;

    private ProductAvailabilityByNameRequest(ProductName productName) {
        this.productName = productName;
    }

    public static ProductAvailabilityByNameRequest productAvailabilityRequest(String productName) {
        return new ProductAvailabilityByNameRequest(productName(productName));
    }

    @Override
    public ProductName getProductName() {
        return productName;
    }
}
