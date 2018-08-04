package ru.job4j.crud.listners;

import org.apache.log4j.Logger;
import ru.job4j.crud.load.LoadResource;
import ru.job4j.crud.pojo.City;
import ru.job4j.crud.pojo.State;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yury Matskevich
 */
public class MapOfState implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(MapOfState.class);
	private LoadResource res = new LoadResource("/db.properties");
	private ConnectionDb conn = new ConnectionDb(
			res.getProperty("db.path"),
			res.getProperty("db.username"),
			res.getProperty("db.password")
	);

	/**
	 * Gets all the states from a store
	 * @return <code>List</code> of states
	 */
	private Map<Integer, State> getAllStates() {
		Map<Integer, State> states = new HashMap<>();
		String query = res.getProperty("db.queryGetAllStates");
		try (Connection connection = conn.getConnection();
			 PreparedStatement st = connection.prepareStatement(query);
			 ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt(1);
				states.put(
						id,
						new State(
								id,
								rs.getString(2),
								getAllCitiesByStateId(id)
						)
				);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return states;
	}

	/**
	 * Gets all the cities of a current state
	 * @param id an id of a current state
	 * @return <code>List</code> of cities for current state
	 */
	private List<City> getAllCitiesByStateId(int id) {
		List<City> cities = new ArrayList<>();
		String query = res.getProperty("db.queryGetAllCitiesByStateId");
		try (Connection connection = conn.getConnection();
			 PreparedStatement st = connection.prepareStatement(query)) {
			st.setInt(1, id);
			 try (ResultSet rs = st.executeQuery()) {
				 while (rs.next()) {
					 cities.add(
							 new City(
									 rs.getInt(1),
									 rs.getString(2)
							 )
					 );
				 }
			 }
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return cities;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		Map<Integer, State> states = getAllStates();
		sc.setAttribute("stateList", states);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
