package me.maxwu.genre.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import me.maxwu.genre.IGenreCmd;
import me.maxwu.genre.search.GoogleKeyword;
import me.maxwu.genre.selenide.pageObjects.BillboardTop100Page;
import me.maxwu.genre.selenide.pageObjects.GooglePage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Configuration.baseUrl;
import static me.maxwu.genre.GenreTerm.getDefaultGenreList;
import static me.maxwu.genre.GenreTerm.isDefaultGenreList;

/**
 * Created by maxwu on 3/14/17.
 */
public class SelenideBase implements IGenreCmd{
    static Logger logger = LoggerFactory.getLogger(SelenideBase.class.getName());
    WebDriver driver = null;

    private void setDriver(){
        if (DriverFactory.hasQuit(driver)) {
            driver = DriverFactory.getChromeDriver();

            WebDriverRunner.setWebDriver(driver);
            Configuration.reportsFolder = "target";
        }
    }

    public BillboardTop100Page onBillboardTop100Page() {
        setDriver();
        baseUrl = "http://www.billboard.com/charts/hot-100";
        return open("/", BillboardTop100Page.class);
    }

    public Map<Integer, Map<String, Object>> getBillboardTop100Map(){
        return getBillboardTop100Map(100);
    }

    public Map<Integer, Map<String, Object>> getBillboardTop100Map(int size) {
        Map<Integer, Map<String, Object>> map = onBillboardTop100Page().getTop100Map(size);
        quitDriver();
        return map;
    }

    public GooglePage onGooglePage() {
        setDriver();
        baseUrl = "http://google.com";
        return open("/", GooglePage.class);
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static String keywordSongGenre(String song){
        return song + " song genre";
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static String keywordGenre(String song){
        return song + " genre";
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static String keywordArtistGenre(String song, String artist){
        return artist + " - " + song + " genre";
    }

    /**
     * The logic to fetch genre is:
     *  - Try "$song genre";
     *  - If NA, try "$song song genre", otherwise return genre list
     *  - Return genre list, for failed trial, return {"NA"}.
     * @param song
     * @return ArrayList of genre strings
     */
    @Override
    public List<String> getSongGenres(String song, String artist) {
        for (String keyword : new GoogleKeyword().getKeyWordList(song, artist)) {
            List<String> genres = onGooglePage().getSearchFor(keyword).getGenres();
            if (!isDefaultGenreList(genres)) {
                quitDriver();
                return genres;
            }
        }
        quitDriver();
        return getDefaultGenreList();
    }


    public static void quitDriver(){
        DriverFactory.quitDriver(WebDriverRunner.getWebDriver());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        quitDriver();
    }
}