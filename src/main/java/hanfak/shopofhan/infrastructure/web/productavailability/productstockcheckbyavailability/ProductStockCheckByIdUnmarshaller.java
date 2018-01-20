package hanfak.shopofhan.infrastructure.web.productavailability.productstockcheckbyavailability;

import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;

import javax.servlet.http.HttpServletRequest;

public class ProductStockCheckByIdUnmarshaller implements Unmarshaller<ProductId> {

    //TODO validation and test
    @Override
    public ProductId unmarshall(HttpServletRequest request) {
        return ProductId.productId(request.getPathInfo().substring(1));
    }
}
