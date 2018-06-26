package com.viadee.sonarQuest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.Artefact;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.repositories.ArtefactRepository;

@Service
public class ArtefactService {

    @Autowired
    private ArtefactRepository artefactRepository;

    @Autowired
    private LevelService levelService;

    @Autowired
    private UserService userService;

    public List<Artefact> getArtefacts() {
        return artefactRepository.findAll();
    }

    public List<Artefact> getArtefactsforMarkteplace() {
        return artefactRepository.findByQuantityIsGreaterThanEqual((long) 1);
    }

    public Artefact getArtefact(final long id) {
        return artefactRepository.findOne(id);
    }

    public Artefact createArtefact(final Artefact artefact) {
        final Level level = new Level();
        level.setMin(artefact.getMinLevel().getMin());
        levelService.save(level);
        artefact.setMinLevel(level);
        artefactRepository.saveAndFlush(artefact);
        return artefact;
    }

    public Artefact updateArtefact(final Long id, final Artefact artefactDto) {
        final Artefact artefact = artefactRepository.findOne(id);
        Level level;
        if (artefactDto.getMinLevel().getId() == null) {
            level = new Level();
            level.setMin(artefact.getMinLevel().getMin());
        } else {
            level = levelService.findById(artefact.getMinLevel().getId());
        }
        levelService.save(level);

        artefact.setName(artefactDto.getName());
        artefact.setIcon(artefactDto.getIcon());
        artefact.setPrice(artefactDto.getPrice());
        artefact.setDescription(artefactDto.getDescription());
        artefact.setQuantity(artefactDto.getQuantity());
        artefact.setMinLevel(level);
        artefact.setSkills(artefactDto.getSkills());
        return artefactRepository.save(artefact);
    }

    public Artefact buyArtefact(Artefact artefact, final User user) {

        // If developer has TOO LITTLE GOLD, Then the purchase is canceled
        final long gold = user.getGold() - artefact.getPrice();
        if (gold < 0) {
            return null;
        }

        // If the developer has ALREADY BOUGHT the Artefact, Then the purchase is
        // canceled
        for (final Artefact a : user.getArtefacts()) {
            if (a.equals(artefact)) {
                return null;
            }
        }

        // If the artefact is SOLD OUT, then the purchase is canceled
        if (artefact.getQuantity() < 1) {
            return null;
        }

        // When the LEVEL of the developer is too low, then the purchase is canceled
        final long minLevel = artefact.getMinLevel().getMin();
        final long xp = user.getXp();
        final long devLevel = userService.getLevel(xp);

        if (minLevel > devLevel) {
            return null;
        }

        user.getArtefacts().add(artefact);
        user.setGold(gold);
        userService.save(user);

        artefact.setQuantity(artefact.getQuantity() - 1);
        artefact = artefactRepository.save(artefact);
        return artefact;
    }

}
