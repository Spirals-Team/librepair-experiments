import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertNotNull;

public class Testing {
  @Test
  public void test() throws Exception {
    //System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver/chromedriver");
    final ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setBinary("/tmp/chromedriver/chromedriver");
    chromeOptions.addArguments("--headless");
    chromeOptions.addArguments("--disable-gpu");

    final DesiredCapabilities dc = new DesiredCapabilities();
    dc.setJavascriptEnabled(true);
    dc.setCapability(
            ChromeOptions.CAPABILITY, chromeOptions
    );
    WebDriver browser = new ChromeDriver(dc);
    browser.get("http://localhost:8084");
    Thread.sleep(2000);
    WebElement downloadButton = browser.findElement(By.id("download-button"));
    assertNotNull(downloadButton);
  }
}
