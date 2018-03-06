package homework;

import net.serenitybdd.core.pages.PageObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by zemlyanova on 19.02.2018.
 */
public class BaseTest extends PageObject{
    protected static WebDriver driver;

    @BeforeClass
    public static void setup() {
/*        if (System.getProperty("os.name").contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        }
        else System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");*/

        //System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDown(){
        driver.quit();
    }
}

