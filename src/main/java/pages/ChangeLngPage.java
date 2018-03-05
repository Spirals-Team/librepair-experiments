package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangeLngPage {
    private By menuField = By.cssSelector(".link.dropdown-menu__switcher") ;
    private By settingText = By.xpath("//a[text()='Настройки портала']");
    private By languageText = By.cssSelector("a[data-statlog='tabs.lang']");
    private By languageSelect = By.cssSelector(".select__button");
    private By languageInput = By.xpath("(//*[text()='English'])[2]");
    private By saveButton = By.cssSelector(".button_theme_action");
    private By resultText = By.cssSelector("a[data-statlog='tabs.search']");

    private final WebDriver driver;

    public ChangeLngPage(WebDriver driver) {
        this.driver = driver;}

    public void changeLanguage() {
        driver.findElement(menuField).click();
        driver.findElement(settingText).click();
        driver.findElement(languageText).click();
        driver.findElement(languageSelect).click();
        driver.findElement(languageInput).click();
        driver.findElement(saveButton).click();
    }

    public void changeLanguageResult() {
        driver.findElement(menuField).click();
        driver.findElement(settingText).click();
    }

    public String getResult() {
        return driver.findElement(resultText).getText();
    }
}
