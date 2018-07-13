package ru.job4j.map;

import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testMap() {
        Object DUMMY = new Object();
        Map<User,Object> map = new HashMap<>();
        map.put(new User("Alex",0,new GregorianCalendar(1975,2,15)), DUMMY);
        map.put(new User("Alex",0,new GregorianCalendar(1975,2,15)), DUMMY);
        System.out.println(map);
    }

}