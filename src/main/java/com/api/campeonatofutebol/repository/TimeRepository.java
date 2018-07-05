package com.api.campeonatofutebol.repository;

import com.api.campeonatofutebol.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<Time, Integer> {

    Time findByNome(String nome);

    Optional<List<Time>> findByNomeContaining(String nome);
    
}
