package TestCourse;





import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.SearchYaPage;

public class YaTest extends BaseTest {

        private static SearchYaPage searchYaPage;

        @BeforeClass
        public void beforeTest()  {
            searchYaPage = PageFactory.initElements(driver, SearchYaPage.class);
        }


        @Test
        public void searchTest() {
            driver.get("https://ya.ru/");
            searchYaPage.search("Погода пенза");

            String result = searchYaPage.getResult();

            Assert.assertEquals("Погода в Пензе", result);
        }

}
