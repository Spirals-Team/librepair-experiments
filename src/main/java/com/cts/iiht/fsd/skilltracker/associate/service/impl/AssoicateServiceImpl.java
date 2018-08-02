package com.cts.iiht.fsd.skilltracker.associate.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cts.iiht.fsd.skilltracker.associate.repository.AssociateRepository;
import com.cts.iiht.fsd.skilltracker.associate.repository.AssociateSkillsRepository;
import com.cts.iiht.fsd.skilltracker.associate.service.AssociateService;
import com.cts.iiht.fsd.skilltracker.associate.to.AssociateSkillsTO;
import com.cts.iiht.fsd.skilltracker.associate.to.AssociateTO;
import com.cts.iiht.fsd.skilltracker.framework.entity.Associate;
import com.cts.iiht.fsd.skilltracker.framework.entity.AssociateSkills;
import com.cts.iiht.fsd.skilltracker.framework.entity.Skill;
import com.cts.iiht.fsd.skilltracker.skill.repository.SkillRepository;

@Service
public class AssoicateServiceImpl implements AssociateService{
	
	@Autowired
	private AssociateRepository associateRepository;
	
	@Autowired
	private AssociateSkillsRepository associateSkillsRepository;
	
	@Autowired
	private SkillRepository skillRepository;

