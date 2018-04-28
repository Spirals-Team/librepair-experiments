package test.steps;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
	public void a_list_of_users(List<String> users) throws Throwable {
		System.out.println("asda");
	}

	@When("^I login with name \"(.+)\" and password \"(.+)\"$")
	public void i_login_with_name_and_password(String name, String password) throws Throwable {
		System.out.println("Login with values..." + name + " - " + password);
	}

	@Then("^I receive a welcome message$")
	public void i_receive_a_welcome_message() throws Throwable {
		System.out.println("Wellcome value received");
	}

	@Given("^there are no users$")
	public void there_are_no_users() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Creating an empty DB");
	}

	@When("^I create a user \"([^\"]*)\" with password \"([^\"]*)\"$")
	public void i_create_a_user_with_password(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Creating user " + arg1 + " with password " + arg2);
	}

	@Then("^The number of users is (\\d+)$")
	public void the_number_of_users_is(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Checking numbers");

	}

}
