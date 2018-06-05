package pl.hycom.ip2018.searchengine.selenium.utils;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver getChromeDriver() {
        String chromeDriverPath = getChromeDriverPath();
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox", "--headless");
        return new ChromeDriver(chromeOptions);
    }

    private static String getChromeDriverPath() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return "webdrivers/binaries/windows/googlechrome/64bit/chromedriver.exe";
        }
        if (SystemUtils.IS_OS_LINUX) {
            return "webdrivers/binaries/linux/googlechrome/64bit/chromedriver";
        }
        if (SystemUtils.IS_OS_MAC) {
            return "webdrivers/binaries/mac/googlechrome/64bit/chromedriver";
        }
        throw new RuntimeException("Unsupported operating system.");
    }
}
