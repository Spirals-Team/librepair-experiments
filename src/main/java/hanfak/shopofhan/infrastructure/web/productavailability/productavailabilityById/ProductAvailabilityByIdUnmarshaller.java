package hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityById;

import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static hanfak.shopofhan.domain.product.ProductId.productId;


public class ProductAvailabilityByIdUnmarshaller implements Unmarshaller<ProductId> {

    private final Logger logger;

    public ProductAvailabilityByIdUnmarshaller(Logger logger) {
        this.logger = logger;
    }

    // TODO try returning string of JSON in request body
    @Override
    public ProductId unmarshall(HttpServletRequest request) {
        // validate request.getPathInfo() is size > 1
        String contextPath = request.getPathInfo().substring(1);

        try {
            return productId(contextPath);
        } catch (Exception e) {
            logger.error("Bad product id argument");
            throw e;
        }
    }
}
