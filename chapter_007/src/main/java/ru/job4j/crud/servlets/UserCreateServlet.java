package ru.job4j.crud.servlets;

import org.apache.log4j.Logger;
import ru.job4j.crud.pojo.State;
import ru.job4j.crud.pojo.User;
import ru.job4j.crud.validate.Validate;
import ru.job4j.crud.validate.ValidateService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yury Matskevich
 */
public class UserCreateServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(UserCreateServlet.class);
	private final Validate valid = ValidateService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<Integer, State> stateMap = (Map<Integer, State>) getServletContext().getAttribute("stateList");
		List<State> stateList = new ArrayList<>(stateMap.values());
		req.setAttribute("states", stateList);
		RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/view/create.jsp");
		view.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		String login = req.getParameter("login");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String role = req.getParameter("roles");
		String city = req.getParameter("cities");
		if (valid.add(
				new User(
						name.equals("") ? null : name,
						login.equals("") ? null : login,
						email.equals("") ? null : email,
						System.currentTimeMillis(),
						password.equals("") ? null : password,
						Integer.parseInt(
								role == null
										? req.getServletContext().getInitParameter("roleUser")
										: role
						),
						city.equals("") ? null : Integer.parseInt(city)
				)
		)) {
			resp.sendRedirect("/users");
		} else {
			req.setAttribute("error", "There is such a user with the same login or email");
			doGet(req, resp);
		}
	}
}
