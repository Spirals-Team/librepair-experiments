// generated at 2018-06-04 06:56.56 from main/resources/templates/PageObject.vm 

package net.atf4j.generated;

import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.atf4j.webdriver.page.AbstractPageObject;

public class LandingPage extends AbstractPageObject {


	public LandingPage() {
		super();
	}

	public LandingPage(WebDriver webDriver) {
		super(webDriver);
	}

	public LandingPage open() {
		super.open("http://atf4j.net");
		return this;
	}

	public LandingPage verify() {
		super.verifyPageTitle("Landing Page");
		return this;
	}

	// Navigation Elements.

	// Input Elements

	// Content Elements
}
