package uo.asw.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.Before;
import cucumber.api.java.es.*;
import inciManager.uo.asw.InciManagerE3aApplication;
import uo.asw.selenium.pageobjects.PO_LoginView;
import uo.asw.selenium.pageobjects.PO_View;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { InciManagerE3aApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class DetailSteps {
	
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

	@Dado("^un agente iniciado en sesión$")
	public void un_agente_iniciado_en_sesión() throws Throwable {
		LOGGER.info("un agente de nombre Agente1 contraseña 123456 y "
	    		+ "kind person registrado en el sistema inicia sesion");
		driver.navigate().to(url+"/login");
	    assertEquals(url+"/login", driver.getCurrentUrl());
	    PO_LoginView.fillForm(driver, "Agente1", "123456", "person");
	    assertEquals(url+"/home", driver.getCurrentUrl());
	}

	@Cuando("^el agente intenta ver los detalles de una incidencia$")
	public void el_agente_intenta_ver_los_detalles_de_una_incidencia() throws Throwable {
		LOGGER.info("el agente intenta ver los detalles de una incidencia");
		driver.navigate().to(url+"/incidencia/list");
		assertEquals(url+"/incidencia/list", driver.getCurrentUrl());
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'incidencia/details')]");
		elementos.get(0).click();
	}

	@Entonces("^es redireccionado a la página \"([^\"]*)\"$")
	public void es_redireccionado_a_la_página(String details) throws Throwable {
	    LOGGER.info("es redireccionado a la página "+details);
	    assertThat(driver.getCurrentUrl().contains(details));
	}
}
