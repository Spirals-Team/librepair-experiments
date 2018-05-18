package com.lespasrieurs.m2dl.ivvq.service;

import com.lespasrieurs.m2dl.ivvq.domain.Parieur;
import com.lespasrieurs.m2dl.ivvq.repositories.ParieurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by MONTASSER on 30/03/2018.
 */

@Service
public class ParieurService {

    @Autowired
    private ParieurRepository parieurRepository;

    public Parieur saveParieur(Parieur parieur) {
        return parieurRepository.save(parieur) ;
    }

    public Parieur findById(Long id) {
        return parieurRepository.findOne(id);
    }

    public ParieurRepository getParieurRepository() {
        return parieurRepository;
    }

    public void setParieurRepository(ParieurRepository parieurRepository) {
        this.parieurRepository = parieurRepository;
    }
}
