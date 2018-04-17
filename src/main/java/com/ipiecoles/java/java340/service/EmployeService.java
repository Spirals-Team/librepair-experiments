package com.ipiecoles.java.java340.service;

import com.ipiecoles.java.java340.model.Employe;
import com.ipiecoles.java.java340.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    public Employe findById(Long id){
        return employeRepository.findOne(id);
    }

    public Long countAllEmploye() {
        return employeRepository.count();
    }

    public void deleteEmploye(Long id){
        employeRepository.delete(id);
    }

    public <T extends Employe> T creerEmploye(T e) {
        return employeRepository.save(e);
    }

    public <T extends Employe> T updateEmploye(Long id, T employe) {
        return employeRepository.save(employe);
    }

    public Page<Employe> findAllEmployes(Integer page, Integer size, String sortProperty, String sortDirection) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.fromString(sortDirection),sortProperty));
        Pageable pageable = new PageRequest(page,size,sort);
        return employeRepository.findAll(pageable);
    }

    public Employe findByMatricule(String matricule) {
        Employe employe = this.employeRepository.findByMatricule(matricule);
        if(employe == null){
            throw new EntityNotFoundException("Impossible de trouver l'employé de matricule " + matricule);
        }
        return employe;
    }

}
