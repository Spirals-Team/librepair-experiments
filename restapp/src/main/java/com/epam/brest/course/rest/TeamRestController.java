package com.epam.brest.course.rest;


import com.epam.brest.course.model.Team;
import com.epam.brest.course.service.TeamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class TeamRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TeamService teamService;


    @GetMapping(value = "/teams")
    @ResponseStatus(HttpStatus.OK)
    Collection<Team> getAllTeams(){

        LOGGER.debug("getAllTeamsREST()");
        return teamService.getAllTeams();
    }

    @DeleteMapping(value = "/teams/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    void deleteTeam(@PathVariable Integer id){

        LOGGER.debug("deleteTeamREST({})", id);
        teamService.deleteTeamById(id);
    }
}
