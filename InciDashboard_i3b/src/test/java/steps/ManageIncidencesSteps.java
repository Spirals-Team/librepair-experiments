package steps;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import model.Operator;
import repository.OperatorRepository;

public class ManageIncidencesSteps {
	private WebDriver driver;
	private String baseUrl;


	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8090/index";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	  
	@Given("^The operator is logged on$")
	public void the_operator_is_logged_on() throws Throwable {
		driver.findElement(By.name("id")).click();
	    driver.findElement(By.name("id")).clear();
	    driver.findElement(By.name("id")).sendKeys("operator1");
	    driver.findElement(By.name("pass")).clear();
	    driver.findElement(By.name("pass")).sendKeys("asd");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[2]/div/div/h1")).click();
	}

	@Given("^The operator is in the operator panel$")
	public void the_operator_is_in_the_operator_panel() throws Throwable {
		 assert (driver.getCurrentUrl().equalsIgnoreCase("http://localhost:8090/operatorpanel")) ;
	}

	@Given("^it has assigned incidents$")
	public void there_it_has_assigned_incidents() throws Throwable {
		 driver.findElement(By.xpath("//table[@id='tableAsignedIncidences']/tbody/tr[2]/td")).click();

	}

	@When("^The operator clicks \"([^\"]*)\"$")
	public void the_operator_clicks(String arg1) throws Throwable {
	    driver.findElement(By.xpath("//button[@type='manage']")).click();

	}

	@Then("^The operator sees the incidence manager panel$")
	public void the_operator_sees_the_incidence_manager_panel() throws Throwable {
	    driver.findElement(By.xpath("//div[2]/div/div[2]")).click();

	}

	@Then("^The operator can write a comment$")
	public void the_operator_can_write_a_comment() throws Throwable {
	    driver.findElement(By.id("comments")).sendKeys("asd");

	}
}
