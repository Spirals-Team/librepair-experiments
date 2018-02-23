package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class POLogin extends POUniversity {

	By user = By.xpath("html/body/font/form/center/table/tbody/tr[1]/td[2]/p/input");
	//By user = By.name("usuario");
	By pass = By.xpath("html/body/font/form/center/table/tbody/tr[2]/td[2]/p/input");
	By loginButton = By.name("Ingresar");

//	WebDriver seleniumDriver;

	public POLogin(WebDriver _driver) {
		org.testng.Reporter.log("<p>Se corrobora que se esta en la pagina de login... ");
		this.seleniumDriver = _driver;

//		try {
//			WebDriverWait wait = new WebDriverWait(seleniumDriver, 50);
//			WebElement containerElement = wait.until(ExpectedConditions.presenceOfElementLocated(user));
//			if (containerElement.isDisplayed()) {
//				org.testng.Reporter.log("OK");
//			} else {
//				throw new IllegalStateException("<p>ERROR - No se encontro el elemento que corrobora que se esta en la pagina que permite loguearse");
//			}
//		} catch (Exception ex) {
//			throw new IllegalStateException("<p>ERROR - No se encontro la pagina que permite loguearse");
//		}
	}

	public POLogin typeUserName(String _userName) {
		seleniumDriver.findElement(user).sendKeys(_userName);
		return this;
	}

	public POLogin typePassword(String _pass) {
		seleniumDriver.findElement(pass).sendKeys(_pass);
		return this;
	}

	public POLogin loginAs(String _user, String _pass) {
		typeUserName(_user);
		typePassword(_pass);
		return submitLogin();
	}

	public POLogin submitLogin() {
		seleniumDriver.findElement(loginButton).click();
		return this;
	}
}
