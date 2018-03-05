package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    private final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElement(By locator){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void clickByElement(By locator){
        waitForElement(locator);
        driver.findElement(locator).click();
    }

    public void sendKeysByElement(By locator, String text){
        waitForElement(locator);
        driver.findElement(locator).sendKeys(text);
    }
}
