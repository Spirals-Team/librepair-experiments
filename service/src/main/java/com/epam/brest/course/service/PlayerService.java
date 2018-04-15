package com.epam.brest.course.service;

import com.epam.brest.course.model.Player;

import java.util.Collection;

public interface PlayerService {

    /**
     * Gets all players
     * @return collection of players
     */
    Collection<Player> getAllPlayers();

    /**
     * Adds new player
     * @return instance of added player
     */
    Player addPlayer(Player player);

    /**
     * Deletes player by id
     * @param playerId required id
     */
    void deletePlayerById(Integer playerId);

    /**
     * Gets player for some name template
     * @param namePattern part or whole name
     * @return player's instance
     */
    Collection<Player> getPlayerByName(String namePattern);


    /**
     * Updates player
     * @param player player to update
     */
    void updatePlayer(Player player);

    /**
     * Gets player's instance from table using id
     * @param playerId required id
     * @return player instance
     */
    Player getPlayerById(Integer playerId);
}
