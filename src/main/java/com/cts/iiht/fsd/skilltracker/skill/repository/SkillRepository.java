package com.cts.iiht.fsd.skilltracker.skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.iiht.fsd.skilltracker.framework.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>{

}
