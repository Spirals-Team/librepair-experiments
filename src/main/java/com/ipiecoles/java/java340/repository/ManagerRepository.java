package com.ipiecoles.java.java340.repository;

import com.ipiecoles.java.java340.model.Manager;
import org.springframework.data.jpa.repository.EntityGraph;
// Lorsqu'un classe abstraite ne possède aucun attribut (en dehors de constantes) 
//et aucune méthode non statique, on peut la remplacer par une interface.
//repository (dépôt) est un stockage centralisé et organisé de données
public interface ManagerRepository extends BaseEmployeRepository<Manager> {
    @EntityGraph(attributePaths = "equipe")
    Manager findOneWithEquipeById(Long id);
}
