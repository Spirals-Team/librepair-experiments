package app;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.app.MainApplication;



@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class MainControllerTest {

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private RestTemplate template;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getLanding() throws Exception {
		template.getForEntity(base.toString(), String.class);
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Log in")))
		.andExpect(content().string(containsString("Username:")))
		.andExpect(content().string(containsString("Password:")))
		.andExpect(content().string(containsString("Kind:")));
	}

	@Test
	public void loginCorrecto() throws Exception {
		String message = mockMvc.perform(post("/login")
				.param("id", "8")
				.param("password", "lucia123")
				.param("kind","1"))
				.andExpect(status().is3xxRedirection())
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );
	}

	@Test
	public void loginIncorrecto() throws Exception {

		// Campos vacios
		String message = mockMvc.perform(post("/login")
				.param("id", "8")
				.param("password", "lucia123")
				.param("kind",""))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );
		message = mockMvc.perform(post("/login")
				.param("id", "8")
				.param("password", "")
				.param("kind","1"))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );
		message = mockMvc.perform(post("/login")
				.param("id", "")
				.param("password", "lucia123")
				.param("kind","1"))
				.andExpect(content().string(containsString("This field must not be empty")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );

		// Id incorrecto
		message = mockMvc.perform(post("/login")
				.param("id", "7")
				.param("password", "lucia123")
				.param("kind","1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Username not registered in the data base")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );

		// Contraseña incorrecta
		message = mockMvc.perform(post("/login")
				.param("id", "8")
				.param("password", "lucia13")
				.param("kind","1"))
				.andExpect(content().string(containsString("Wrong password")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );

		// Kind code incorrecto
		message = mockMvc.perform(post("/login")
				.param("id", "8")
				.param("password", "lucia123")
				.param("kind","9"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Wrong kind")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );

	}

	@Test
	public void login() throws Exception {

	}

	@Test
	public void send() throws Exception {
		String message = mockMvc.perform(get("/send"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Incident sent correctly!")))
				.andReturn().getResponse().getErrorMessage();
		assertNull( message );
	}

}