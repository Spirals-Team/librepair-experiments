package asw.selenium;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import asw.selenium.pageobjects.PO_LoginView;
import asw.selenium.pageobjects.PO_View;

//Ordenamos las pruebas por el nombre del método
@SuppressWarnings("unused")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class  DashBoardTests{
	
	  static WebDriver driver;
	  static String baseUrl;
	  static StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
	    driver = new HtmlUnitDriver();
	    baseUrl = "http://localhost:8090";
	    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	  }

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// 1 Inicio de sesión con datos de administrador validos.
	@Test
	public void test01() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pablo", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
	}
	
	// 2 Inicio de sesión con datos de operario validos.
	@Test
	public void test02() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pepe", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
	}
	
	// LAS SIGUIENTES 6 PRUEBAS PRUEBAN EL CORRECTO ACCESO DE LOS DISTINTOS ROLES
	// A SUS RESPECTIVOS LINKS.
		
	// 3.1 probamos que al operario pueda acceder a su lista de incidencias
	@Test
	public void test03part1() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pepe", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
		driver.get(baseUrl + "/incidencias/list");
		PO_View.checkElement(driver, "text", "Gestionar");
	}
	
/*	// 3.2 probamos que al operario pueda acceder a estadisticas
	@Test
	public void test03part2() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pepe", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
		driver.get(baseUrl + "/estadisticas");
		PO_View.checkElement(driver, "text", "Estadisticas");
	}*/
	
	// 3.3 probamos que al operario no pueda acceder a los campos criticos
	@Test
	public void test03part4() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pepe", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
		driver.get(baseUrl + "/campos");
		PO_View.checkElement(driver, "text", "Access");
	}
	
	// 4.1 probamos que al administrador si pueda acceder a los campos criticos
	@Test
	public void test04part1() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pablo", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
		driver.get(baseUrl + "/campos");
		PO_View.checkElement(driver, "text", "Campos");
	}
	
/*	// 4.2 probamos que al administrador si pueda acceder a las estadisticas
	@Test
	public void test04part2() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pablo", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
		driver.get(baseUrl + "/estadisticas");
		PO_View.checkElement(driver, "text", "Estadisticas");
	}*/
	
	// 4.3 probamos que el usuario no pueda acceder a la lista de incidencias personal
	@Test
	public void test04part3() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pablo", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
		driver.get(baseUrl + "/incidencias/list");
		PO_View.checkElement(driver, "text", "Access");
	}
	
	
	
//	// 5 Comprobamos que Pepe pueda acceder a modificar la primera de sus incidencias
	@Test
	public void test05() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pepe", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
    	driver.get(baseUrl + "/incidencias/list");
    	PO_View.checkElement(driver, "free", "//td[contains(text(), 'Incidencia 1')]/following-sibling::"
			+ "*/a[contains(@id, 'gestionar')]").get(0).click();
    	PO_View.checkElement(driver, "text", "Gestionar");
    	 
	}
	
	
//	// 6 Comprobamos que Pepe pueda acceder a ver los detalles la primera de sus incidencias
	@Test
	public void test06() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pepe", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
    	driver.get(baseUrl + "/incidencias/list");
    	PO_View.checkElement(driver, "free", "//td[contains(text(), 'Incidencia 1')]/following-sibling::"
			+ "*/a[contains(@id, 'detalles')]").get(0).click();
    	PO_View.checkElement(driver, "text", "Detalles");
    	 
	}
	
//	// 7 Comprobamos que un administrador pueda acceder a modificar el primero de los campos criticos
	@Test
	public void test07() {
		driver.get(baseUrl + "/");
		PO_LoginView.fillForm(driver, "Pablo", "123456");
		PO_View.checkElement(driver, "text", "Bienvenido");
    	driver.get(baseUrl + "/campos");
    	PO_View.checkElement(driver, "free", "//td[contains(text(), 'Temperatura')]/following-sibling::"
			+ "*/a[contains(@id, 'modificar')]").get(0).click();
    	PO_View.checkElement(driver, "text", "Modificar");
    	 
	}

}
