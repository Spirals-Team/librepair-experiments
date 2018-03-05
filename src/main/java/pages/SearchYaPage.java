package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchYaPage {

        private By searchInput = By.cssSelector("#text");
        private By clickButton = By.cssSelector(".search2__button button");
        private By findResult = By.cssSelector("li[data-cid='0'] a.organic__url");

        private final WebDriver driver;

        public SearchYaPage(WebDriver driver) {
           this.driver = driver;}

        public void search(String text) {
            driver.findElement(searchInput).sendKeys(text);
            driver.findElement(clickButton).click();
        }

        public String getResult() {
            return driver.findElement(findResult).getText();

        }


}
