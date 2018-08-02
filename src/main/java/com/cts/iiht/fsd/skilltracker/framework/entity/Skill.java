package com.cts.iiht.fsd.skilltracker.framework.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SKILL_DETAILS")
public class Skill {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="skill_id")
	private Long skillId;
	
	@Column(name="skill_name",nullable=false)
	private String skillName;
	
	@OneToMany(mappedBy="skill",fetch=FetchType.LAZY)
	private Set<AssociateSkills> skillsAssociatesSet = new HashSet<AssociateSkills>();
	
	public Skill() {
		
	}
	
	public Skill(Long skillId) {
		this.skillId = skillId;
	}

	public Long getSkillId() {
		return skillId;
	}

	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Set<AssociateSkills> getSkillsAssociatesSet() {
		return skillsAssociatesSet;
	}

	public void setSkillsAssociatesSet(Set<AssociateSkills> skillsAssociatesSet) {
		this.skillsAssociatesSet = skillsAssociatesSet;
	}
}
