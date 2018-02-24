package me.maxwu.genre.httpClient;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by maxwu on 3/19/17.
 */
public class GenreHttpTest {
    @Test
    @Ignore("Not Finished")
    public void testOneSong(){
        String song = "That's What I Like";
        String artist = "Bruno Mars";
        List<String> genres = new JsoupBase().getSongGenres(song, artist);
        Assert.assertEquals("Pop", genres.get(0));

        genres.stream().forEach(System.err::println);
    }
}
