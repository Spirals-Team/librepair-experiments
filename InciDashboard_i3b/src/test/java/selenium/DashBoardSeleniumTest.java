package selenium;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "8090" })
public class DashBoardSeleniumTest {
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	private WebDriver driver;
	private String baseUrl;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	    driver = new HtmlUnitDriver();
	    baseUrl = "http://localhost:8090";
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  }

	 @Test
	  public void testPageTitle() throws Exception {
	    driver.get(baseUrl + "/index");
	    System.err.println("Current URL is: " + driver.getCurrentUrl());

	    System.err.println("Page title is: " + driver.getTitle());
	    assertEquals("Dashboard_i1b", driver.getTitle());

		    driver.quit();
	 }

}
