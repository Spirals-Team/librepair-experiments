package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class POHome {

	By buttonLogin = By.xpath("html/body/div[1]/div/div/div[1]/div[10]/div[1]/ul/li[9]/a/span[3]");
	
	WebDriver seleniumDriver;
	
	public POHome(WebDriver _driver) {
		this.seleniumDriver = _driver;
	}
	
	public POHome menuLogin(){
		WebDriverWait wait = new WebDriverWait(seleniumDriver, 5);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(buttonLogin));	
    	element.click();
		return this;
		
	}
	
}
