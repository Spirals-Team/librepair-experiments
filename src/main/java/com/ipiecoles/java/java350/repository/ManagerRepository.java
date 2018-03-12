package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Manager;
import org.springframework.data.jpa.repository.EntityGraph;

public interface ManagerRepository extends BaseEmployeRepository<Manager> {
    @EntityGraph(attributePaths = "equipe")
    Manager findOneWithEquipeById(Long id);
}
