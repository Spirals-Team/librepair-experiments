package ru.job4j.servlets;

import ru.job4j.model.pojo.Address;
import ru.job4j.model.pojo.MusicType;
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
import java.io.IOException;

/**
 * @author Yury Matskevich
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
	private final Validate validate = ValidateService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		User user = validate.findById(Integer.parseInt(id));
		req.setAttribute("user", user);
		req.setAttribute("roles", Roles.values());
		req.setAttribute("musics", MusicType.values());
		req.getRequestDispatcher("/WEB-INF/views/update.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String country = req.getParameter("country");
		String city = req.getParameter("city");
		String role = req.getParameter("role");
		String[] music = req.getParameterValues("music");
		User user = new User(
				Integer.parseInt(id),
				login,
				password,
				name,
				new Address(country, city),
				AppUtils.toRoles(role),
				AppUtils.toListMusicType(music)
		);
		if (validate.update(user)) {
			if (AppUtils.getLoginedUser(req.getSession()).getId() == user.getId()) {
				AppUtils.storeLoginedUser(req.getSession(), user);
			}
			resp.sendRedirect("/users");
		} else {
			String errorMessage = "There is such a user with the same login";
			req.setAttribute("errorMessage", errorMessage);
			doGet(req, resp);
		}
	}
}
