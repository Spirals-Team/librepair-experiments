package com.epam.brest.course.service;

import com.epam.brest.course.model.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test.xml",
        "classpath:test-db.xml", "classpath:dao-context.xml"})
@Rollback
public class PlayerServiceImplTest {

    @Autowired
    private PlayerService playerService;

    @Test
    public void getAllPlayersTest(){

        Collection<Player> players = playerService.getAllPlayers();
        Assert.assertTrue(((Integer) players.size()).equals(10));
    }

    @Test
    public void deletePlayerTest(){

        Integer playersCounter = playerService.getAllPlayers().size();
        playerService.deletePlayerById(1);
        Assert.assertTrue(((Integer) playerService.getAllPlayers().size())
                .equals(playersCounter - 1));
    }
}
