package com.ipiecoles.java.java340.service;

import com.ipiecoles.java.java340.model.Manager;
import com.ipiecoles.java.java340.model.Technicien;
import com.ipiecoles.java.java340.repository.ManagerRepository;
import com.ipiecoles.java.java340.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TechnicienService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private TechnicienRepository technicienRepository;

    public Manager addManager(Long idTechnicien, String matricule) {
        Technicien t = technicienRepository.findOne(idTechnicien);
        if(t == null){
            throw new EntityNotFoundException("Impossible de trouver le technicien d'identifiant " + idTechnicien);
        }
        Manager m = managerRepository.findByMatricule(matricule);
        if(m == null){
            throw new EntityNotFoundException("Impossible de trouver le manager de matricule " + matricule);
        }

        if(t.getManager() != null){
            throw new IllegalArgumentException("Le technicien a déjà un manager : " + t.getManager().getPrenom() + " " + t.getManager().getNom()
                    + " (matricule " + t.getManager().getMatricule() + ")");
        }

        m.getEquipe().add(t);
        m = managerRepository.save(m);

        t.setManager(m);
        technicienRepository.save(t);

        return m;
    }
}
