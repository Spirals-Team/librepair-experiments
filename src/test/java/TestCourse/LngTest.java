package TestCourse;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.ChangeLngPage;

public class LngTest extends BaseTest {

    private static ChangeLngPage changeLngPage;

    @BeforeClass
    public void beforeTest() {
        changeLngPage = PageFactory.initElements(driver, ChangeLngPage.class);
    }

    @Test
    public void lngTest() {
        driver.get("https://yandex.ru/");
        changeLngPage.search();

        changeLngPage.searchResult();
        String result = changeLngPage.getResult();
        Assert.assertEquals("Search", result);
    }
}
