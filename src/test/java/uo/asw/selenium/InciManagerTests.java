package uo.asw.selenium;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import uo.asw.selenium.pageobjects.PO_CreateIncidence;
import uo.asw.selenium.pageobjects.PO_LoginView;
import uo.asw.selenium.pageobjects.PO_NavView;
import uo.asw.selenium.pageobjects.PO_View;
import uo.asw.selenium.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InciManagerTests {

	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
		// automáticas)):
		static String PathFirefox = "E:\\USER\\Desktop\\Firefox46.win\\FirefoxPortable.exe";
//		static String PathFirefox = "C:\\Users\\Pelayo Díaz Soto\\Desktop\\Firefox46.win\\FirefoxPortable.exe";
		// En MACOSX (Debe ser la versión 46.0 y desactivar las actualizaciones
		// automáticas):
		// static String PathFirefox =
		// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
		// Común a Windows y a MACOSX
		static WebDriver driver = getDriver(PathFirefox);
		static String URL = "http://localhost:8090";

		public static WebDriver getDriver(String PathFirefox) {
			// Firefox (Versión 46.0) sin geckodriver para Selenium 2.x.
			System.setProperty("webdriver.firefox.bin", PathFirefox);
			WebDriver driver = new FirefoxDriver();
			return driver;
		}

		// Antes de cada prueba se navega al URL home de la aplicaciónn
		@Before
		public void setUp() {
			driver.navigate().to(URL);
		}

		// Después de cada prueba se borran las cookies del navegador y se cierra la sesión
		@After
		public void tearDown() {
			PO_NavView.logout(driver);
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
		
		/**
		 * Comprobamos que al iniciar la aplicación entramos al login
		 */
		@Test
		public void P01_Inicio() {
			PO_LoginView.checkElement(driver, "id", "login");
		}
		
		/**
		 * Comprobamos que al introducir datos incorrectos no se realiza el login
		 */
		@Test
		public void P02_IncorrectLogin() {
			//Comprobamos que estamos en el Login
			PO_LoginView.checkElement(driver, "id", "login");
			//Usuario incorrecto, contraseña correcta, kind correcta
			PO_LoginView.fillForm(driver, "Paco", "123456", "person");
			//Comprobamos que permanecemos en el Login
			PO_LoginView.checkElement(driver, "id", "login");
			//Usuario correcto, contraseña incorrecta, kind correcta
			PO_LoginView.fillForm(driver, "Agente1", "1234567", "person");
			//Comprobamos que permanecemos en el Login
			PO_LoginView.checkElement(driver, "id", "login");
			//Usuario incorrecto, contraseña correcta, kind correcta
			PO_LoginView.fillForm(driver, "Agente1", "123456", "sensor");
			//Comprobamos que permanecemos en el Login
			PO_LoginView.checkElement(driver, "id", "login");
		}
		
		/**
		 * Comprobamos que al introducir datos correctos se realiza el login 
		 * y se redirige a la página adecuada.
		 */
		@Test
		public void P03_CorrectLogin() {
			//Comprobamos que estamos en el Login
			PO_LoginView.checkElement(driver, "id", "login");
			//Usuario correcto, contraseña correcta, kind correcta
			PO_LoginView.fillForm(driver, "Agente1", "123456", "person");
			//Comprobamos que accedemos a la página home
			PO_LoginView.checkElement(driver, "id", "crearInci");
		}
		
		/**
		 * Accedemos a la página de listar las incidencias y comprobamos que
		 * hay.
		 */
		@Test
		public void P04_ViewIncidents() {
			//Comprobamos que estamos en el Login
			PO_LoginView.checkElement(driver, "id", "login");
			//Usuario correcto, contraseña correcta, kind correcta
			PO_LoginView.fillForm(driver, "Agente1", "123456", "person");
			//Comprobamos que accedemos a la página home
			PO_LoginView.checkElement(driver, "id", "crearInci");
			//Accedemos a la página de listar las incidencias
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'incidencia/list')]");
			elementos.get(0).click();
			//Comprobamos que estamos en la página correcta
			PO_NavView.checkElement(driver, "id", "tableInci");
			//Comprobar tamaño de la tabla
			List<WebElement> incidencias = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
					PO_View.getTimeout());
			assertTrue(incidencias.size() > 0);
		}
		
		/**
		 * Creamos de forma válida una nueva incidencia
		 */
		@Test
		public void P05_SendNewIncidence() {
			//Comprobamos que estamos en el Login
			PO_LoginView.checkElement(driver, "id", "login");
			//Usuario correcto, contraseña correcta, kind correcta
			PO_LoginView.fillForm(driver, "Agente1", "123456", "person");
			//Comprobamos que accedemos a la página home
			PO_LoginView.checkElement(driver, "id", "crearInci");
			//Accedemos a la página de listar las incidencias
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'incidencia/list')]");
			elementos.get(0).click();
			//Comprobamos que estamos en la página correcta
			PO_NavView.checkElement(driver, "id", "tableInci");
			//Almacenamos el tamaño de la tabla
			List<WebElement> incidencias = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
					PO_View.getTimeout());
			int tamaño = incidencias.size();
			//Accedemos a la página de listar las incidencias
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'incidencia/create')]");
			elementos.get(0).click();
			//Comprobamos que estamos en la página correcta
			PO_CreateIncidence.checkElement(driver, "id", "sendIncidence");
			//Rellenamos el formulario
			PO_CreateIncidence.fillForm(driver, "Test5", "Incidencia creada con el test5", "VALOR_NO_ASIGNADO", 
					"12", "15", "25", "50", "120");
			//Comprobamos que estamos en la página correcta
			PO_CreateIncidence.checkElement(driver, "id", "tableInci");
			//Contamos el numero de incidencias
			incidencias = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
					PO_View.getTimeout());
			int tamaño2 = incidencias.size();
			//Comprobamos que ha aumentado en uno
			assertTrue(tamaño+1==tamaño2);
		}

}
