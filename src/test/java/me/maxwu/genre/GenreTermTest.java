package me.maxwu.genre;

import me.maxwu.genre.GenreTerm;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static me.maxwu.genre.GenreTerm.getDefaultGenreList;
import static me.maxwu.genre.GenreTerm.isDefaultGenreList;

/**
 * Created by maxwu on 3/15/17.
 */
public class GenreTermTest {

    @Test
    public void testGenreList(){
        List<String> genres = GenreTerm.getGenreList();
        genres.stream().forEach(g -> System.out.println("<"+g+">"));
        Assert.assertEquals(126, genres.size());
    }

    @Test
    public void testDefaultGenreList(){
        Assert.assertTrue(isDefaultGenreList(getDefaultGenreList()));
    }
}
