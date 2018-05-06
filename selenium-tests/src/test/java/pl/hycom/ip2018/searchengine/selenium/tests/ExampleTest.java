package pl.hycom.ip2018.searchengine.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pl.hycom.ip2018.searchengine.selenium.utils.DriverFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class ExampleTest {

    private WebDriver driver = DriverFactory.getChromeDriver();

    @Before
    public void before() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        driver.get("https://www.p.lodz.pl/pl");
        driver.findElement(By.id("NewsletterButton")).click();
        boolean isSomeElementPresent = driver.findElements(By.id("block-block-27")).size() > 0;
        assertTrue(isSomeElementPresent);
    }

    @After
    public void after() {
        driver.quit();
    }
}
