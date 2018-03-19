package pl.kolejarz.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import pl.kolejarz.domain.Player;
import pl.kolejarz.repository.IPlayerRepository;
import pl.kolejarz.repository.PlayerRepositoryFactory;
import static org.hamcrest.CoreMatchers.*;

public class PlayerRepositoryTest
{
    IPlayerRepository playerRepository;
    

    @Before
    public void initDatabase() throws SQLException
    {
        String url = "jdbc:hsqldb:hsql://localhost/workdb";
        playerRepository = new PlayerRepositoryFactory(DriverManager.getConnection(url));
        Player fNeo = new Player();
        fNeo.setId(1);
        fNeo.setFirstName("Filip");
        fNeo.setNickName("Neo");
        Player jJarzabkowski = new Player();
        jJarzabkowski.setId(2);
        jJarzabkowski.setFirstName("Jaroslaw");
        jJarzabkowski.setNickName("Pasha");
        Player fPionka = new Player();
        fPionka.setId(3);
        fPionka.setFirstName("Filip");
        fPionka.setNickName("Pionas");
         
        playerRepository.add(fNeo);
        playerRepository.add(jJarzabkowski);
        playerRepository.add(fPionka);
     
    }

    
    @Test
    public void addPlayerTest() throws SQLException
    {
        Player wWojtas = new Player();
       
        wWojtas.setId(4);
        wWojtas.setFirstName("Wiktor");
        wWojtas.setNickName("Taz");
        playerRepository.add(wWojtas);
       assertEquals(wWojtas.getNickName(), playerRepository.getById(4).getNickName());
    }

    @Test
    public void updatePlayerTest() throws SQLException
    {
        Player updatePlayer = playerRepository.getById(1);
        updatePlayer.setFirstName("Dominik");
        playerRepository.update(updatePlayer, 1);

        assertEquals("Dominik", playerRepository.getById(1).getFIrstName());
        assertEquals("Jaroslaw", playerRepository.getById(2).getFIrstName());
    }

    @Test
    public void deletePlayerTest() throws SQLException
    {
        playerRepository.delete(3);
        assertNull(playerRepository.getById(3).getFIrstName());
        assertFalse(playerRepository.getAll().isEmpty());
    }

    @Test
    public void getByIdTest() throws SQLException
    {
        assertEquals(1, playerRepository.getById(1).getId());
    }

    @Test
    public void getByName()
    {
        Player player = playerRepository.getByNickName("Pasha");
        assertThat(player.getFIrstName(), is("Jaroslaw"));
    }

    @Test
    public void getAllTest()
    {
        assertNotNull(playerRepository.getAll());
    }
} 