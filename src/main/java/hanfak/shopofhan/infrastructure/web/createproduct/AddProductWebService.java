package hanfak.shopofhan.infrastructure.web.createproduct;

import hanfak.shopofhan.application.createproduct.AddProductUseCase;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.infrastructure.web.RenderedContent;

import java.sql.SQLException;

import static hanfak.shopofhan.infrastructure.web.RenderedContent.errorContent;
import static hanfak.shopofhan.infrastructure.web.RenderedContent.jsonContent;
import static java.lang.String.format;

public class AddProductWebService {
    private AddProductUseCase addProductUseCase;

    public AddProductWebService(AddProductUseCase addProductUseCase) {
        this.addProductUseCase = addProductUseCase;
    }

    public RenderedContent addProduct(Product product) throws SQLException {
        try {
            addProductUseCase.addProduct(product);
            // TODO change to different rendered content
            return jsonContent(format("Product with id, '%s', has been added.", product.productId));
        } catch (Exception e) {
            return errorContent(format("Product with id, '%s', has not been added, as it already exists.", product.productId));
        }
    }
}
