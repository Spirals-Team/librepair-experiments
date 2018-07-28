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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Yury Matskevich
 */
public class UsersServletTest {
	private Store store = DbStore.getInstance();

	@Before
	//cleaning the store before testing
	public void setUp() {
		for (User user : store.findAll()) {
			store.delete(user.getId());
		}
		User user = new User("name", "login", "email", 0, "pass", 2, 1);
		store.add(user);
	}

	//gets id of a existing user in the store
	private String getId() {
		return Integer.toString(store.findAll().get(0).getId());
	}

	@Test
	public void deleteUserWithCurrentIdTest() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(request.getParameter("id")).thenReturn(getId());
		new UsersServlet().doPost(request, response);
		assertTrue(store.findAll().isEmpty());
	}
}
