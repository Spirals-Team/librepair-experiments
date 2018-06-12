package com.meteoradesigner.repository;

import com.meteoradesigner.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//TODO documentation
//TODO custom methods
@Transactional
@Repository
public interface DataJpaTaskRepository extends JpaRepository<Task, Integer>,
        GenericAbstractCrudRepository<Task, Integer> {
}
