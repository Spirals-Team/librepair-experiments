package com.api.campeonatofutebol.repository;

import com.api.campeonatofutebol.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

    Jogador findByNome(String nome);
    
    Optional<List<Jogador>> findByNomeContaining(String nome);
}
