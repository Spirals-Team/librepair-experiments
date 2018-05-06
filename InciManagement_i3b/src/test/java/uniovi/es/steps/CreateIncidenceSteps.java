package uniovi.es.steps;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cucumber.api.java.After;
import cucumber.api.java.Before;
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
public class CreateIncidenceSteps {
	  private CucumberUtils utils = new CucumberUtils();
	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();


//	  @When(value = "^the user completes the log in")
	  public void testUntitledTestCase() throws Exception {
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
	  
//	  @Then(value = "^fill the text fields and clicks Notify Incidence")
	  public void testUntitledTestCase2() throws Exception 
	  {
		  driver.findElement(By.id("key")).click();
		    driver.findElement(By.id("key")).clear();
		    driver.findElement(By.id("key")).sendKeys("f1");
		    driver.findElement(By.id("value")).clear();
		    driver.findElement(By.id("value")).sendKeys("f2");
		    driver.findElement(By.id("addButton")).click();
		    driver.findElement(By.xpath("//div[@id='incident-form']/div/form[2]/div")).click();
		    driver.findElement(By.xpath("//div[@id='incident-form']/div/form[2]/div")).click();
		    driver.findElement(By.id("title")).click();
		    driver.findElement(By.id("title")).clear();
		    driver.findElement(By.id("title")).sendKeys("titulo");
		    driver.findElement(By.id("message")).clear();
		    driver.findElement(By.id("message")).sendKeys("desc");
		    driver.findElement(By.id("aditionalInfo")).clear();
		    driver.findElement(By.id("aditionalInfo")).sendKeys("adinfo");
		    driver.findElement(By.id("tags")).clear();
		    driver.findElement(By.id("tags")).sendKeys("tag,tags,tagggs");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
	  }

//	  @Then(value = "^an incidence is created (and redirected to a list view of them)")
	  public void testUntitledTestCase3() throws Exception 
	  {
		  //esta en la pagina de lista
		  utils.textoPresentePagina(driver, "title");
		  utils.textoPresentePagina(driver, "Comments(from the operators)");		  
		  //sale el incidente
		  utils.textoPresentePagina(driver, "titulo");
		  utils.textoPresentePagina(driver, "desc");
	  }

}