	@Override
	public AssociateTO getAssociatesList() {
		AssociateTO associateTO = new AssociateTO();
		List<AssociateTO> associatesList = new ArrayList<AssociateTO>();
		List<Object[]> associateResults = associateRepository.getAssociatesDetailsList();
		if(associateResults != null && !associateResults.isEmpty()) {
			Iterator<Object[]> associateResultsItr = associateResults.iterator();
			while(associateResultsItr.hasNext()) {
				Object[] associateDtls = associateResultsItr.next();
				AssociateTO associate = new AssociateTO();
				associate.setId(((BigInteger)associateDtls[0]).longValue());
				associate.setAssociateId(((BigInteger)associateDtls[1]).longValue());
				associate.setAssociateName((String)associateDtls[2]);
				associate.setEmail((String)associateDtls[3]);
				associate.setMobile(((BigInteger)associateDtls[4]).longValue());
				boolean statusBlue = (boolean) associateDtls[5];
				boolean statusGreen = (boolean) associateDtls[6];
				boolean statusRed = (boolean) associateDtls[7];
				if(statusBlue) {
					associate.setStatus("blue");
				} else if(statusGreen) {
					associate.setStatus("green");
				} else if(statusRed) {
					associate.setStatus("red");
				}
				associate.setSkills((String) associateDtls[8]);
				associatesList.add(associate);
			}
		}
		Double associatesCount = new Double(associatesList.size());
		Double percentObj = new Double(100);
		List<Object[]> candidatesByGenderResult = associateRepository.getCandidatesCountByGender();
		if(candidatesByGenderResult != null && !candidatesByGenderResult.isEmpty()) {
			Iterator<Object[]> candidatesByGenderResultsItr = candidatesByGenderResult.iterator();
			while(candidatesByGenderResultsItr.hasNext()) {
				Object[] candidatesByGender = candidatesByGenderResultsItr.next();
				String gender = (String) candidatesByGender[0];
				if(gender.equalsIgnoreCase("MALE")) {
					Double maleCount = new BigDecimal((BigInteger) candidatesByGender[1]).doubleValue();
					Double malePercentage = new Double(0);
					if(associatesCount != null && associatesCount.intValue() != 0) {
						malePercentage = (maleCount/associatesCount) * percentObj;
					}
					associateTO.setMalePercentage(malePercentage.toString());
				} else if(gender.equalsIgnoreCase("FEMALE")) {
					Double femaleCount = new BigDecimal((BigInteger) candidatesByGender[1]).doubleValue();
					Double femalePercentage = new Double(0);
					if(associatesCount != null && associatesCount.intValue() != 0) {
						femalePercentage = (femaleCount/associatesCount) *percentObj;
					}
					associateTO.setFemalePercentage(femalePercentage.toString());
				}
			}
		} else {
			associateTO.setMalePercentage("0");
			associateTO.setFemalePercentage("0");
		}
		Object[] freshersResult =  associateRepository.getFreshersCount();
		if(freshersResult != null) {
			Double freshersCount = new BigDecimal((BigInteger) freshersResult[0]).doubleValue();
			Double freshersPercentage = new Double(0);
			if(associatesCount != null && associatesCount.intValue() != 0) {
				freshersPercentage = (freshersCount/associatesCount) * percentObj;
			}
			associateTO.setFreshersPercentage(freshersPercentage.toString());
		} else {
			associateTO.setFreshersPercentage("0");
		}
		List<Object[]> ratedCandidatesByGenderResult = associateRepository.getRatedCandidatesByGender();
		if(ratedCandidatesByGenderResult != null && !ratedCandidatesByGenderResult.isEmpty()) {
			Iterator<Object[]> ratedCandidatesByGenderResultsItr = ratedCandidatesByGenderResult.iterator();
			Double maleCount = new Double(0);
			Double femaleCount = new Double(0);
			Double totalCount = new Double(0);
			Double malePercentage = new Double(0);
			Double femalePercentage = new Double(0);
			while(ratedCandidatesByGenderResultsItr.hasNext()) {
				Object[] ratedCandidatesByGender = ratedCandidatesByGenderResultsItr.next();
				String gender = (String) ratedCandidatesByGender[0];
				if(gender.equalsIgnoreCase("MALE")) {
					maleCount = new BigDecimal((BigInteger) ratedCandidatesByGender[1]).doubleValue();
				} else if(gender.equalsIgnoreCase("FEMALE")) {
					femaleCount = new BigDecimal((BigInteger) ratedCandidatesByGender[1]).doubleValue();
				}
			}
			totalCount = maleCount + femaleCount;
			if(totalCount != null && totalCount.intValue() != 0) {
				malePercentage = (maleCount/totalCount) * percentObj;
				femalePercentage = (femaleCount/totalCount) * percentObj;
			}
			associateTO.setRatedAssociatesCount(totalCount.toString());
			associateTO.setMaleRatedPercentage(malePercentage.toString());
			associateTO.setFemaleRatedPercentage(femalePercentage.toString());
		} else {
			associateTO.setRatedAssociatesCount("0");
			associateTO.setMaleRatedPercentage("0");
			associateTO.setFemaleRatedPercentage("0");
		}
		Object[] level1CandidatesResult =  associateRepository.getLevel1CandidatesCount();
		if(level1CandidatesResult != null) {
			Double level1Count = new BigDecimal((BigInteger) level1CandidatesResult[0]).doubleValue();
			Double level1Percentage = new Double(0);
			if(associatesCount != null && associatesCount.intValue() != 0) {
				level1Percentage = (level1Count/associatesCount) * percentObj;
			}
			associateTO.setLevel1Percentage(level1Percentage.toString());
		} else {
			associateTO.setLevel1Percentage("0");
		}
		Object[] level2CandidatesResult =  associateRepository.getLevel2CandidatesCount();
		if(level2CandidatesResult != null) {
			Double level2Count = new BigDecimal((BigInteger) level2CandidatesResult[0]).doubleValue();
			Double level2Percentage = new Double(0);
			if(associatesCount != null && associatesCount.intValue() != 0) {
				level2Percentage = (level2Count/associatesCount) * percentObj;
			}
			associateTO.setLevel2Percentage(level2Percentage.toString());
		} else {
			associateTO.setLevel2Percentage("0");
		}
		Object[] level3CandidatesResult =  associateRepository.getLevel3CandidatesCount();
		if(level3CandidatesResult != null) {
			Double level3Count = new BigDecimal((BigInteger) level3CandidatesResult[0]).doubleValue();
			Double level3Percentage = new Double(0);
			if(associatesCount != null && associatesCount.intValue() != 0) {
				level3Percentage = (level3Count/associatesCount) * percentObj;
			}
			associateTO.setLevel3Percentage(level3Percentage.toString());
		} else {
			associateTO.setLevel3Percentage("0");
		}
		associateTO.setAssociatesList(associatesList);
		associateTO.setAssociatesCount(((Integer)associatesList.size()).toString());
		return associateTO;
	}
	
