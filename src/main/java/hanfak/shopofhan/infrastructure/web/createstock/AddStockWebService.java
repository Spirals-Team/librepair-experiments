package hanfak.shopofhan.infrastructure.web.createstock;

import hanfak.shopofhan.application.createstock.AddStockUseCase;
import hanfak.shopofhan.domain.AddStockRequest;
import hanfak.shopofhan.infrastructure.web.RenderedContent;

import static java.lang.String.format;

public class AddStockWebService {

    private final AddStockUseCase addStockUseCase;

    public AddStockWebService(AddStockUseCase addStockUseCase) {
        this.addStockUseCase = addStockUseCase;
    }

    public RenderedContent addStock(AddStockRequest request) {
        addStockUseCase.addStock(request);
        return RenderedContent.successfullyAddedStock(format("Product with id, '%s', has been added.Stock of '%s' items for Product with id, '%s', has been added.", request.productId, request.stock.amount, request.productId));
    }
}
