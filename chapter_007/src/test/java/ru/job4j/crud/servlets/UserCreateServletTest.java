package ru.job4j.crud.servlets;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.crud.pojo.User;
import ru.job4j.crud.store.DbStore;
import ru.job4j.crud.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Yury Matskevich
 */
public class UserCreateServletTest {
	private Store store = DbStore.getInstance();
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String name;
	private String login;
	private String email;
	private String pass;
	private String role;
	private String city;

	@Before
	//cleaning the store before testing and setting up for a test
	public void setUp() {
		for (User user : store.findAll()) {
			store.delete(user.getId());
		}
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		name = "name";
		login = "login";
		email = "email";
		pass = "password";
		role = "2";
		city = "1";
		when(request.getParameter("name")).thenReturn(name);
		when(request.getParameter("login")).thenReturn(login);
		when(request.getParameter("email")).thenReturn(email);
		when(request.getParameter("password")).thenReturn(pass);
		when(request.getParameter("roles")).thenReturn(role);
		when(request.getParameter("cities")).thenReturn(city);
	}

	@Test
	public void createNewUserTest() throws ServletException, IOException {
		new UserCreateServlet().doPost(request, response);
		User user = store.findAll().get(0); // gets a user wich has been added to the stiore
		assertEquals(name, user.getName());
		assertEquals(login, user.getLogin());
		assertEquals(email, user.getEmail());
		assertEquals(pass, user.getPassword());
		assertEquals(role, user.getRole().toString());
		assertEquals(city, user.getCityId().toString());
	}
}