package com.epam.brest.course.service;

import com.epam.brest.course.dao.PlayerDao;
import com.epam.brest.course.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class PlayerServiceImpl implements PlayerService {

    private static final Logger LOGGER = LogManager.getLogger();

    private PlayerDao playerDao;

    public PlayerServiceImpl(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    /**
     * Gets all players
     *
     * @return collection of players
     */
    @Override
    public Collection<Player> getAllPlayers() {

        LOGGER.debug("getAllPlayer()");
        return playerDao.getAllPlayers();
    }

    /**
     * Adds new player
     *
     * @param player player to add
     * @return instance of added player
     */
    @Override
    public Player addPlayer(Player player) {

        LOGGER.debug("addPlayer({})", player);
        return playerDao.addPlayer(player);
    }

    /**
     * Deletes player by id
     *
     * @param playerId required id
     */
    @Override
    public void deletePlayerById(Integer playerId) {
        LOGGER.debug("deletePlayerById({})", playerId);
        playerDao.deletePlayerById(playerId);
    }

    /**
     * Gets player for some name template
     *
     * @param namePattern part or whole name
     * @return player's instance
     */
    @Override
    public Collection<Player> getPlayerByName(String namePattern) {
        LOGGER.debug("getPlayerByName({})", namePattern);
        return playerDao.getPlayerByName(namePattern);
    }

    /**
     * Updates player
     *
     * @param player player to update
     */
    @Override
    public void updatePlayer(Player player) {

        LOGGER.debug("updatePlayer({})", player);
        playerDao.updatePlayer(player);
    }

    /**
     * Gets player's instance from table using id
     *
     * @param playerId required id
     * @return player instance
     */
    @Override
    public Player getPlayerById(Integer playerId) {

        LOGGER.debug("getPlayerById({})", playerId);
        return playerDao.getPlayerById(playerId);
    }
}