	@Override
	public AssociateTO getAssociateById(Long associateId) {
		AssociateTO associateTO = new AssociateTO();
		Optional<Associate> associate = associateRepository.findById(associateId);
		if(associate != null && associate.isPresent()) {
			Associate associateDtls = associate.get();
			associateTO.setId(associateDtls.getId());
			associateTO.setAssociateId(associateDtls.getAssociateId());
			associateTO.setAssociateName(associateDtls.getAssociateName());
			associateTO.setEmail(associateDtls.getEmail());
			associateTO.setMobile(associateDtls.getMobileNumber());
			associateTO.setGender(associateDtls.getGender());
			if(associateDtls.isLevel1()) {
				associateTO.setLevel("LEVEL_1");
			} else if(associateDtls.isLevel2()) {
				associateTO.setLevel("LEVEL_2");
			} else if(associateDtls.isLevel3()) {
				associateTO.setLevel("LEVEL_3");
			}
			if(associateDtls.isStatusBlue()) {
				associateTO.setStatus("blue");
			} else if(associateDtls.isStatusGreen()) {
				associateTO.setStatus("green");
			}else if(associateDtls.isStatusRed()) {
				associateTO.setStatus("red");
			}
			associateTO.setRemark(associateDtls.getRemark());
			associateTO.setStrength(associateDtls.getStrength());
			associateTO.setWeakness(associateDtls.getWeakness());
			List<Object[]> associateSkillsResult = associateRepository.getSkillsByAssociateId(associateDtls.getId());
			List<AssociateSkillsTO> associateSkillsTOList = new ArrayList<AssociateSkillsTO>();
			List<Skill> skillsList = skillRepository.findAll();
			if(skillsList != null && !skillsList.isEmpty()) {
				Iterator<Skill> skillListItr = skillsList.iterator();
				while(skillListItr.hasNext()) {
					Skill skill = skillListItr.next();
					AssociateSkillsTO associateSkillTO = new AssociateSkillsTO();
					associateSkillTO.setSkillId(skill.getSkillId());
					associateSkillTO.setSkillName(skill.getSkillName());
					boolean skillFound = false;
					if(associateSkillsResult != null && !associateSkillsResult.isEmpty()) {
						Iterator<Object[]> associateSkillsResultItr = associateSkillsResult.iterator();
						while(associateSkillsResultItr.hasNext()) {
							Object[] associateSkillsObj = associateSkillsResultItr.next();
							if(skill.getSkillId() == ((BigInteger)associateSkillsObj[0]).longValue()) {
								associateSkillTO.setSkillLevel(((BigInteger)associateSkillsObj[2]).longValue());
								skillFound = true;
								break;
							}
						}
					}
					if(!skillFound) {
						associateSkillTO.setSkillLevel(0L);
					}
					associateSkillsTOList.add(associateSkillTO);
				}
			}
			
			associateTO.setAssociateSkillsList(associateSkillsTOList);
		}
		return associateTO;
	}

