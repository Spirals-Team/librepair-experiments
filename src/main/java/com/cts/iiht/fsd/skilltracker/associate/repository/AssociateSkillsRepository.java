package com.cts.iiht.fsd.skilltracker.associate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.iiht.fsd.skilltracker.framework.entity.AssociateSkills;

@Repository
public interface AssociateSkillsRepository extends JpaRepository<AssociateSkills, Long>{

}
