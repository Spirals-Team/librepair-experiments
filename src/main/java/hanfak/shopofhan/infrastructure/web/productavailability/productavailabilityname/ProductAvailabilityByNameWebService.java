package hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityname;

import hanfak.shopofhan.application.productavailability.ProductCheckByNameUseCase;
import hanfak.shopofhan.domain.ProductStock;
import hanfak.shopofhan.infrastructure.web.Marshaller;
import hanfak.shopofhan.infrastructure.web.RenderedContent;

import static hanfak.shopofhan.infrastructure.web.RenderedContent.errorContent;
import static hanfak.shopofhan.infrastructure.web.RenderedContent.jsonContent;
import static java.lang.String.format;

public class ProductAvailabilityByNameWebService {

    private final ProductCheckByNameUseCase productCheckByNameUseCase;
    private final Marshaller<ProductStock> marshaller;

    public ProductAvailabilityByNameWebService(ProductCheckByNameUseCase productCheckByNameUseCase, Marshaller<ProductStock> marshaller) {
        this.productCheckByNameUseCase = productCheckByNameUseCase;
        this.marshaller = marshaller;
    }

    public RenderedContent requestProductCheck(ProductAvailabilityByNameRequest productAvailabilityRequest) {
        try {
            ProductStock productStock = productCheckByNameUseCase.checkStock(productAvailabilityRequest);
            return jsonContent(marshaller.marshall(productStock));
        } catch (IllegalStateException e) {
            return errorContent(format("Product '%s' is not stocked %s", productAvailabilityRequest.getProductName().value, e.toString()));
        }
    }

}
