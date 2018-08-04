package ru.job4j.crud.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ru.job4j.crud.pojo.City;
import ru.job4j.crud.pojo.State;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yury Matskevich
 */
public class JsonServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(JsonServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String param = req.getParameter("stateId");
		Integer stateId = param.equals("") ? null : Integer.parseInt(param);
		@SuppressWarnings("unchecked")
		Map<Integer, State> states =
				(Map<Integer, State>) getServletContext().getAttribute("stateList");
		List<City> cityList;
		if (stateId == null) {
			cityList = new ArrayList<>(); //returns an empty ArrayList for creating an empty select
		} else {
			State state = states.get(stateId);
			cityList = state.getCities();
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(cityList);
		resp.setContentType("text/json");
		PrintWriter writer = new PrintWriter(resp.getOutputStream());
		writer.append(jsonInString);
		writer.flush();
	}
}
