package jmri.web;

import cucumber.api.java8.En;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Cucumber step definitions for Web Server Acceptance tests.
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class WebServerAcceptanceSteps implements En {

    private EventFiringWebDriver webDriver;

    String[] firefoxtags = {"@webtest", "@firefox"};
    String[] chrometags = {"@webtest", "@chrome"};
    String[] tags = {"@webtest"};

    public WebServerAcceptanceSteps(jmri.InstanceManager instance) {

        Before(chrometags, () -> {
            WebDriverManager.getInstance(ChromeDriver.class).setup();
        });
        Before(firefoxtags, () -> {
            WebDriverManager.getInstance(FirefoxDriver.class).setup();
        });
        Given("^I am using firefox$", () -> {
            if (GraphicsEnvironment.isHeadless()) {
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                firefoxBinary.addCommandLineOptions("--headless");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                webDriver = new EventFiringWebDriver(new FirefoxDriver(firefoxOptions));
            } else {
                webDriver = new EventFiringWebDriver(new FirefoxDriver());
            }
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        });

        Given("^I am using chrome$", () -> {
            if (GraphicsEnvironment.isHeadless()) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                webDriver = new EventFiringWebDriver(new ChromeDriver(chromeOptions));
            } else {
                webDriver = new EventFiringWebDriver(new ChromeDriver());
            }
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        });

        When("^I ask for the url (.*)$", (String url) -> {
            webDriver.get(url);
        });

        Then("^a page with title (.*) is returned$", (String pageTitle) -> {
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            wait.until((WebDriver driver) -> {
                // Derived from code by Jeff Vincent at
                // https://stackoverflow.com/a/26812386/176160
                String script = "if (typeof window != 'undefined' && window.document) { return window.document.readyState; } else { return 'notready'; }";
                Boolean result;
                try {
                    result = ((JavascriptExecutor) driver).executeScript(script).equals("complete");
                } catch (Exception ex) {
                    result = Boolean.FALSE;
                }
                return result;
            });
            Assert.assertEquals("Page Title", pageTitle, webDriver.getTitle());
        });

        After(tags, () -> {
            if (webDriver != null) {
                webDriver.close();
            }
        });

    }
}
