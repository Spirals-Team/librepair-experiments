package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.model.Incidence;

@Repository
public interface IncidencesRepository extends CrudRepository<Incidence, Long> {

    // @Query("SELECT i FROM Incidence i WHERE i.user.identificador = ?1")
    // public Page<Incidence> findIncidences(Pageable page, String identificador);
    //
    // @Query("SELECT i FROM Incidence i WHERE i.id = ?1")
    // public Incidence findIncidence(Long id);
}
