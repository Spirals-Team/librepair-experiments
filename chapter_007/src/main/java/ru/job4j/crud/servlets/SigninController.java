package ru.job4j.crud.servlets;

import org.apache.log4j.Logger;
import ru.job4j.crud.validate.Validate;
import ru.job4j.crud.validate.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Yury Matskevich
 */
public class SigninController extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(SigninController.class);
	private final Validate valid = ValidateService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/view/signin.jsp")
				.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		Integer id = valid.isCredential(login, password);
		if (id != null) {
			HttpSession session = req.getSession();
			session.setAttribute("activeUser", valid.findById(id));
			resp.sendRedirect("/users");
		} else {
			req.setAttribute("error", "A Login and/or a password is invalid");
			doGet(req, resp);
		}
	}
}
