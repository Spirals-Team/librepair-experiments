package zuryanov.servlets.presentation;

import zuryanov.servlets.logic.Validate;
import zuryanov.servlets.logic.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UsersController extends HttpServlet {

    private final Validate logic = ValidateService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", logic.findAll());
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String button = req.getParameter("submit");
        if ("edit".equals(button)) {
            resp.sendRedirect(String.format("%s/edit?id=%s", req.getContextPath(), ValidateService.getInstance().findByName(req.getParameter("user"))));
        } else if ("delete".equals(button)) {
            logic.delete(ValidateService.getInstance().findByName(req.getParameter("user")));
            resp.sendRedirect(String.format("%s/list", req.getContextPath()));
        } else if ("change role".equals(button)) {
            HttpSession session = req.getSession();
            session.setAttribute("rolesession", req.getParameter("rolechange"));
            resp.sendRedirect(String.format("%s/list", req.getContextPath()));
        }
    }
}
