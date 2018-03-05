package TestCourse;



import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.YaMarketPage;

public class MarketTest extends BaseTest {

    private static YaMarketPage yaMarketPage;

    @BeforeClass
    public void beforeTest() {
        yaMarketPage = PageFactory.initElements(driver, YaMarketPage.class);
    }

    @Test
    public void marketTest() {
        driver.get("https://market.yandex.ru/");
        yaMarketPage.searchContent("холодильник");
        int result = yaMarketPage.getContentResult();
        Assert.assertEquals(48, result);
    }

    @Test
    public void sortPriceAscIconTest() {
        driver.get("https://market.yandex.ru/");
        yaMarketPage.searchContent("холодильник");
        yaMarketPage.sortPriceAsc();
        Assert.assertTrue( yaMarketPage.sortIconResult().contains("asc"),"true");
    }

    @Test
    public void sortPriceAscTest ()  {
        driver.get("https://market.yandex.ru/");
        yaMarketPage.searchContent("холодильник");
        yaMarketPage.sortPriceAsc();
        boolean sortAscResult = yaMarketPage.sortPriceAscResult();
        Assert.assertEquals(true, sortAscResult);
    }

    @Test
    public void sortPriceDescIconTest() {
        driver.get("https://market.yandex.ru/");
        yaMarketPage.searchContent("холодильник");
        yaMarketPage.sortPriceDesc();
        Assert.assertTrue(yaMarketPage.sortIconResult().contains("desc"), "true");
    }

    @Test
    public void sortPriceDescTest() {
        driver.get("https://market.yandex.ru/");
        yaMarketPage.searchContent("холодильник");
        yaMarketPage.sortPriceDesc();
        boolean sortDescResult = yaMarketPage.sortPriceDescResult();
        Assert.assertEquals(true, sortDescResult);
    }
}
