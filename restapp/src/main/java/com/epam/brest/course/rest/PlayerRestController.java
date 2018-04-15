package com.epam.brest.course.rest;

import com.epam.brest.course.model.Player;
import com.epam.brest.course.service.PlayerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class PlayerRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private PlayerService playerService;

    @GetMapping(value = "/players")
    @ResponseStatus(HttpStatus.OK)
    Collection<Player> getAllPlayer(){

        LOGGER.debug("getAllPlayerREST()");
        return playerService.getAllPlayers();
    }

    @PostMapping(value = "/players")
    @ResponseStatus(HttpStatus.CREATED)
    Player addPlayer(@RequestBody Player player){

        LOGGER.debug("addPlayerREST({})", player);
        return playerService.addPlayer(player);
    }

    @GetMapping(value = "/players/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    Player getPlayerById(@PathVariable Integer id){

        LOGGER.debug("getPlayerByIdREST({})", id);
        return playerService.getPlayerById(id);
    }

    @DeleteMapping(value = "/players/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    void deletePlayerById(@PathVariable Integer id){

        LOGGER.debug("deletePlayerByIdREST({})", id);
        playerService.deletePlayerById(id);
    }
}
