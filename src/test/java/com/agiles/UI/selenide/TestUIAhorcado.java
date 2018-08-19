package com.agiles.UI.selenide;

import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.ChromeDriverManager;

import static com.codeborne.selenide.Selenide.*;

import java.io.IOException;
import java.net.URL;

import static com.codeborne.selenide.Condition.*;


public class TestUIAhorcado {
	
//	@Before
//	public static void setupClass() {
//		openBrowser();
////	    ChromeDriverManager.getInstance().setup();
//	}
	
//	@After
//	public static void close(){
//		WebDriverRunner.closeWebDriver();
//		WebDriverRunner.getWebDriver().quit();
//	}
	
	@Test
	public void ingresarUnaSolaLetra() {
		
		 openBrowser();
		 $("#letterh").click();
		 $("#letrasUsadas").shouldHave(text("h"));
		 $(".letras-palabra").shouldHave(text("h _ _ _ "));
		 $(".errores").shouldHave(text("4"));
		 $(".ganador-section").shouldNot(exist);
		 $(".perdedor-section").shouldNot(exist);
		 $(".hangman").isImage();
		 $(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman1.png"));
	}
	
	@Test
	public void juegoGanado() {
		
		 openBrowser();
		 $("#letterh").click();
		 $("#lettero").click();
		 $("#letterl").click();
		 $("#lettera").click();
		 $("#letrasUsadas").shouldNot(exist);
		 $(".letras-palabra").shouldHave(text("h o l a "));
		 $(".errores").shouldNot(exist);
		 $(".alphabets").shouldNot(exist);
		 $(".ganador-tit").shouldHave(text("Te Salvaste!"));
		 $(".perdedor-section").shouldNot(exist);
		 $(".hangman").isImage();
		 $(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman1.png"));
	}
	
	@Test
	public void juegoPerdido() {
		
 		 openBrowser();
		 $("#letterg").click();
		 $("#letterr").click();
		 $("#lettere").click();
		 $("#lettert").click();
		 $("#letterz").click();
		 $("#letrasUsadas").shouldNot(exist);
		 $(".letras-palabra").shouldHave(text("_ _ _ _ "));
		 $(".errores").shouldNot(exist);
		 $(".alphabets").shouldNot(exist);
		 $(".ganador-section").shouldNot(exist);
		 $(".perdedor-tit").shouldHave(text("Perdiste!"));
		 $(".palabraera").shouldHave(text("La palabra era: hola"));
		 $(".hangman").isImage();
		 $(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman6.png"));
	}
	
	@Test
	public void juegoGanadoConCincoLetras(){
 		 openBrowser();
		 $("#letterh").click();
		 $("#letterr").click();
		 $("#letterl").click();
		 $("#lettera").click();
		 $("#lettero").click();
		 $("#letrasUsadas").shouldNot(exist);
		 $(".letras-palabra").shouldHave(text("h o l a "));
		 $(".errores").shouldNot(exist);
		 $(".alphabets").shouldNot(exist);
		 $(".ganador-tit").shouldHave(text("Te Salvaste!"));
		 $(".perdedor-section").shouldNot(exist);
		 $(".hangman").isImage();
		 $(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman2.png"));
	
	}
	
	
	
	public static void openBrowser(){
		
		try{			
			//FirefoxOptions options = new FirefoxOptions();
			ChromeOptions options = new ChromeOptions();
			options.setHeadless(true);
			// set some options
			DesiredCapabilities dc = DesiredCapabilities.chrome();
			dc.setPlatform(Platform.LINUX);
			dc.setCapability(ChromeOptions.CAPABILITY, options);
			//URL url = new URL("http://localhost:4444/wd/hub");
			//WebDriver driver = new RemoteWebDriver(url, dc);
//			DesiredCapabilities cap=new DesiredCapabilities();
//			cap.setBrowserName("chrome");
//			cap.setPlatform(Platform.LINUX);
//			
//			ChromeOptions options=new ChromeOptions();
//			options.merge(cap);
			
//			System.setProperty("selenide.browser", "Chrome");
//			String urlToRemoteWD = "http://localhost:4444/wd/hub";
			
			//System.setProperty("webdriver.chrome.driver", "/Users/jsanchez/Downloads/google-chrome");
			//System.setProperty("webdriver.gecko.driver","/Users/jsanchez/Downloads/geckodriver");
			
			System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");
			//System.setProperty("webdriver.gecko.driver","/home/circleci/repo/geckodriver");
			//System.setProperty("webdriver.gecko.driver","/home/travis/build/andreslopapa/AhorcadoWeb2/geckodriver");
			//System.setProperty("webdriver.gecko.driver","/builds/joacosanchez95/ahorcado-web/geckodriver");
			
			System.setProperty("selenide.browser", "chrome");
//			ChromeDriverManager.getInstance().setup();
			
//			Configuration.remote=urlToRemoteWD;
			
//			WebDriver driver =new RemoteWebDriver(new URL(urlToRemoteWD),options);
//			WebDriverRunner.setWebDriver(driver);
			open("/AhorcadoWeb/index.jsp");
		    Selenide.clearBrowserCookies();
			
			
//		System.setProperty("webdriver.chrome.driver","/home/lopa/Descargas/chromedriver");
//		System.setProperty("selenide.browser", "Chrome");
		
		
		
//		WebDriverRunner.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
//		SeleniumServer _server = new SeleniumServer(); 
//		_server.boot(); 
//		_server.start();
//	    open("http://localhost:4444/wd/hub/AhorcadoWeb/index.jsp");
		
	    /*when you launch selenium it creates a 
	     * temporary profile and when you close it,selenium deletes it*/}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
}


