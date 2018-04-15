package com.epam.brest.course.dao;

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
@ContextConfiguration(locations = {"classpath:dao-context-test.xml",
        "classpath:test-db.xml", "classpath:dao-context.xml"})
@Rollback
public class PlayerDaoImplTest {

    private static final int PLAYER_NUMBER = 1;
    private static final String PLAYER_NAME = "player name";

    @Autowired
    PlayerDao playerDao;

    @Test
    public void getAllPlayersTest(){
        Collection<Player> players = playerDao.getAllPlayers();
        Assert.assertFalse(players.isEmpty());
    }

    @Test
    public void deletePlayerByIdTest(){
        Integer wasCount = playerDao.getAllPlayers().size();
        playerDao.deletePlayerById(1);

        Assert.assertTrue(((Integer) playerDao.getAllPlayers()
                .size()).equals(wasCount - 1));
    }

    @Test
    public void addPlayerTest(){
        Player player = new Player(PLAYER_NUMBER, PLAYER_NAME,
                PLAYER_NUMBER, PLAYER_NUMBER, PLAYER_NUMBER);

        Player player1 = playerDao.addPlayer(player);
        Assert.assertTrue(player1.getPlayer_name().equals(player.getPlayer_name()));
        Assert.assertTrue(player1.getPlayer_age().equals(player.getPlayer_age()));
        Assert.assertTrue(player1.getPlayer_cost().equals(player.getPlayer_cost()));
        Assert.assertTrue(player1.getPlayer_number().equals(player.getPlayer_number()));
        Assert.assertTrue(player1.getPlayer_team_id().equals(player.getPlayer_team_id()));
    }

    @Test
    public void updatePlayerTest(){

        Player player = playerDao.getPlayerById(PLAYER_NUMBER);
        player.setPlayer_name(PLAYER_NAME + PLAYER_NAME);
        player.setPlayer_cost(PLAYER_NUMBER + PLAYER_NUMBER);
        player.setPlayer_age(PLAYER_NUMBER + PLAYER_NUMBER);
        playerDao.updatePlayer(player);
        Player newPlayer = playerDao.getPlayerById(PLAYER_NUMBER);
        Assert.assertTrue(newPlayer.getPlayer_name().equals(player.getPlayer_name()));
        Assert.assertTrue(newPlayer.getPlayer_age().equals(player.getPlayer_age()));
        Assert.assertTrue(newPlayer.getPlayer_cost().equals(player.getPlayer_cost()));
    }

//    @Test
//    public void getPlayerByName(){
//        Collection<Player> players = playerDao.getPlayerByName("pav");
//
//    }
}
