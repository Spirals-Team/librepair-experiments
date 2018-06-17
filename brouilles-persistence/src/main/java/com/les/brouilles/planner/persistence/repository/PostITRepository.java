package com.les.brouilles.planner.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.les.brouilles.planner.persistence.model.PostIT;

public interface PostITRepository extends CrudRepository<PostIT, Long> {

	List<PostIT> findByNom(String nom);

	List<PostIT> findAllByOrderByDebutAsc();

	List<PostIT> findByDebutAfterAndDebutBeforeOrderByDebutAsc(Date dateMin, Date dateMax);

}