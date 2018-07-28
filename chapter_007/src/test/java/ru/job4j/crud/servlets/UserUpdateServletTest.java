package ru.job4j.crud.servlets;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
//import org.springframework.mock.web.MockHttpSession;
import ru.job4j.crud.pojo.User;
import ru.job4j.crud.store.DbStore;
import ru.job4j.crud.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Yury Matskevich
 */
public class UserUpdateServletTest {
	private Store store = DbStore.getInstance();
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private User activeUser;
	private String id;
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
		store.add(new User("name", "login", "email", 0, "pass", 2, 1));
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		activeUser = getActiveUser();
		id = getId();
		name = "newName";
		login = "newLogin";
		email = "newEmail";
		pass = "newPassword";
		role = "2";
		city = "1";
		when(request.getParameter("id")).thenReturn(id);
		when(request.getParameter("name")).thenReturn(name);
		when(request.getParameter("login")).thenReturn(login);
		when(request.getParameter("email")).thenReturn(email);
		when(request.getParameter("password")).thenReturn(pass);
		when(request.getParameter("roles")).thenReturn(role);
		when(request.getParameter("cities")).thenReturn(city);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("activeUser")).thenReturn(activeUser);
	}

	//gets id of a existing user in the store
	private String getId() {
		return Integer.toString(store.findAll().get(0).getId());
	}

	//get an active user for setting up a session attribute
	private User getActiveUser() {
		return store.findAll().get(0);
	}

	@Test
	public void updateUserTest() throws ServletException, IOException {
		new UserUpdateServlet().doPost(request, response);
		User user = store.findAll().get(0); // gets a user wich has been added to the stiore
		assertEquals(name, user.getName());
		assertEquals(login, user.getLogin());
		assertEquals(email, user.getEmail());
		assertEquals(pass, user.getPassword());
		assertEquals(role, user.getRole().toString());
		assertEquals(city, user.getCityId().toString());
	}
}