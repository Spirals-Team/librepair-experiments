package com.epam.brest.course.dao;

import com.epam.brest.course.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collection;

public class PlayerDaoImpl implements PlayerDao{

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PLAYER_ID = "player_id";
    private static final String PLAYER_NAME = "player_name";
    private static final String PLAYER_NUMBER = "player_number";
    private static final String PLAYER_AGE = "player_age";
    private static final String PLAYER_TEAM_ID = "player_team_id";
    private static final String PLAYER_COST = "player_cost";


    @Value("${players.addPlayerIntoTable}")
    private String addNewPlayerQuery;

    @Value("${players.getAllPlayersFromTable}")
    private String getAllPlayersQuery;

    @Value("${players.deletePlayerByIdFromTable}")
    private String deletePlayerByIdQuery;

    @Value("${players.updatePlayer}")
    private String updatePlayerQuery;

    @Value("${players.getPlayerByName}")
    private String getPlayerNameQuery;

    @Value("${players.getPlayerById}")
    private String getPlayerByIdQuery;

    /**
     * Allows use named parameters instead of "?"-symbol
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PlayerDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Gets all players
     *
     * @return collection of players
     */
    @Override
    public Collection<Player> getAllPlayers() {

        LOGGER.debug("getAllPlayers()");
        Collection<Player> players =
                namedParameterJdbcTemplate.getJdbcOperations()
                        .query(getAllPlayersQuery,
                                BeanPropertyRowMapper
                                        .newInstance(Player.class));
        return players;
    }

    /**
     * Adds new player
     *
     * @return instance of added player
     */
    @Override
    public Player addPlayer(Player player) {

        LOGGER.debug("addPlayer({})", player);

        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue(PLAYER_NAME, player.getPlayer_name());
        namedParameter.addValue(PLAYER_NUMBER, player.getPlayer_number());
        namedParameter.addValue(PLAYER_AGE, player.getPlayer_age());
        namedParameter.addValue(PLAYER_COST, player.getPlayer_cost());
        namedParameter.addValue(PLAYER_TEAM_ID, player.getPlayer_team_id());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(addNewPlayerQuery, namedParameter,
                generatedKeyHolder);
        player.setPlayer_id(generatedKeyHolder.getKey().intValue());
        return player;
    }

    /**
     * Deletes player by id
     *
     * @param playerId required id
     */
    @Override
    public void deletePlayerById(Integer playerId) {

        LOGGER.debug("deletePlayerById({})", playerId);
        namedParameterJdbcTemplate.getJdbcOperations()
                .update(deletePlayerByIdQuery, playerId);
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
        SqlParameterSource namedParameter =
                new MapSqlParameterSource(PLAYER_NAME, namePattern);
        Collection<Player> players = namedParameterJdbcTemplate
                .query(getPlayerNameQuery, namedParameter,
                        BeanPropertyRowMapper.newInstance(Player.class));
        return players;
    }

    /**
     * Updates player
     * @param player player to update
     */
    @Override
    public void updatePlayer(Player player) {

        LOGGER.debug("updatePlayer({})", player);
        SqlParameterSource namedParameter =
                new BeanPropertySqlParameterSource(player);
        namedParameterJdbcTemplate.update(updatePlayerQuery, namedParameter);
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

        SqlParameterSource namedParameter =
                new MapSqlParameterSource(PLAYER_ID, playerId);
        Player player =
                namedParameterJdbcTemplate.queryForObject(getPlayerByIdQuery,
                        namedParameter,
                        BeanPropertyRowMapper.newInstance(Player.class));
        return player;
    }
}
