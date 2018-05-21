package com.hipermidia.campeonato.repository;

import com.hipermidia.campeonato.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

    Jogador findByNome(String nome);
}
