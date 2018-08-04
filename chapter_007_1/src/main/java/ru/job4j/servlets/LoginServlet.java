package ru.job4j.servlets;

import ru.job4j.model.pojo.User;
import ru.job4j.model.validate.Validate;
import ru.job4j.model.validate.ValidateService;
import ru.job4j.servlets.utils.AppUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yury Matskevich
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private final Validate validate = ValidateService.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		User user = validate.isCredential(login, password);
		if (user == null) {
			String errorMessage = "Invalid login or password";
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
		} else {
			AppUtils.storeLoginedUser(req.getSession(), user);
			resp.sendRedirect("/users");
		}
	}
}
