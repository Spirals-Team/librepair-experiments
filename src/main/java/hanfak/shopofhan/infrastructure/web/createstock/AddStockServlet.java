package hanfak.shopofhan.infrastructure.web.createstock;


import hanfak.shopofhan.domain.AddStockRequest;
import hanfak.shopofhan.infrastructure.web.RenderedContent;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddStockServlet extends HttpServlet {

    private final Unmarshaller<AddStockRequest> unmarshaller;
    private final AddStockWebService addStockWebService;

    public AddStockServlet(Unmarshaller<AddStockRequest> unmarshaller, AddStockWebService addStockWebService) {
        this.unmarshaller = unmarshaller;
        this.addStockWebService = addStockWebService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RenderedContent renderedContent = addStockWebService.addStock(unmarshaller.unmarshall(request));
        renderedContent.render(response);
    }
}
