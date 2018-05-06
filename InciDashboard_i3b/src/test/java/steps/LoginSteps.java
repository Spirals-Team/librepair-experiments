package steps;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import main.Application;

@ContextConfiguration(classes=Application.class, loader=SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class LoginSteps {
	  private WebDriver driver;
	  private String baseUrl;


	  @Before
	  public void setUp() throws Exception {
	    driver = new FirefoxDriver();
	    baseUrl = "http://localhost:8090/index";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
  
  @Value("${local.server.port}")
  protected int port;

  @When("^operator introduces username \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void operator_introduces_username_and_password(String username, String password) throws Throwable {
	    driver.get(baseUrl);
		driver.findElement(By.name("id")).click();
	    driver.findElement(By.name("id")).clear();
	    driver.findElement(By.name("id")).sendKeys(username);
	    driver.findElement(By.name("pass")).clear();
	    driver.findElement(By.name("pass")).sendKeys(password);
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[2]/div/div/h1")).click();
  }


  @Then("^operator sees opeatorpanel$")
  public void operator_sees_opeatorpanel() throws Throwable {
	  assert (driver.getCurrentUrl().equalsIgnoreCase("http://localhost:8090/operatorpanel")) ;
		driver.close();
  }
}