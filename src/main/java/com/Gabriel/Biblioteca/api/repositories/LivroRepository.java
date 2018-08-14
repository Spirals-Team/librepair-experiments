package com.Gabriel.Biblioteca.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gabriel.Biblioteca.api.entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer>{

}
