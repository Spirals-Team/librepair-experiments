package me.maxwu.genre.app;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import java.util.List;
import java.util.Map;

import static me.maxwu.genre.app.App.parseCli;

/**
 * Created by maxwu on 3/22/17.
 */
public class AppTest {
    @Test
    public void testOneSong(){
        String[] args = {"-s", "\"Love On The Brain\"", "-a", "\"Rihanna\""};
        Map<Integer, Map<String, Object>> map = parseCli(args).map;
        System.out.println(new Yaml().dump(map));
        Assert.assertEquals(1, map.size());
        List<String> genres = (List<String>)(map.get(Integer.valueOf(1)).get("genres"));
        Assert.assertNotEquals(-1, genres.indexOf("Pop"));
    }

    @Test
    public void testBillboardTop2(){
        String[] args = {"-n", "2"};
        Map<Integer, Map<String, Object>> map = parseCli(args).map;
        System.out.println(new Yaml().dump(map));
        Assert.assertEquals(2, map.size());
    }

    @Test
    public void testBillboardTop10Default(){
        Map<Integer, Map<String, Object>> map = new App().map;
        System.out.println(new Yaml().dump(map));
        Assert.assertEquals(10, map.size());
    }
}
