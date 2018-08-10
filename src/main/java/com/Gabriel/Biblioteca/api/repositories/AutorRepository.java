package com.Gabriel.Biblioteca.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gabriel.Biblioteca.api.entities.Autor;

public interface AutorRepository extends JpaRepository<Autor, Integer> {

}
