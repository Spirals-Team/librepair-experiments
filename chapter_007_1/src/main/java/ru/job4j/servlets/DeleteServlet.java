package ru.job4j.servlets;

import ru.job4j.model.validate.Validate;
import ru.job4j.model.validate.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yury Matskevich
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	private final Validate validate = ValidateService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		validate.delete(Integer.parseInt(id));
		resp.sendRedirect("/users");
	}
}
