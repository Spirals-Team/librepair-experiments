package ru.job4j.crud.servlets;

import org.apache.log4j.Logger;
import ru.job4j.crud.pojo.User;
import ru.job4j.crud.validate.Validate;
import ru.job4j.crud.validate.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Yury Matskevich
 */
public class UsersServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(UsersServlet.class);
	private final Validate valid = ValidateService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("activeUser");
		int role = user.getRole();
		List<User> allUsers = valid.findAll();
		String adminRole = getServletContext().getInitParameter("roleAdmin");
		if (Integer.toString(role).equals(adminRole)) {
			allUsers.remove(user);
			req.setAttribute("users", allUsers);
		}
		req.getRequestDispatcher("/WEB-INF/view/users.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		valid.delete(Integer.parseInt(id));
		resp.sendRedirect("/users");
	}
}
