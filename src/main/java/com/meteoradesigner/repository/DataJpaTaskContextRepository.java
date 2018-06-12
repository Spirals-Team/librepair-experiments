package com.meteoradesigner.repository;

import com.meteoradesigner.model.TaskContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This interface @code{DataJpaTaskContextRepository} declares the data jpa @code{TaskContext}
 * repository.
 */
//TODO custom methods
@Transactional
@Repository
public interface DataJpaTaskContextRepository extends JpaRepository<TaskContext, Integer>,
        GenericAbstractCrudRepository<TaskContext, Integer> {

    //TODO Query and check good practice how to name/add methods not existing by default
}