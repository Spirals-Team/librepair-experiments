package com.epam.brest.course.dao;

import com.epam.brest.course.model.Team;
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

public class TeamDaoImpl implements TeamDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String TEAM_ID = "team_id";
    private static final String TEAM_NAME = "team_name";
    private static final String TEAM_COUNTRY = "team_country";

    @Value("${teams.getAllTeams}")
    private String getAllTeamsQuery;

    @Value("${teams.addNewTeam}")
    private String addNewTeamQuery;

    @Value("${teams.deleteTeamById}")
    private String deleteTeamQuery;

    @Value("${players.deletePlayersFromTeam}")
    private String deletePlayersFromTeamQuery;

    @Value("${meetings.setFirstTeamIdAsNull}")
    private String deleteFirstTeamQuery;

    @Value("${meetings.setSecondTeamIdAsNull}")
    private String deleteSecondTeamQuery;

    @Value("${teams.updateTeam}")
    private String updateTeamQuery;

    @Value("${teams.getTeamById}")
    private String getTeamByIdQuery;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TeamDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Gets collection of teams
     *
     * @return teams
     */
    @Override
    public Collection<Team> getAllTeams() {

        LOGGER.debug("getAllTeams()");
        Collection<Team> teams = namedParameterJdbcTemplate.getJdbcOperations()
                .query(getAllTeamsQuery,
                        BeanPropertyRowMapper.newInstance(Team.class));
        return teams;
    }

    /**
     * Adds new team
     *
     * @param team team to adding
     * @return added team
     */
    @Override
    public Team addTeam(Team team) {

        LOGGER.debug("addTeam()", team);
        MapSqlParameterSource mapSqlParameterSource =
                new MapSqlParameterSource();
        mapSqlParameterSource.addValue(TEAM_NAME, team.getTeam_name());
        mapSqlParameterSource.addValue(TEAM_COUNTRY, team.getTeam_country());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(addNewTeamQuery,
                mapSqlParameterSource, generatedKeyHolder);
        team.setTeam_id(generatedKeyHolder.getKey().intValue());
        return team;
    }

    /**
     * Deletes team by id, players from it and sets as NULL fields of meeting's table
     * which refers on this team
     * @param teamId required id
     */
    @Override
    public void deleteTeamById(Integer teamId) {

        LOGGER.debug("deleteTeamById({})", teamId);
        namedParameterJdbcTemplate.getJdbcOperations()
                .update(deletePlayersFromTeamQuery, teamId);
        namedParameterJdbcTemplate.getJdbcOperations()
                .update(deleteFirstTeamQuery, teamId);
        namedParameterJdbcTemplate.getJdbcOperations()
                .update(deleteSecondTeamQuery, teamId);
        namedParameterJdbcTemplate.getJdbcOperations()
                .update(deleteTeamQuery, teamId);
    }

    /**
     * Updates required team
     * @param team team to update
     * @return updated team's instance
     */
    @Override
    public Team updateTeam(Team team) {

        LOGGER.debug("updateTeam({})", team);
        SqlParameterSource namedParameter =
                new BeanPropertySqlParameterSource(team);
        namedParameterJdbcTemplate.update(updateTeamQuery, namedParameter);
        return team;
    }

    /**
     * Gets team with required ID
     * @param teamId team's ID
     * @return required team
     */
    @Override
    public Team getTeamById(Integer teamId) {

        LOGGER.debug("getTeamById({})", teamId);

        MapSqlParameterSource mapSqlParameterSource =
                new MapSqlParameterSource(TEAM_ID, teamId);
        Team team = namedParameterJdbcTemplate.queryForObject(getTeamByIdQuery,
                mapSqlParameterSource, BeanPropertyRowMapper
                        .newInstance(Team.class));
        return team;
    }
}
