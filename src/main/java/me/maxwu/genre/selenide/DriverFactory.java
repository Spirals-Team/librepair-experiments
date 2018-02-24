package me.maxwu.genre.selenide;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by maxwu on 3/14/17.
 */
public class DriverFactory {
    static Logger logger = LoggerFactory.getLogger(DriverFactory.class.getName());
    static {
        System.setProperty("timeout", "30");
        String arch = System.getProperty("sun.arch.data.model");
        logger.info("Arch=" + arch);
        // Set forceCache to true will always cache the latest version
        System.setProperty("wdm.forceCache", "true");
    }

    public static WebDriver getDefaultDriver(){
        //TODO: check system property "browser"
        return getChromeDriver();
    }

    public static WebDriver getChromeDriver() {
        ChromeOptions options = new ChromeOptions();

        // List of options, http://peter.sh/experiments/chromium-command-line-switches/
        options.addArguments("start-maximized");
        options.addArguments("dns-prefetch-disable");
        options.addArguments("test-type");
        options.addArguments("disable-plugins");
        options.addArguments("disable-extensions");
        options.setExperimentalOption("forceDevToolsScreenshot", true);
        options.addArguments("--loglevel 0");
        options.addArguments("ignore-urlfetcher-cert-requests");

        ChromeDriverManager.getInstance().setup();
        WebDriver driver = new ChromeDriver(options);

        logger.debug("**** Created Web Driver #" + driver.hashCode() + "****");
        return driver;
    }

    public static WebDriver getFirefoxDriver() {
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setCapability("binary", "/Applications/FirefoxNightly.app/Contents/MacOS/firefox-bin");

        FirefoxDriverManager.getInstance().setup();
        WebDriver driver = new FirefoxDriver(caps);

        logger.debug("**** Created Web Driver #" + driver.hashCode() + "****");
        return driver;
    }

    public static WebDriver getPhantomJsDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "/usr/local/bin/phantomjs");

        PhantomJsDriverManager.getInstance().setup();
        WebDriver driver = new PhantomJSDriver(caps);

        logger.debug("**** Created Web Driver #" + driver.hashCode() + "****");
        return driver;
    }


    public static boolean hasQuit(WebDriver driver) {
        return ((driver == null) || (((RemoteWebDriver) driver).getSessionId() == null));
    }

    public static void quitDriver(WebDriver driver) {
        if ((driver != null) && (!hasQuit(driver))) {
            logger.debug("**** Destroy Web Driver #" + driver.hashCode() + "****");
            ((JavascriptExecutor) driver).executeScript("window.stop;");
            driver.quit();
        } else {
            logger.error("Destroy a null or quit driver" + driver.hashCode());

        }
    }
}