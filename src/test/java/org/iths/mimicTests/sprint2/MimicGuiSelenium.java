package org.iths.mimicTests.sprint2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class MimicGuiSelenium {

	public static final String USERNAME = "vaffeljarnet";
	public static final String ACCESS_KEY = "4f0cc6b2-1b6a-46f4-81c4-7c1fc020c40d";
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
	 
	private WebDriver webdriver;

	public MimicGuiSelenium() {
		DesiredCapabilities caps = DesiredCapabilities.chrome();
	    caps.setCapability("platform", "Windows 10");
	    caps.setCapability("version", "latest");
	 
	    try {
			webdriver = new RemoteWebDriver(new URL(URL), caps);
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		}
	}
	
	public void openURL(String siteURL) {
		webdriver.get(siteURL);
	}  
	
	public void sendText(String text) {
		WebElement element = webdriver.findElement(By.xpath("/html/body/form/textarea"));
		element.click();
		delay(2000);
		element.sendKeys(text);
	}
	
	public void clickLearn() {
		WebElement element = webdriver.findElement(By.cssSelector("#learn"));
		element.click();
	}
	
	public String getValue() {
		WebElement element = webdriver.findElement(By.cssSelector("body"));
		return element.getText();
	}
	
	public void delay(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
			}
			catch (InterruptedException a) {
			}
	}
	
	public void quitSelenium() {
		webdriver.quit();
	}
	
}
