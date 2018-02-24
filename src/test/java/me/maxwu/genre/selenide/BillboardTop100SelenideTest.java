package me.maxwu.genre.selenide;

import org.junit.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by maxwu on 3/15/17.
 */
public class BillboardTop100SelenideTest extends SelenideBase {

    @Rule
    public BillboardTop100Watcher top100Rule = new BillboardTop100Watcher();

    @Before
    public void setUp() {
        System.setProperty("selenide.browser", "chrome");
        org.junit.Assume.assumeTrue(System.getProperty("SelenideTest", "False").equalsIgnoreCase("True"));
    }

    @AfterClass
    public static void tearDown(){
        quitDriver();
    }


    @Test
    public void testTop10Genres(){
        Map<Integer, Map<String, Object>> map = onBillboardTop100Page().getTop100Map(10);
        List<String> songs = map.values().stream().map(ls -> ls.get("song").toString()).collect(Collectors.toList());
        //songs.stream().forEach(System.out::println);

        for(Map<String, Object> entry: map.values()){
            Assert.assertNotNull(entry.get("song"));
            Assert.assertFalse(entry.get("song").toString().isEmpty());

            List<String> genres = getSongGenres(entry.get("song").toString(),entry.get("artist").toString());
            Assert.assertNotNull(genres);
            Assert.assertTrue(genres.size() > 0);

            System.out.println(">>> " + entry.get("song") + ":" + genres.stream().collect(Collectors.joining()));

        }
    }
}
