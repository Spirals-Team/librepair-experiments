package me.maxwu.genre.selenide;

/**
 * Created by maxwu on 3/19/17.
 */
import java.util.List;
import java.util.Set;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class SelenideDriver implements WebDriver {

    private final WebDriver driver;

    public SelenideDriver(WebDriver driver) {
        this.driver = driver;
    }

    public SelenideElement find(By seleniumLocator) {
        return ElementFinder.wrap(this.driver, seleniumLocator, 0);
    }

    public SelenideElement find(String cssSelector) {
        return find(By.cssSelector(cssSelector));
    }

    public SelenideElement find(WebElement pageFactoryElement) {
        return WebElementWrapper.wrap(pageFactoryElement);
    }

    public ElementsCollection findAll(By seleniumLocator) {
        return new ElementsCollection(new BySelectorCollection(this.driver, seleniumLocator));
    }

    public ElementsCollection findAll(String cssSelector) {
        return findAll(By.cssSelector(cssSelector));
    }

    public ElementsCollection findAll(List<WebElement> pageFactoryElements) {
        return new ElementsCollection(new WebElementsCollectionWrapper(pageFactoryElements));
    }

    public void get(String s) {
        this.driver.get(s);
    }

    public String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    /*
      impl. should return findAll(by)
      but currently ElementsCollection impl. does not support this...
     */
    public List<WebElement> findElements(By by) {
        return this.driver.findElements(by);
    }

    public WebElement findElement(By by) {
        return find(by);
    }

    public String getPageSource() {
        return this.driver.getPageSource();
    }

    public void close() {
        this.driver.close();
    }

    public void quit() {
        this.driver.quit();
    }

    public Set<String> getWindowHandles() {
        return this.driver.getWindowHandles();
    }

    public String getWindowHandle() {
        return this.driver.getWindowHandle();
    }

    public TargetLocator switchTo() {
        return this.driver.switchTo();
    }

    public Navigation navigate() {
        return this.driver.navigate();
    }

    public Options manage() {
        return this.driver.manage();
    }
}