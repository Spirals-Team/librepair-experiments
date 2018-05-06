package selenium;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import main.Application;
import model.Operator;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "8090" })
public class LoginDashboardSeleniumTest {
  @Autowired
  private WebApplicationContext context;
  private MockMvc mvc;
	@Autowired
	private repository.OperatorRepository operatorRepository;
  
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	  mvc = MockMvcBuilders.webAppContextSetup(context).build();
    driver = new HtmlUnitDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


		if (operatorRepository.count() == 0)
		{
		Operator o1 = new Operator("operator1", "asd");
		Operator o2 = new Operator("operator2", "asd");
		Operator o3 = new Operator("operator3", "asd");
		Operator o4 = new Operator("operator4", "asd");
		operatorRepository.insert(o1);
		operatorRepository.insert(o2);
		operatorRepository.insert(o3);
		operatorRepository.insert(o4);
	}
  }

  @Test //meter operator en la database
  public void testLogin() throws Exception {
	
    driver.get("http://localhost:8090/index");
    driver.findElement(By.name("id")).click();
    driver.findElement(By.name("id")).clear();
    driver.findElement(By.name("id")).sendKeys("operator1");
    driver.findElement(By.name("pass")).clear();
    driver.findElement(By.name("pass")).sendKeys("asd");
//    driver.findElement(By.xpath("//button[@type='submit']")).click();
//    driver.findElement(By.xpath("//div[2]/div/div/h1")).click();
//    driver.findElement(By.xpath("//h1")).click();
//    driver.findElement(By.linkText("Log out")).click();
//    driver.findElement(By.xpath("//h1")).click();
//    driver.findElement(By.name("id")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
