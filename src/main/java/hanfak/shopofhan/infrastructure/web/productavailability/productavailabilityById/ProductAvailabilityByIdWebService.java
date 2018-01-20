package hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityById;

import hanfak.shopofhan.application.productavailability.ProductCheckByIdUseCase;
import hanfak.shopofhan.domain.ProductStock;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.infrastructure.web.Marshaller;
import hanfak.shopofhan.infrastructure.web.RenderedContent;

import static hanfak.shopofhan.infrastructure.web.RenderedContent.errorContent;
import static hanfak.shopofhan.infrastructure.web.RenderedContent.jsonContent;
import static java.lang.String.format;

public class ProductAvailabilityByIdWebService {

    private final ProductCheckByIdUseCase productCheckByIdUseCase;
    private final Marshaller<ProductStock> marshaller;

    public ProductAvailabilityByIdWebService(ProductCheckByIdUseCase productCheckByIdUseCase, Marshaller<ProductStock> marshaller) {
        this.productCheckByIdUseCase = productCheckByIdUseCase;
        this.marshaller = marshaller;
    }

    public RenderedContent requestProductCheck(ProductId productId) {
        try {
            ProductStock productStock = productCheckByIdUseCase.checkStock(productId);
            return jsonContent(marshaller.marshall(productStock));
        } catch (IllegalStateException e) {
            return errorContent(format("Product '%s' is not stocked %s", productId.value, e.toString()));
        }
    }

}
