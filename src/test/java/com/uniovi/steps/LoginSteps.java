package com.uniovi.steps;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.pageObjects.PO_LoginView;
import com.uniovi.pageObjects.PO_View;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginSteps {

	static String PathFirefox = "Firefox46.win\\FirefoxPortable.exe";

	static WebDriver driver = getDriver(PathFirefox);
	static String URL_INCI = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() throws Exception {
		driver.navigate().to(URL_INCI);
	}

	@BeforeClass
	static public void begin() {
	}

	@AfterClass
	static public void end() {
		driver.quit();
	}

	@After
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();
	}

	@Given("^a list of users:$")
	public void a_list_of_users(List<Agent> users) throws Throwable {
		for (Agent u : users) {
			System.out.println("Inserting user..." + u.name + " - " + u.password);
		}
	}

	@When("^I login with name \"(.+)\" and password \"(.+)\"$")
	public void i_login_with_name_and_password(String name, String password) throws Throwable {
		PO_LoginView.identificar(driver);
		PO_LoginView.fillForm(driver, name, password);
	}

	@Then("^I am redirected to the incident form$")
	public void i_receive_a_welcome_message() throws Throwable {
		PO_View.checkElement(driver, "text", "Datos de la incidencia");
	}

	public static class Agent {
		private String name;
		private String password;
	}

}
