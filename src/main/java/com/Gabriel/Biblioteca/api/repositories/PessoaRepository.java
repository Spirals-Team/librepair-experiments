package com.Gabriel.Biblioteca.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gabriel.Biblioteca.api.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
