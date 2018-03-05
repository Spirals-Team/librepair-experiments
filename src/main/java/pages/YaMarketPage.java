package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class YaMarketPage  extends BasePage{

    private By searchInput = By.cssSelector("#header-search");
    private By searchButton = By.cssSelector(".button2");
    private By searchContentField = By.cssSelector(".n-snippet-card2");
    private By sortPriceField = By.xpath("//a[text()=\"по цене\"]");
    private By sortDefoltField = By.xpath("//a[text()=\"по умолчанию\"]");
    private By sortPriceIconField = By.cssSelector(".n-filter-sorter.n-filter-sorter_state_select");
    private By priceField = By.cssSelector(".n-snippet-card2__main-price .price");


    private final WebDriver driver;
    public YaMarketPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void searchContent (String text) {
        sendKeysByElement(searchInput, text);
        clickByElement(searchButton);
    }

    public Integer getContentResult() {

        return driver.findElements(searchContentField).size();
    }

    public void sortPriceAsc() {
        clickByElement(sortDefoltField);
        clickByElement(sortPriceField);
    }

    public String sortIconResult() {
        return driver.findElement(sortPriceIconField).getAttribute("class");
    }

    public  void sortPriceDesc() {
        clickByElement(sortDefoltField);
        clickByElement(sortPriceField);
        clickByElement(sortPriceField);
    }

    public Boolean sortPriceAscResult () {
        driver.navigate().refresh();
        List<WebElement> priceElements = driver.findElements(priceField);
        ArrayList<Integer> prices = parsePrices(priceElements);
        int i = 1;
        Boolean sortAscResult = true;
        while (i < prices.size()){
            Integer element = prices.get(i-1);
            Integer nextElement = prices.get(i);
            if (element <= nextElement) {
                sortAscResult = true;
            }
            else {sortAscResult = false;
            break;}
            i++;
        }
        return sortAscResult;
    }

    public Boolean sortPriceDescResult () {
        driver.navigate().refresh();
        List<WebElement> elements = driver.findElements(priceField);
        ArrayList<Integer> prices = parsePrices(elements);
        int i = 1;
        Boolean sortDescResult = true;
        while (i < prices.size()){
            Integer element = prices.get(i-1);
            Integer nextElement = prices.get(i);
            if (element >= nextElement) {
                sortDescResult = true;
            }
            else {sortDescResult = false;
                break;}
            i++;
        }
        return sortDescResult;
    }

    protected ArrayList<Integer> parsePrices(List<WebElement> prices) {
        ArrayList<Integer> convertResult = new ArrayList<Integer>();

        prices.forEach(element -> {
            String rawPrice = element.getText();
            rawPrice = rawPrice.replaceAll("[^\\d]", "");
            Integer price = Integer.parseInt(rawPrice);
            convertResult.add(price);
        });
        return convertResult;
    }




}