	@Override	
	public Long saveAssociate(AssociateTO associateTO) {
		Associate associate = null;
		if(StringUtils.isEmpty(associateTO.getId())) {
			associate = new Associate();
		} else {
			Optional<Associate> associateObj = associateRepository.findById(associateTO.getId());
			if(associateObj.isPresent()) {
				associate = associateObj.get();
			} else {
				associate = new Associate();
			}
		}
		associate.setAssociateId(associateTO.getAssociateId());
		associate.setAssociateName(associateTO.getAssociateName());
		associate.setEmail(associateTO.getEmail());
		associate.setMobileNumber(associateTO.getMobile());
		associate.setRemark(associateTO.getRemark());
		associate.setGender(associateTO.getGender());
		associate.setStrength(associateTO.getStrength());
		associate.setWeakness(associateTO.getWeakness());
		if(associateTO.getLevel().equalsIgnoreCase("LEVEL_1")) {
			associate.setLevel1(true);
			associate.setLevel2(false);
			associate.setLevel3(false);
		} else if(associateTO.getLevel().equalsIgnoreCase("LEVEL_2")) {
			associate.setLevel1(false);
			associate.setLevel2(true);
			associate.setLevel3(false);
		} else if(associateTO.getLevel().equalsIgnoreCase("LEVEL_3")) {
			associate.setLevel1(false);
			associate.setLevel2(false);
			associate.setLevel3(true);
		}
		if(associateTO.getStatus().equalsIgnoreCase("blue")) {
			associate.setStatusBlue(true);
			associate.setStatusGreen(false);
			associate.setStatusRed(false);
		} else if(associateTO.getStatus().equalsIgnoreCase("green")) {
			associate.setStatusBlue(false);
			associate.setStatusGreen(true);
			associate.setStatusRed(false);
		} else if(associateTO.getStatus().equalsIgnoreCase("red")) {
			associate.setStatusBlue(false);
			associate.setStatusGreen(false);
			associate.setStatusRed(true);
		}
		Associate savedAssociateObj = associateRepository.save(associate);
		if(associate.getAssociateSkillsSet() != null && !associate.getAssociateSkillsSet().isEmpty()) {
			associateSkillsRepository.deleteAll(associate.getAssociateSkillsSet());
		}
		if(associateTO.getAssociateSkillsList() != null && !associateTO.getAssociateSkillsList().isEmpty()) {
			Iterator<AssociateSkillsTO> associateSkillItr = associateTO.getAssociateSkillsList().iterator();
			while(associateSkillItr.hasNext()) {
				AssociateSkillsTO associateSkills = associateSkillItr.next();
				if(associateSkills.getSkillLevel() > 0) {
					AssociateSkills associateSkill = new AssociateSkills();
					associateSkill.setAssociate(new Associate(savedAssociateObj.getId()));
					associateSkill.setSkill(new Skill(associateSkills.getSkillId()));
					associateSkill.setSkillLevel(associateSkills.getSkillLevel());
					associateSkillsRepository.save(associateSkill);
				}
			}
		}
		return savedAssociateObj.getId();
	}

	@Override
	public boolean deleteAssociate(Long associateId) {
		Associate associate = null;
		Optional<Associate> associateObj = associateRepository.findById(associateId);
		if(associateObj.isPresent()) {
			associate = associateObj.get();
		}
		if(associate == null) {
			return false;
		} else {
			if(associate.getAssociateSkillsSet() != null && !associate.getAssociateSkillsSet().isEmpty()) {
				associateSkillsRepository.deleteAll(associate.getAssociateSkillsSet());
			}
			associateRepository.delete(associate);
		}
		return true;
	}
	
	public AssociateTO getAssociateSkills() {
		AssociateTO associateTO = new AssociateTO();
		List<Skill> skillsList = skillRepository.findAll();
		List<AssociateSkillsTO> associateSkillsTOList = new ArrayList<AssociateSkillsTO>();
		if(skillsList != null && !skillsList.isEmpty()) {
			Iterator<Skill> skillListItr = skillsList.iterator();
			while(skillListItr.hasNext()) {
				Skill skill = skillListItr.next();
				AssociateSkillsTO associateSkillTO = new AssociateSkillsTO();
				associateSkillTO.setSkillId(skill.getSkillId());
				associateSkillTO.setSkillName(skill.getSkillName());
				associateSkillTO.setSkillLevel(0L);
				associateSkillsTOList.add(associateSkillTO);
			}
		}
		associateTO.setAssociateSkillsList(associateSkillsTOList);
		return associateTO;
	}
}
