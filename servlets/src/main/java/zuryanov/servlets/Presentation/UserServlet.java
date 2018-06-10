package zuryanov.servlets.Presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zuryanov.servlets.Logic.Validate;
import zuryanov.servlets.Logic.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserServlet.class);

    private final Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String[] userArray = req.getParameterValues("user");
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            if ("user".equals(parameterNames.nextElement())) {
                PrintWriter writer = new PrintWriter(resp.getOutputStream());
                for (String user : userArray) {
                    writer.append("" + logic.add(user));
                }
                writer.append("List of users:");
                writer.flush();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        DispatchAction d = new DispatchAction();
        d.init(req);
    }

    class DispatchAction {
        private final Map<String, Function<HttpServletRequest, Boolean>> dispatch = new HashMap<>();

        public Function<HttpServletRequest, Boolean> toAdd(HttpServletRequest req) {
            return userServlet -> {
                logic.add(req.getParameter("name"));
                return true;
            };
        }

        public Function<HttpServletRequest, Boolean> toUpdate(HttpServletRequest req) {
            return userServlet -> {
                logic.update(Integer.parseInt(req.getParameter("id")), req.getParameter("name"));
                return true;
            };
        }

        public Function<HttpServletRequest, Boolean> toDelete(HttpServletRequest req) {
            return userServlet -> {
                logic.delete(Integer.parseInt(req.getParameter("id")));
                return true;
            };
        }

        public DispatchAction init(HttpServletRequest req) {
            String action = req.getParameter("action");
            this.load("add", toAdd(req));
            this.load("update", toUpdate(req));
            this.load("delete", toDelete(req));
            return this;
        }

        public void load(String action, Function<HttpServletRequest, Boolean> handle) {
            this.dispatch.put(action, handle);
        }

    }
}



