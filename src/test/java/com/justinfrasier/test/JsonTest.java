package com.justinfrasier.test;

import com.justinfrasier.Json.Json;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JsonTest {

    Json json;

    @Before
    public void before(){
//        json = new Json("https://www.fantasyfootballnerd.com/service/schedule/json/yhnkisjxcbsm/");
        json = new Json("http://justinfrasier.com/test/test.json");
    }
    @Test
    public void currentWeekTest() throws Exception{ assertEquals(1,json.currentWeek()); }

    @Test
    public void gamesInCurrentWeekTest(){
        List<Integer> list = new ArrayList<Integer>();
        for(int i =1; i<17 ; i++) list.add(i);
        assertEquals(list,json.gamesInCurrentWeek(1));
    }

    @Test
    public void getGameWeekTest(){ assertEquals(1, json.getGameWeek(1)); }

    @Test
    public void getGameWeekBreakTest(){ assertEquals(-1, json.getGameWeek(257)); }

    @Test
    public void getGameDateTest1(){ assertEquals("2017-09-07", json.getGameDate(1)); }

    @Test
    public void getGameDateTest2(){ assertEquals("2017-10-01", json.getGameDate(56)); }

    @Test
    public void getGameDateTest3(){ assertEquals("2017-09-17", json.getGameDate(30)); }

    @Test
    public void getGameDateTestBreak(){ assertNull(json.getGameDate(257));}

    @Test
    public void getAwayTeamTest(){ assertEquals("DET", json.getAwayTeam(56)); }

    @Test
    public void getHomeTeamTest(){ assertEquals("MIN", json.getHomeTeam(56)); }

    @Test
    public void getGameTimeTest(){assertEquals("1:00 PM", json.getGameTimeET(56));}

    @Test
    public void getTvStationTest(){assertEquals("FOX", json.getTvStation(56));}

    @Test
    public void getWinnerTest(){assertEquals("", json.getWinner(1));}
}
