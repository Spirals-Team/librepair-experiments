package com.cts.iiht.fsd.skilltracker.framework.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ASSOCIATE_SKILL_DETAILS")
public class AssociateSkills {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="associate_id")
	private Associate associate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="skill_id")
	private Skill skill;

	@Column(name="skill_level", nullable=false)
	private Long skillLevel;

	public Associate getAssociate() {
		return associate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAssociate(Associate associate) {
		this.associate = associate;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Long getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(Long skillLevel) {
		this.skillLevel = skillLevel;
	}
}
