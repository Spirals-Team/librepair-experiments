// generated at 2018-06-04 06:56.56 from main/resources/templates/PageObject.vm 

package net.atf4j.generated;

import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.atf4j.webdriver.page.AbstractPageObject;

public class ExamplePageObject extends AbstractPageObject {

	@FindBy(id = "id")
	protected WebElement findById;
	
	@FindBy(name = "name")
	protected WebElement findByName;
	
	@FindBy(className = "className")
	protected WebElement findByClassName;
	
	@FindBy(linkText = "linkText")
	protected WebElement findByLinkText;
	
	@FindBy(partialLinkText = "partialLinkText")
	protected WebElement findByPartialLinkText;
	
	@FindBy(xpath = "//a[contains(text(), 'ATF4J')]")
	protected WebElement findByXpath;
	
	@FindBy(css = ".")
	protected WebElement findByCss;
	

	public ExamplePageObject() {
		super();
	}

	public ExamplePageObject(WebDriver webDriver) {
		super(webDriver);
	}

	public ExamplePageObject open() {
		super.open("http://atf4j.net");
		return this;
	}

	public ExamplePageObject verify() {
		super.verifyPageTitle("Example Page");
		return this;
	}

	// Navigation Elements.
	public ExamplePageObject navTofindByLinkText() {
		assertNotNull(findByLinkText);
		findByLinkText.click();
		return this;
	}

	public ExamplePageObject navTofindByPartialLinkText() {
		assertNotNull(findByPartialLinkText);
		findByPartialLinkText.click();
		return this;
	}


	// Input Elements
	public ExamplePageObject inputTofindById(CharSequence inputText) {
		assertNotNull(findById);
		findById.sendKeys(inputText);
		return this;
	}


	// Content Elements
}
