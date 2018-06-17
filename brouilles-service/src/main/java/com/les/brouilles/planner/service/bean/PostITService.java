package com.les.brouilles.planner.service.bean;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.les.brouilles.planner.persistence.model.PostIT;
import com.les.brouilles.planner.persistence.repository.PostITRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PostITService {

	@Autowired
	private PostITRepository repository;

	@Transactional
	public PostIT create(final PostIT entity) {
		log.info("Creation de l'evenement post-it :{}", entity);

		final PostIT created = repository.save(entity);
		return created;
	}

	public List<PostIT> getPostIts() {
		return Lists.newArrayList(repository.findAll());
	}

	public List<PostIT> getPostItWithDateInRange(final Date dateMin, final Date dateMax) {
		return repository.findByDebutAfterAndDebutBeforeOrderByDebutAsc(dateMin, dateMax);
	}

	public PostIT getPostItById(final Long id) {

		return repository.findOne(id);
	}

	@Transactional
	public PostIT update(final PostIT withUpdate) {

		return repository.save(withUpdate);
	}

	@Transactional
	public void delete(final Long id) {
		repository.delete(id);
	}
}
