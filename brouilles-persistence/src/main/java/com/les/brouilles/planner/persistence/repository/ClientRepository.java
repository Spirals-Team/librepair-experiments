package com.les.brouilles.planner.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.les.brouilles.planner.persistence.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

	List<Client> findByPrenom(String prenom);

	List<Client> findByNom(String nom);

	List<Client> findAllByOrderByNumeroClientAsc();
}