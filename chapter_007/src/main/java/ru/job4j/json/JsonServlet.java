package ru.job4j.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Yury Matskevich
 */
@WebServlet("/json")
public class JsonServlet extends HttpServlet {
	private final MemoryStore store = MemoryStore.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = req.getParameter("json");
		store.add(mapper.readValue(jsonInString, User.class));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<User> users = store.getAllUsers();
		int size = users.size();
		resp.setContentType("text/json");
		PrintWriter writer = new PrintWriter(resp.getOutputStream());
		writer.append("[");
		for (int i = 0; i < size; i++) {
			writer.append(mapper.writeValueAsString(users.get(i)))
					.append(
							i == (size - 1) ? "]" : ","
					);
		}
		writer.flush();
	}
}
