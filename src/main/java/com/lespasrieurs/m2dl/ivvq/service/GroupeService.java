package com.lespasrieurs.m2dl.ivvq.service;

import com.lespasrieurs.m2dl.ivvq.domain.Groupe;
import com.lespasrieurs.m2dl.ivvq.repositories.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Marti_000 on 30/03/2018.
 */
@Service
public class GroupeService {

    @Autowired
    private GroupeRepository groupeRepository;

    public Groupe saveGroupe(Groupe groupe) {
        return groupeRepository.save(groupe) ;
    }

    public GroupeRepository getGroupeRepository() {
        return groupeRepository;
    }

    public void setGroupeRepository(GroupeRepository groupeRepository) {
        this.groupeRepository = groupeRepository;
    }
}
