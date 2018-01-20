package hanfak.shopofhan.infrastructure.web.productavailability.productstockcheckbyavailability;

import hanfak.shopofhan.application.productavailability.ProductStockCheckByIdUseCase;
import hanfak.shopofhan.domain.ProductStockList;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.infrastructure.web.Marshaller;
import hanfak.shopofhan.infrastructure.web.RenderedContent;

import static hanfak.shopofhan.infrastructure.web.RenderedContent.errorContent;
import static hanfak.shopofhan.infrastructure.web.RenderedContent.jsonContent;
import static java.lang.String.format;

public class ProductStockCheckByIdWebService {
    private final ProductStockCheckByIdUseCase productStockCheckByIdUseCase;
    private final Marshaller<ProductStockList> marshaller;

    public ProductStockCheckByIdWebService(Marshaller<ProductStockList> marshaller, ProductStockCheckByIdUseCase productStockCheckByIdUseCase) {
        this.marshaller = marshaller;
        this.productStockCheckByIdUseCase = productStockCheckByIdUseCase;
    }

    public RenderedContent requestProductCheck(ProductId productId) {
        try {
            ProductStockList productStockList = productStockCheckByIdUseCase.checkStock(productId);
            return jsonContent(marshaller.marshall(productStockList));
        } catch (IllegalStateException e) {
            return errorContent(format("Product '%s' is not stocked %s", productId.value, e.toString()));
        }
    }
}
