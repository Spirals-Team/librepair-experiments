package com.agiles.UI.Cucumber;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide.*;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.ChromeDriverManager;

import static com.codeborne.selenide.Selenide.*;

import java.net.URI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Condition.*;




public class WinTheGameStepDefinitions {
	
	
//	@Before
//	public static void setupClass() {
//	    ChromeDriverManager.getInstance().setup();
//	}
	
	
	@Given("^an open browser with AhorcadoWeb/index\\.jsp$")
	public void an_open_browser_with_AhorcadoWeb_index_jsp() throws Throwable {
//		ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox");
//        System.setProperty("webdriver.chrome.driver","/usr/bin/google-chrome");
//        WebDriver browser = new ChromeDriver(options);
        
//		Configuration.browser = "chrome";
//	    Configuration.startMaximized = false;
//		System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("start-maximized"); // open Browser in maximized mode
//		options.addArguments("disable-infobars"); // disabling infobars
//		options.addArguments("--disable-extensions"); // disabling extensions
//		options.addArguments("--disable-gpu"); // applicable to windows os only
//		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
//		options.addArguments("--no-sandbox"); // Bypass OS security model
//		WebDriver driver = new ChromeDriver(options);
//		driver.get("/AhorcadoWeb/index.jsp");
//		System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");
		
		
//		System.setProperty("selenide.browser", "Chrome");
        
	    open("http://localhost:8080/AhorcadoWeb/index.jsp");
//	    Thread.sleep(40000);
	
//	    driver = new ChromeDriver();
//		driver.navigate().to("http://localhost:8080/AhorcadoWeb/index.jsp");
	}
	
	@When("^I click the letter \"([^\"]*)\"$")
	public void i_click_the_letter(String letter) throws Throwable {
		if(letter.equals("ñ")){
			letter="enie";
		}
//		$("#letterh").should(exist);
		$("#letter"+letter).click();
//		WebElement addTrainer = (new WebDriverWait(driver, 7)).until(ExpectedConditions.elementToBeClickable($("#letter"+letter)));
//		addTrainer.click();
//
	}
	

	@Then("^a message saying \"([^\"]*)\" should appear$")
	public void a_message_saying_should_appear(String winTit) throws Throwable {
		$(".ganador-tit").shouldHave(text(winTit));
	}

	@Then("^the keyboard should disappear$")
	public void the_keyboard_should_disappear() throws Throwable {
		$(".alphabets").shouldNot(exist);
	}

	@Then("^the remaining errors should disappear$")
	public void the_remaining_errors_should_disappear() throws Throwable {
		$(".errorestit").shouldNot(exist);
		$(".errores").shouldNot(exist);
	}

	@Then("^the entered letters should disappear$")
	public void the_entered_letters_should_disappear() throws Throwable {
		$(".letrasUsadasTit").shouldNot(exist);
		$(".letrasUsadas").shouldNot(exist);
	}


}
