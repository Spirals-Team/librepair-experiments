package ru.job4j.servlets;

import ru.job4j.model.pojo.Roles;
import ru.job4j.model.pojo.User;
import ru.job4j.model.validate.Validate;
import ru.job4j.model.validate.ValidateService;
import ru.job4j.servlets.utils.AppUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Yury Matskevich
 */
@WebServlet("/findByAddress")
public class FindByAddressServlet extends HttpServlet {
	private final Validate validate = ValidateService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/findByAddress.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String country = req.getParameter("country");
		String city = req.getParameter("city");
		User user = AppUtils.getLoginedUser(req.getSession());
		String page;
		if (user.getRole().equals(Roles.USER)) {
			page = "userView.jsp";
		} else {
			List<User> users = validate.findByAddress(country, city);
			req.setAttribute("users", users);
			if (user.getRole().equals(Roles.MODERATOR)) {
				page = "moderView.jsp";
			} else {
				page = "adminView.jsp";
			}
		}
		req.getRequestDispatcher(String.format("/WEB-INF/views/%s", page)).forward(req, resp);
	}
}
