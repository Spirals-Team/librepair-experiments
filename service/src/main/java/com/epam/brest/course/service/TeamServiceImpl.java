package com.epam.brest.course.service;

import com.epam.brest.course.dao.TeamDao;
import com.epam.brest.course.model.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class TeamServiceImpl implements TeamService {

    private static final Logger LOGGER = LogManager.getLogger();

    private TeamDao teamDao;

    public TeamServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    /**
     * Gets collection of teams
     *
     * @return teams
     */
    @Override
    public Collection<Team> getAllTeams() {

        LOGGER.debug("getAllTeams()");
        return teamDao.getAllTeams();
    }

    /**
     * Adds new team
     *
     * @param team team to adding
     * @return added team
     */
    @Override
    public Team addTeam(Team team) {

        LOGGER.debug("addTeam({})", team);
        return teamDao.addTeam(team);
    }

    /**
     * Deletes team by id
     *
     * @param teamId required id
     */
    @Override
    public void deleteTeamById(Integer teamId) {

        LOGGER.debug("deleteTeamById({})", teamId);
        teamDao.deleteTeamById(teamId);
    }

    /**
     * Updates required team
     *
     * @param team team to update
     * @return updated team's instance
     */
    @Override
    public Team updateTeam(Team team) {

        LOGGER.debug("updateTeam({})", team);
        return teamDao.updateTeam(team);
    }

    /**
     * Gets team with required ID
     *
     * @param teamId team's ID
     * @return required team
     */
    @Override
    public Team getTeamById(Integer teamId) {

        LOGGER.debug("getTeamById({})", teamId);
        return teamDao.getTeamById(teamId);
    }
}
