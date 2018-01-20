package hanfak.shopofhan.infrastructure.web.createproduct;

import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.infrastructure.web.RenderedContent;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AddProductServlet extends HttpServlet {
    private final Unmarshaller<Product> unmarshaller;
    private final AddProductWebService addProductWebService;

    public AddProductServlet(Unmarshaller<Product> unmarshaller, AddProductWebService addProductWebService) {
        this.unmarshaller = unmarshaller;
        this.addProductWebService = addProductWebService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO will be cleaned up when testing duplicate
        RenderedContent renderedContent = null;
        try {
            renderedContent = addProductWebService.addProduct(unmarshaller.unmarshall(request));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        renderedContent.render(response);
    }
}
