package com.lala.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Random;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class SomeSiteTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "src/test/java/com/lala/app/chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "http://automationpractice.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
	@Ignore
	@Test
	public void registerTest() throws Exception {

		Random rand = new Random();
		int  n = rand.nextInt(5000) + 1;

		//login_button
		driver.get(baseUrl + "/index.php");
		driver.findElement(By.cssSelector(".login")).click();

		//create_account
		driver.findElement(By.cssSelector("#email_create")).clear();
		driver.findElement(By.cssSelector("#email_create")).sendKeys("poprawnyemaill" + n + "@lala.com");
		driver.findElement(By.cssSelector("#SubmitCreate")).click();
		Thread.sleep(500);		
		assertEquals(false, driver.findElement(By.cssSelector("#create_account_error")).isDisplayed());

		//gender
		driver.findElement(By.cssSelector("#id_gender2")).click();
		assertEquals(true,driver.findElement(By.cssSelector("#id_gender2")).isSelected());

		//firstname
		driver.findElement(By.cssSelector("#customer_firstname")).clear();
		driver.findElement(By.cssSelector("#customer_firstname")).sendKeys("Magdalena");
		assertNotEquals(0,driver.findElement(By.cssSelector("#customer_firstname")).getAttribute("value").length());
		
		//lastname
		driver.findElement(By.cssSelector("#customer_lastname")).clear();
		driver.findElement(By.cssSelector("#customer_lastname")).sendKeys("Lalalalalala");
		assertNotEquals(0,driver.findElement(By.cssSelector("#customer_lastname")).getAttribute("value").length());
		
		//email
		assertEquals(true,driver.findElement(By.cssSelector("#email")).getAttribute("value").contains(Character.toString('@')));
		
		//password
		driver.findElement(By.cssSelector("#passwd")).clear();
		driver.findElement(By.cssSelector("#passwd")).sendKeys("haslo");
		assertNotEquals(0,driver.findElement(By.cssSelector("#passwd")).getAttribute("value").length());
		
		//firstname
		driver.findElement(By.cssSelector("#firstname")).clear();
		driver.findElement(By.cssSelector("#firstname")).sendKeys("Magdalena");
		assertNotEquals(0,driver.findElement(By.cssSelector("#firstname")).getAttribute("value").length());
		
		//lastname
		driver.findElement(By.cssSelector("#lastname")).clear();
		driver.findElement(By.cssSelector("#lastname")).sendKeys("Lalalalalala");
		assertNotEquals(0,driver.findElement(By.cssSelector("#lastname")).getAttribute("value").length());
		
		//address
		driver.findElement(By.cssSelector("#address1")).clear();
		driver.findElement(By.cssSelector("#address1")).sendKeys("Adres 101");
		assertNotEquals(0,driver.findElement(By.cssSelector("#address1")).getAttribute("value"));
		
		//city
		driver.findElement(By.cssSelector("#city")).clear();
		driver.findElement(By.cssSelector("#city")).sendKeys("Miasto");
		assertNotEquals(0,driver.findElement(By.cssSelector("#city")).getAttribute("value"));
		
		//state
		driver.findElement(By.cssSelector("#id_state")).click();
		driver.findElement(By.cssSelector("#id_state > option:nth-child(13)")).click();
		assertNotEquals(null,driver.findElement(By.cssSelector("#id_state > option:nth-child(13)")).getAttribute("value"));
		
		//postcode
		driver.findElement(By.cssSelector("#postcode")).clear();
		driver.findElement(By.cssSelector("#postcode")).sendKeys("88888");
		//assertEquals(5,driver.findElement(By.cssSelector("#postcode")).getAttribute("value").length());
		Thread.sleep(500);
		assertEquals(true, driver.findElement(By.cssSelector("#create_account_error")).isDisplayed());
		
		//country
		driver.findElement(By.cssSelector("#id_country")).click();
		driver.findElement(By.cssSelector("#id_country > option:nth-child(2)")).click();
		assertNotEquals(null,driver.findElement(By.cssSelector("#id_country > option:nth-child(2)")).getAttribute("value"));
		
		//phone
		driver.findElement(By.cssSelector("#phone_mobile")).clear();
		driver.findElement(By.cssSelector("#phone_mobile")).sendKeys("111222333");
		assertNotEquals(0,driver.findElement(By.cssSelector("#phone_mobile")).getAttribute("value").length());
		
		//alias
		driver.findElement(By.cssSelector("#alias")).clear();
		driver.findElement(By.cssSelector("#alias")).sendKeys("Alias");
		assertNotEquals(0,driver.findElement(By.cssSelector("#alias")).getAttribute("value"));
		
		//submit
		driver.findElement(By.cssSelector("#submitAccount")).click();
		Thread.sleep(500);
		assertEquals(true, driver.findElement(By.cssSelector("div.col-sm-6:nth-child(1) > ul:nth-child(1) > li:nth-child(4) > a:nth-child(1) > span:nth-child(2)")).isDisplayed());
	}

	@Test
	public void registerTestFailed() throws Exception {

		Random rand = new Random();
		int  n = rand.nextInt(5000) + 1;

		//login_button
		driver.get(baseUrl + "/index.php");
		driver.findElement(By.cssSelector(".login")).click();

		//create_account
		driver.findElement(By.cssSelector("#email_create")).clear();
		driver.findElement(By.cssSelector("#email_create")).sendKeys("poprawnyemaill" + n + "@lala.com");
		driver.findElement(By.cssSelector("#SubmitCreate")).click();
		Thread.sleep(500);		
		assertEquals(false, driver.findElement(By.cssSelector("#create_account_error")).isDisplayed());

		//gender
		driver.findElement(By.cssSelector("#id_gender2")).click();
		assertEquals(true,driver.findElement(By.cssSelector("#id_gender2")).isSelected());

		//firstname
		driver.findElement(By.cssSelector("#customer_firstname")).clear();
		driver.findElement(By.cssSelector("#customer_firstname")).sendKeys("Magdalena");
		assertNotEquals(0,driver.findElement(By.cssSelector("#customer_firstname")).getAttribute("value").length());
		
		//lastname
		driver.findElement(By.cssSelector("#customer_lastname")).clear();
		driver.findElement(By.cssSelector("#customer_lastname")).sendKeys("Lalalalalala");
		assertNotEquals(0,driver.findElement(By.cssSelector("#customer_lastname")).getAttribute("value").length());
		
		//email
		assertEquals(true,driver.findElement(By.cssSelector("#email")).getAttribute("value").contains(Character.toString('@')));
		
		//password
		driver.findElement(By.cssSelector("#passwd")).clear();
		driver.findElement(By.cssSelector("#passwd")).sendKeys("haslo");
		assertNotEquals(0,driver.findElement(By.cssSelector("#passwd")).getAttribute("value").length());
		
		//firstname
		driver.findElement(By.cssSelector("#firstname")).clear();
		driver.findElement(By.cssSelector("#firstname")).sendKeys("Magdalena");
		assertNotEquals(0,driver.findElement(By.cssSelector("#firstname")).getAttribute("value").length());
		
		//lastname
		driver.findElement(By.cssSelector("#lastname")).clear();
		driver.findElement(By.cssSelector("#lastname")).sendKeys("Lalalalalala");
		assertNotEquals(0,driver.findElement(By.cssSelector("#lastname")).getAttribute("value").length());
		
		//address
		driver.findElement(By.cssSelector("#address1")).clear();
		driver.findElement(By.cssSelector("#address1")).sendKeys("Adres 101");
		assertNotEquals(0,driver.findElement(By.cssSelector("#address1")).getAttribute("value"));
		
		//city
		driver.findElement(By.cssSelector("#city")).clear();
		driver.findElement(By.cssSelector("#city")).sendKeys("Miasto");
		assertNotEquals(0,driver.findElement(By.cssSelector("#city")).getAttribute("value"));
		
		//state
		driver.findElement(By.cssSelector("#id_state")).click();
		driver.findElement(By.cssSelector("#id_state > option:nth-child(13)")).click();
		assertNotEquals(null,driver.findElement(By.cssSelector("#id_state > option:nth-child(13)")).getAttribute("value"));
		
		//postcode
		driver.findElement(By.cssSelector("#postcode")).clear();
		driver.findElement(By.cssSelector("#postcode")).sendKeys("888888");
		assertNotEquals(5,driver.findElement(By.cssSelector("#postcode")).getAttribute("value").length());		
		
		//country
		driver.findElement(By.cssSelector("#id_country")).click();
		driver.findElement(By.cssSelector("#id_country > option:nth-child(2)")).click();
		assertNotEquals(null,driver.findElement(By.cssSelector("#id_country > option:nth-child(2)")).getAttribute("value"));
		
		//phone
		driver.findElement(By.cssSelector("#phone_mobile")).clear();
		driver.findElement(By.cssSelector("#phone_mobile")).sendKeys("111222333");
		assertNotEquals(0,driver.findElement(By.cssSelector("#phone_mobile")).getAttribute("value").length());
		
		//alias
		driver.findElement(By.cssSelector("#alias")).clear();
		driver.findElement(By.cssSelector("#alias")).sendKeys("Alias");
		assertNotEquals(0,driver.findElement(By.cssSelector("#alias")).getAttribute("value"));
		
		//submit
		driver.findElement(By.cssSelector("#submitAccount")).click();
		Thread.sleep(500);
		assertEquals(true, driver.findElement(By.cssSelector(".alert")).isDisplayed());
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
