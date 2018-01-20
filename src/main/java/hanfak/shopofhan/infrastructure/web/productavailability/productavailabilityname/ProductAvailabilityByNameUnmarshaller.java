package hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityname;

import hanfak.shopofhan.infrastructure.web.Unmarshaller;

import javax.servlet.http.HttpServletRequest;


public class ProductAvailabilityByNameUnmarshaller implements Unmarshaller<ProductAvailabilityByNameRequest> {

    @Override
    public ProductAvailabilityByNameRequest unmarshall(HttpServletRequest request)  {
        // validate request.getPathInfo() is size > 1
        String contextPath = request.getPathInfo().substring(1);

        return ProductAvailabilityByNameRequest.productAvailabilityRequest(extractName(contextPath));
    }

    private String extractName(String contextPath) {
        return contextPath.split("/")[0];
    }


}
