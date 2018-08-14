package com.Gabriel.Biblioteca.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gabriel.Biblioteca.api.entities.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer>{

}
