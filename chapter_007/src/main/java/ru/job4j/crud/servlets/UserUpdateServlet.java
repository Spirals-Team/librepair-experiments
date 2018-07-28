package ru.job4j.crud.servlets;

import org.apache.log4j.Logger;
import ru.job4j.crud.pojo.City;
import ru.job4j.crud.pojo.State;
import ru.job4j.crud.pojo.User;
import ru.job4j.crud.validate.Validate;
import ru.job4j.crud.validate.ValidateService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yury Matskevich
 */
public class UserUpdateServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(UserUpdateServlet.class);
	private final Validate valid = ValidateService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		User user = valid.findById(Integer.parseInt(id));
		@SuppressWarnings("unchecked")
		Map<Integer, State> stateMap = (Map<Integer, State>) getServletContext().getAttribute("stateList");
		List<State> states = new ArrayList<>(stateMap.values());
		int stateId = stateId(user, states);
		user.setStateId(stateId);
		List<City> cities = states.get(stateId).getCities();
		req.setAttribute("user", user);
		req.setAttribute("states", states);
		req.setAttribute("cities", cities);
		RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/view/update.jsp");
		view.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String login = req.getParameter("login");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String role = req.getParameter("roles");
		String city = req.getParameter("cities");
		User uploadingUser = new User(
				Integer.parseInt(id),
				name.equals("") ? null : name,
				login.equals("") ? null : login,
				email.equals("") ? null : email,
				password.equals("") ? null : password,
				role == null ? null : Integer.parseInt(role),
				city.equals("") ? null : Integer.parseInt(city)
		);
		if (valid.update(uploadingUser)) {
			HttpSession session = req.getSession();
			User activeUser = ((User) session.getAttribute("activeUser"));
			if (activeUser.getId() == uploadingUser.getId()) {
				User updatedUser = valid.findById(Integer.parseInt(id));
				session.setAttribute("activeUser", updatedUser);
			}
			resp.sendRedirect("/users");
		} else {
			req.setAttribute("error", "There is such a user with the same login or email");
			doGet(req, resp);
		}
	}

	private int stateId(User user, List<State> states) {
		int id = 0;
		for (State state : states) {
			boolean leave = false;
			id = state.getId();
			for (City city : state.getCities()) {
				if (user.getCityId() == city.getId()) {
					leave = true;
					break;
				}
			}
			if (leave) {
				break;
			}
		}
		return id;
	}
}
