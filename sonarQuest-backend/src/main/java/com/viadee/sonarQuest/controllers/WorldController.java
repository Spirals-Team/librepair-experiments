package com.viadee.sonarQuest.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.UserService;
import com.viadee.sonarQuest.services.WorldService;

@RestController
@RequestMapping("/world")
public class WorldController {

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WorldService worldService;

    @RequestMapping(method = RequestMethod.GET)
    public List<World> getAllWorlds() {
        return this.worldRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/developer/{developer_id}", method = RequestMethod.GET)
    public World getCurrentWorld(final Principal principal) {
        final User user = userService.findByUsername(principal.getName());
        return user.getWorlds().stream().findAny().orElse(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public World getWorldById(@PathVariable(value = "id") final Long id) {
        return worldRepository.findOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public World updateWorld(@PathVariable(value = "id") final Long id, @RequestBody final World data) {
        World world = this.worldRepository.findOne(id);
        if (world != null) {
            world.setName(data.getName());
            world.setActive(data.getActive());
            world = this.worldRepository.save(world);
        }
        return world;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}/image", method = RequestMethod.PUT)
    public World updateBackground(@PathVariable(value = "id") final Long id, @RequestBody final String image) {
        World world = this.worldRepository.findOne(id);
        if (world != null) {
            world.setImage(image);
            world = this.worldRepository.save(world);
        }
        return world;
    }

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public List<World> generateWorlds() {
        worldService.updateWorlds();
        return worldRepository.findAll();

    }

}
