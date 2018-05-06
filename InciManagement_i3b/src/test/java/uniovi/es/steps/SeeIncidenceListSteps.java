package uniovi.es.steps;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uniovi.es.Application;
import uniovi.es.steps.utils.CucumberUtils;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = Application.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes=Application.class, loader=SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class SeeIncidenceListSteps {
      private CucumberUtils utils = new CucumberUtils();
	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @When(value = "the user log in")
	  public void testUntitledTestCasete() throws Exception {
		driver.get("http://165.227.236.206:8080/");
		driver.findElement(By.name("login")).clear();
		driver.findElement(By.name("login")).sendKeys("");
		driver.findElement(By.name("login")).click();
		driver.findElement(By.name("login")).clear();
		driver.findElement(By.name("login")).sendKeys("11111111A");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456");
		driver.findElement(By.name("kind")).clear();
		driver.findElement(By.name("kind")).sendKeys("1");
		driver.findElement(By.xpath("//button[@type='submit']")).click();  
	  }
	  
	  @Then(value = "click the option of review my incidence")
	  public void testUntitledTestCase2() throws Exception 
	  {
		  utils.textoPresentePagina(driver, "Create new incident");
		  driver.findElement(By.linkText("Review my incidents")).click();
	  } 
	  
	  @Then(value = "see the list of incidences")
	  public void testUntitledTestCase3() throws Exception 
	  {
		  utils.textoPresentePagina(driver, "title");
		  utils.textoPresentePagina(driver, "Comments(from the operators)");		  
	  }


}
