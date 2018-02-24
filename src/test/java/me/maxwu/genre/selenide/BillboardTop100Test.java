package me.maxwu.genre.selenide;

import org.junit.*;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by maxwu on 3/14/17.
 */
public class BillboardTop100Test extends SelenideBase {
    @Before
    public void setUp() {
        System.setProperty("selenide.browser", "chrome");
        org.junit.Assume.assumeTrue(System.getProperty("SelenideTest", "False").equalsIgnoreCase("True"));
    }
    
    @After
    public void tearDown(){
        quitDriver();
    }

    @Test
    public void testTop100Map(){
        Map<Integer, Map<String, Object>> map = onBillboardTop100Page().getTop100Map();
        Assert.assertEquals(100, map.size());
    }

    @Test
    public void testTop10Map(){
        Map<Integer, Map<String, Object>> map = onBillboardTop100Page().getTop100Map(10);
        Assert.assertEquals(10, map.size());
    }

    /**
    @Test @Ignore("deprecated")
    public void testTop100Songs(){
        List<String> songs = onBillboardTop100Page().getTop100SongNames();
        songs.stream().forEach(logger::info);
        Assert.assertEquals(100, songs.size());
    }

    @Test @Ignore("deprecated")
    public void testTop100Artists(){
        List<String> singers = onBillboardTop100Page().getTop100ArtistNames();
        singers.stream().forEach(logger::info);
        Assert.assertEquals(100, singers.size());
    }
    **/
}
