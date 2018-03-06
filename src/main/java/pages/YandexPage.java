package pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by zemlyanova on 19.02.2018.
 */
public class YandexPage extends PageObject {

    public YandexPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    String symbol = "\u20BD";

    @FindBy(xpath = "//*[@class='input__control input__input']")
    private WebElement searchField;

    @FindBy(css = ".search2__button")
    private WebElement searchButton;

    @FindBy(xpath = "(//*[@class='organic__title-wrapper typo typo_text_l typo_line_m'])[1]")
    private WebElement searchResult;

    @FindBy(xpath = "//*[@data-statlog='head.settings']")
    private WebElement settingsButton;

    @FindBy(xpath = "//*[@data-statlog='head.settings.other']")
    private WebElement portalButton;

    @FindBy(xpath = "//*[@data-statlog='tabs.lang']")
    private WebElement languageButton;

    @FindBy(xpath = "//*[@type='button']")
    private WebElement languageSelectButton;

    @FindBy(xpath = "//*[@class='select__text'][text()='English']")
    private WebElement textSelect;

    @FindBy(xpath = "//*[@type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//*[@class='options__header']")
    private WebElement switchLanguageResult;

    @FindBy(css = ".input__control")
    private WebElement searchInput;

    private By productCount = By.cssSelector("div[data-id]");

    private By priceLink = By.cssSelector(".n-filter-sorter.i-bem.n-filter-sorter_js_inited:nth-child(3)");

    private By ascendingFilter = By.cssSelector(".n-filter-sorter.i-bem.n-filter-sorter_js_inited.n-filter-sorter_sort_asc.n-filter-sorter_state_select");

    private By descendingFilter = By.cssSelector(".n-filter-sorter.i-bem.n-filter-sorter_js_inited.n-filter-sorter_sort_desc.n-filter-sorter_state_select");

    private By price = By.cssSelector(".n-snippet-card2__main-price");

    public void searchWeather(String text){
        searchField.sendKeys(text);
        searchButton.click();
    }

    public String getSearchResult(){
        return searchResult.getText();
    }

    public void switchLanguage(){
        settingsButton.click();
        portalButton.click();
        languageButton.click();
        languageSelectButton.click();
        textSelect.click();
        saveButton.click();
        settingsButton.click();
        portalButton.click();
    }

    public String getSwitchLanguageResult(){
        return switchLanguageResult.getText();
    }

    public void enterNameProductAndSearch(String text){
        searchInput.sendKeys(text);
        searchButton.click();
    }

    public int getProductCount(){
        return driver.findElements(productCount).size();
    }

    public void clickPriceLink(){
        driver.findElement(priceLink).click();
    }

    public String getResultByAscending(){
        waitABit(2000);
        return driver.findElement(ascendingFilter).getAttribute("class");
    }

    public String getResultByDescending(String index){
        if (driver.findElement(ascendingFilter).getAttribute("class").contains(index))
        {
            driver.findElement(priceLink).click();
        }
        waitABit(2000);
        return driver.findElement(descendingFilter).getAttribute("class");
    }

    public String returnSortingFilter() {
        String sortingFilter = null;
        String firstPrice = driver.findElements(price).get(0).getText().replace(symbol, "").replace(" ", "");
        if (Integer.parseInt(firstPrice) == maxPrice()) {
            sortingFilter = "desc";
        }
        else if (Integer.parseInt(firstPrice) == minPrice()) {
            sortingFilter = "asc";
        }
        return sortingFilter;
    }

    private int maxPrice() {
        List<WebElement> prices = driver.findElements(price);
        int max = Integer.parseInt(prices.get(0).getText().replace(symbol, "").replace(" ", ""));
        for (int i = 0; i < prices.size(); i++) {
            int price = Integer.parseInt(prices.get(i).getText().replace(symbol, "").replace(" ", ""));
            if (max < price) {
                max = price;
            }
        }
        return max;
    }

    private int minPrice() {
        List<WebElement> prices = driver.findElements(price);
        int min = Integer.parseInt(prices.get(0).getText().replace(symbol, "").replace(" ", ""));
        for (int i = 0; i < prices.size(); i++) {
            int price = Integer.parseInt(prices.get(i).getText().replace(symbol, "").replace(" ", ""));
            if (min > price) {
                min = price;
            }
        }
        return min;
    }
}
