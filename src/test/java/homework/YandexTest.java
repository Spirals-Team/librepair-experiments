package homework;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.YandexPage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by zemlyanova on 19.02.2018.
 */
public class YandexTest extends BaseTest{

    private static YandexPage yandexPage;

    @BeforeClass
    public static void beforeTest() throws InterruptedException {
        yandexPage = PageFactory.initElements(driver, YandexPage.class);
    }

    @Test
    public void weather_search(){
        driver.get("https://ya.ru/");
        yandexPage.searchWeather("погода пенза");
        assertEquals("Погода в Пензе", yandexPage.getSearchResult());
    }

    @Test
    public void checking_the_language_selection() {
        driver.get("http://yandex.ru");
        yandexPage.switchLanguage();
        assertEquals("Search settings", yandexPage.getSwitchLanguageResult());
    }

    @Test
    public void search_for_product_in_the_market_yandex_and_check_product_count(){
        int productCount = 48;
        driver.get("https://market.yandex.ru");
        yandexPage.enterNameProductAndSearch("Планшет");
        assertThat(yandexPage.getProductCount()).describedAs("На странице отображается НЕ 12 товаров").isEqualTo(productCount);
    }

    @Test
    public void check_sorting_product_by_ascending(){
        driver.get("https://market.yandex.ru");
        yandexPage.enterNameProductAndSearch("Планшет");
        yandexPage.clickPriceLink();
        assertTrue("Значок сортировки отображается не верно - НЕ по возрастанию", yandexPage.getResultByAscending().contains("asc"));
        assertThat(yandexPage.returnSortingFilter()).describedAs("Товары отсортированы НЕ по возрастанию").isEqualTo("asc");
    }

    @Test
    public void check_sorting_product_by_descending(){
        driver.get("https://market.yandex.ru");
        yandexPage.enterNameProductAndSearch("Планшет");
        yandexPage.clickPriceLink();
        assertTrue("Значок сортировки отображается не верно - НЕ по убыванию", yandexPage.getResultByDescending("asc").contains("desc"));
        assertThat(yandexPage.returnSortingFilter()).describedAs("Товары отсортированы НЕ по убыванию").isEqualTo("desc");
    }
}
