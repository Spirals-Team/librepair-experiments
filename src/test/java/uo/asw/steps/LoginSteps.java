package uo.asw.steps;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import uo.asw.selenium.pageobjects.PO_LoginView;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = { InciManagerE3aApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration
public class LoginSteps {

	private static final Logger LOGGER = Logger.getLogger(LoginSteps.class);

	@Autowired
	protected WebApplicationContext context;

	private WebDriver driver;

	@Value("${local.server.port:8081}")
	private int port;

	private String url;
	private int timout = 15;

	@Before
	public void setUp() {
		driver = new HtmlUnitDriver();
		url = "http://localhost:" + 8081;
		LOGGER.debug("BaseURL: '" + url + "'");
		driver.manage().timeouts().implicitlyWait(timout, TimeUnit.SECONDS);
	}

	@Dado("^un operario de nombre \"([^\"]*)\" y contraseña \"([^\"]*)\" registrado en el sistema$")
	public void un_operario_de_nombre_contraseña_registrado_en_el_sistema(String arg1, String arg2) throws Throwable {
		LOGGER.info("un operario de nombre " + arg1 + " contraseña " + arg2 + " registrado en el sistema");

	}

	@Cuando("^hago login con usuario \"([^\"]*)\" y password \"([^\"]*)\"  introduciendo los datos en los campos$")
	public void hago_login_con_usuario_y_password_introduciendo_los_datos_en_los_campos(String arg1, String arg2)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		LOGGER.info("Hago login con el usuario " + arg1 + " contraseña " + arg2);
		PO_LoginView.fillForm(driver, arg1, arg2);

	}

	@Dado("^un administrador de nombre \"([^\"]*)\" y contraseña \"([^\"]*)\" registrado en el sistema$")
	public void un_administrador_de_nombre_y_contraseña_registrado_en_el_sistema(String arg1, String arg2)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		LOGGER.info("un administrador de nombre " + arg1 + " contraseña " + arg2 + " registrado en el sistema");
	}

	@Entonces("^soy redireccionado a la página \"([^\"]*)\"$")
	public void soy_redireccionado_a_la_página(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		LOGGER.info("Soy redireccionado a la página: " + arg1);
		assertEquals(url + arg1, driver.getCurrentUrl());

	}

	@Dado("^un operario de nombre \"([^\"]*)\" contraseña \"([^\"]*)\"  no registrado en el sistema$")
	public void un_operario_de_nombre_contraseña_no_registrado_en_el_sistema(String arg1, String arg2)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		LOGGER.info("un operario de nombre " + arg1 + " contraseña " + arg2 + " no registrado en el sistema");
	}

	@Dado("^situado en la página \"([^\"]*)\"$")
	public void situado_en_la_página(String login) throws Throwable {
		LOGGER.info("situado en la pagina " + login);
		driver.navigate().to(url + login);
		assertEquals(url + login, driver.getCurrentUrl());
	}

	@Dado("^un analista de nombre \"([^\"]*)\" y contraseña \"([^\"]*)\" registrado en el sistema$")
	public void un_analista_de_nombre_y_contraseña_registrado_en_el_sistema(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		LOGGER.info("un analista de nombre " + arg1 + " contraseña " + arg2 + " registrado en el sistema");
	}

	@Dado("^un analista de nombre \"([^\"]*)\" contraseña \"([^\"]*)\"  no registrado en el sistema$")
	public void un_analista_de_nombre_contraseña_no_registrado_en_el_sistema(String arg1, String arg2)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		LOGGER.info("un analista de nombre " + arg1 + " contraseña " + arg2 + " no registrado en el sistema");
	}

	@After
	public void tearDown() {
		driver.quit();
	}

}