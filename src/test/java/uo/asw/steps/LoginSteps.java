package uo.asw.steps;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.es.*;
import inciManager.uo.asw.InciManagerE3aApplication;
import uo.asw.selenium.pageobjects.PO_LoginView;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { InciManagerE3aApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class LoginSteps {
	
	private static final Logger LOGGER = Logger.getLogger(LoginSteps.class);
	private static final int TIMEOUT = 15;

	@Autowired
	protected WebApplicationContext context;
	
	private WebDriver driver;
	
	@Value("${local.server.port:8090}")
	private int port;
	private String url;

	@Before
	public void setUp() {
		driver = new HtmlUnitDriver();
		url = "http://localhost:" + port;
		LOGGER.debug("BaseURL: '" + url + "'");
		driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
	}
	@Dado("^un agente de nombre \"([^\"]*)\" contraseña \"([^\"]*)\" y kind \"([^\"]*)\" no registrado en el sistema$")
	public void un_agente_de_nombre_contraseña_y_kind_no_registrado_en_el_sistema(String username, 
			String password, String kind) throws Throwable {
		LOGGER.info("un agente de nombre "+username+" contraseña "+password+" y "
	    		+ "kind "+kind+" no registrado en el sistema");
	}
	@Dado("^un agente de nombre \"([^\"]*)\" contraseña \"([^\"]*)\" y kind \"([^\"]*)\" registrado en el sistema$")
	public void un_agente_de_nombre_contraseña_y_kind_registrado_en_el_sistema(String username, 
			String password, String kind) throws Throwable {
	    LOGGER.info("un agente de nombre "+username+" contraseña "+password+" y "
	    		+ "kind "+kind+" registrado en el sistema");
	}

	@Dado("^situado en la página \"([^\"]*)\"$")
	public void situado_en_la_página(String login) throws Throwable {
	    LOGGER.info("situado en la pagina "+login);
	    driver.navigate().to(url+login);
	    assertEquals(url+login, driver.getCurrentUrl());
	}

	@Cuando("^hago login con usuario \"([^\"]*)\" y password \"([^\"]*)\" y kind \"([^\"]*)\" introduciendo los datos en los campos$")
	public void hago_login_con_usuario_y_password_y_kind_introduciendo_los_datos_en_los_campos(String username, 
			String password, String kind) throws Throwable {
		LOGGER.info("Hago login con el usuario " +username+" contraseña "+ password + " y kind " + kind);
	    PO_LoginView.fillForm(driver, username, password, kind);
	}

	@Entonces("^soy redireccionada a la página \"([^\"]*)\"$")
	public void soy_redireccionada_a_la_página(String home) throws Throwable {
		LOGGER.info("Soy redireccionado a la página: " + home);
		assertEquals(url+home, driver.getCurrentUrl());
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}

}
