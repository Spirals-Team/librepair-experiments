package ru.job4j.servlets;

import ru.job4j.model.pojo.Address;
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
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
	private final Validate validate = ValidateService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String country = req.getParameter("country");
		String city = req.getParameter("city");
		String role = req.getParameter("role");
		String[] music = req.getParameterValues("music");
		if (validate.add(
				new User(
						login,
						password,
						name,
						new Address(country, city),
						AppUtils.toRoles(role),
						AppUtils.toListMusicType(music))
		)) {
			resp.sendRedirect("/users");
		} else {
			String errorMessage = "There is such a user with the same login or email";
			req.setAttribute("errorMessage", errorMessage);
			doGet(req, resp);
		}
	}
}
