package com.acqua.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.acqua.entities.Skill;

/**
 * Repository for manage the {@link Skill} entity
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public interface SkillRepository extends MongoRepository<Skill, String> {
	

}
