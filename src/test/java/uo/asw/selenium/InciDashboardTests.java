package uo.asw.selenium;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import uo.asw.selenium.pageobjects.PO_LoginView;
import uo.asw.selenium.pageobjects.PO_NavView;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InciDashboardTests {
//
//	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
//		// automáticas)):
////		static String PathFirefox = "E:\\USER\\Desktop\\Firefox46.win\\FirefoxPortable.exe";
////		static WebDriver driver = getDriver(PathFirefox);
//		static WebDriver driver = new HtmlUnitDriver();
//		static String URL = "http://localhost:8081";
//
//		// Antes de cada prueba se navega al URL home de la aplicaciónn
//		@Before
//		public void setUp() {
//			driver.navigate().to(URL);
//		}
//
//		// Después de cada prueba se borran las cookies del navegador y se cierra la sesión
//		@After
//		public void tearDown() {
//			PO_NavView.logout(driver);
//			driver.manage().deleteAllCookies();
//		}
//
//		// Antes de la primera prueba
//		@BeforeClass
//		static public void begin() {
//		}
//
//		// Al finalizar la última prueba
//		@AfterClass
//		static public void end() {
//			// Cerramos el navegador al finalizar las pruebas
//			driver.quit();
//		} 
//		
//		/**
//		 * Comprobamos que al iniciar la aplicación entramos al login
//		 */
//		@Test
//		public void P01_Inicio() {
//			PO_LoginView.checkElement(driver, "id", "username");
//		}
//		
//		/**
//		 * Comprobamos que al introducir datos incorrectos no se realiza el login
//		 */
//		@Test
//		public void P02_IncorrectLogin() {
//			//Comprobamos que estamos en el Login
//			PO_LoginView.checkElement(driver, "id", "username");
//			//Usuario incorrecto, contraseña correcta
//			PO_LoginView.fillForm(driver, "Paco", "123456");
//			//Comprobamos que permanecemos en el Login
//			PO_LoginView.checkElement(driver, "id", "username");
//			//Usuario correcto, contraseña incorrecta
//			PO_LoginView.fillForm(driver, "Id5", "1234567");
//			//Comprobamos que permanecemos en el Login
//			PO_LoginView.checkElement(driver, "id", "username");
//		}
//		
////		/**
////		 * Comprobamos que al introducir datos correctos se realiza el login 
////		 * y se redirige a la página adecuada.
////		 */
////		@Test
////		public void P03_CorrectLoginOperario() {
////			//Aumentamos el tiempo de espera tolerable
////			PO_LoginView.setTimeout(30);
////			//Comprobamos que estamos en el Login
////			PO_LoginView.checkElement(driver, "id", "username");
////			//Usuario correcto, contraseña correcta
////			PO_LoginView.fillForm(driver, "Id5", "123456");
////			//Comprobamos que accedemos a la página home
////			PO_LoginView.checkElement(driver, "id", "OperarioDashboard");
////		}

}
