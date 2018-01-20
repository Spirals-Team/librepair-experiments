package hanfak.shopofhan.infrastructure.web.removeproduct;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveProductServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO pass request to usecase, which goes to repository and deletes it, then sends back confirmation and
        // response sends below
        response.setStatus(200);
        response.getWriter().write("Product Deleted " + request.getPathInfo().substring(1));
    }
}
