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
@Table(name="ASSOCIATE_DETAILS")
public class Associate {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="associate_id",nullable=false, unique=true)
	private Long associateId;
	
	@Column(name="associate_name",nullable=false)
	private String associateName;
	
	@Column(name="email",nullable=true)
	private String email;
	
	@Column(name="mobile",nullable=true)
	private Long mobileNumber;
	
	@Column(name="status_green",nullable=false)
	private boolean statusGreen;
	
	@Column(name="status_blue",nullable=false)
	private boolean statusBlue;
	
	@Column(name="status_red",nullable=false)
	private boolean statusRed;
	
	@Column(name="level1",nullable=false)
	private boolean level1;
	
	@Column(name="level2",nullable=false)
	private boolean level2;
	
	@Column(name="level3",nullable=false)
	private boolean level3;
	
	@Column(name="remark",nullable=true)
	private String remark;
	
	@Column(name="strength",nullable=true)
	private String strength;
	
	@Column(name="weakness",nullable=true)
	private String weakness;
	
	@Column(name="gender",nullable=false)
	private String gender;
	
	@OneToMany(mappedBy="associate",fetch=FetchType.LAZY)
	private Set<AssociateSkills> associateSkillsSet = new HashSet<AssociateSkills>();
	
	public Associate() {
		
	}
	
	public Associate(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssociateId() {
		return associateId;
	}

	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isStatusGreen() {
		return statusGreen;
	}

	public void setStatusGreen(boolean statusGreen) {
		this.statusGreen = statusGreen;
	}

	public boolean isStatusBlue() {
		return statusBlue;
	}

	public void setStatusBlue(boolean statusBlue) {
		this.statusBlue = statusBlue;
	}

	public boolean isStatusRed() {
		return statusRed;
	}

	public void setStatusRed(boolean statusRed) {
		this.statusRed = statusRed;
	}

	public boolean isLevel1() {
		return level1;
	}

	public void setLevel1(boolean level1) {
		this.level1 = level1;
	}

	public boolean isLevel2() {
		return level2;
	}

	public void setLevel2(boolean level2) {
		this.level2 = level2;
	}

	public boolean isLevel3() {
		return level3;
	}

	public void setLevel3(boolean level3) {
		this.level3 = level3;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getWeakness() {
		return weakness;
	}

	public void setWeakness(String weakness) {
		this.weakness = weakness;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Set<AssociateSkills> getAssociateSkillsSet() {
		return associateSkillsSet;
	}

	public void setAssociateSkillsSet(Set<AssociateSkills> associateSkillsSet) {
		this.associateSkillsSet = associateSkillsSet;
	}
}
