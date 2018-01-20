package hanfak.shopofhan.infrastructure.web.productavailability.productstockcheckbyavailability;

import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.infrastructure.web.RenderedContent;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductStockCheckByIdServlet extends HttpServlet {

    private final Unmarshaller<ProductId> unmarshaller;
    private final ProductStockCheckByIdWebService productStockCheckByIdWebService;

    public ProductStockCheckByIdServlet(Unmarshaller<ProductId> unmarshaller, ProductStockCheckByIdWebService productStockCheckByIdWebService) {
        this.unmarshaller = unmarshaller;
        this.productStockCheckByIdWebService = productStockCheckByIdWebService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RenderedContent renderedContent = productStockCheckByIdWebService.requestProductCheck(unmarshaller.unmarshall(request));
        renderedContent.render(response);
    }
}
