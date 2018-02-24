package me.maxwu.genre.htmlUnit;

import me.maxwu.genre.selenide.SelenideBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by maxwu on 3/19/17.
 */
public class GenreHtmlUnitTest {

    @Test
    public void testOneSong(){
        String song = "That's What I Like";
        String artist = "Bruno Mars";
        List<String> genres = new HtmlUnitBase().getSongGenres(song, artist);
        Assert.assertEquals("Pop", genres.get(0));
        Assert.assertEquals(1, genres.size());
    }

    @Test
    public void testTop10Genres(){
        Map<Integer, Map<String, Object>> map = new SelenideBase().getBillboardTop100Map(10);
        List<String> songs = map.values().stream().map(ls -> ls.get("song").toString()).collect(Collectors.toList());
        //songs.stream().forEach(System.out::println);
        HtmlUnitBase htmlUnit = new HtmlUnitBase();

        for(Map<String, Object> entry: map.values()){
            Assert.assertNotNull(entry.get("song"));
            Assert.assertFalse(entry.get("song").toString().isEmpty());

            List<String> genres = htmlUnit.getSongGenres(entry.get("song").toString(),entry.get("artist").toString());
            Assert.assertNotNull(genres);
            Assert.assertTrue(genres.size() > 0);

            System.out.println(">>> " + entry.get("song") + ":" + genres.stream().collect(Collectors.joining()));

        }
    }
}
