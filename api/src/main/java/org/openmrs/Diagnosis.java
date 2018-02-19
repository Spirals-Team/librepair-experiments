/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs;

/**
 * Diagnosis class defines the identification of the nature of an illness or other problem 
 * by examination of the symptoms during an encounter (visit or interaction of a patient with a healthcare worker).
 */

public class Diagnosis extends BaseChangeableOpenmrsData{

	public static final long serialVersionUID = 2L;
	
	private Integer diagnosisId;
	
	private Encounter encounter;
	
	private CodedOrFreeText diagnosis;
	
	private Condition condition;

	private ConditionVerificationStatus certainty;
	
	private Integer rank;
	
	public Diagnosis() {
	}

	/**
	 * Diagnosis constructor with encounter, CodedOrFreeText, ConditionVerificationStatus, and rank
	 *
	 * @param encounter the encounter during which the diagnosis was made
	 * @param diagnosis the CodedOrFreeText of the diagnosis
	 * @param certainty the ConditionVerificationStatus of the diagnosis
	 * @param rank the rank of the diagnosis
	 */
	
	public Diagnosis(Encounter encounter, CodedOrFreeText diagnosis, ConditionVerificationStatus certainty, Integer rank) {
		this.encounter = encounter;
		this.diagnosis = diagnosis;
		this.certainty = certainty;
		this.rank = rank;
	}
	
	/**
	 * Gets the diagnosisId.
	 * 
	 * @return the diagnosisId of the current diagnosis
	 */
	@Override
	public Integer getId() {
		return diagnosisId;
	}

	/**
	 * Sets diagnosis diagnosisId
	 * 
	 * @param diagnosisId the diagnosisId to set 
	 */
	@Override
	public void setId(Integer diagnosisId) {
		this.diagnosisId = diagnosisId;
	}

	/**
	 * Gets the encounter associated with this diagnosis.
	 * 
	 * @return encounter associated with this diagnosis
	 */
	public Encounter getEncounter() {
		return encounter;
	}

	/**
	 * Sets the encounter associated with this diagnosis.
	 * 
	 * @param encounter the encounter object to set when this diagnosis was made
	 */
	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}

	/**
	 * Gets the diagnosis.
	 * 
	 * @return diagnosis the CodedOrFreeText associated with this diagnosis
	 */
	public CodedOrFreeText getDiagnosis() {
		return diagnosis;
	}

	/**
	 * Sets the CodedOrFreeText diagnosis.
	 * 
	 * @param diagnosis the CodedOrFreeText to set associated with this diagnosis
	 */
	public void setDiagnosis(CodedOrFreeText diagnosis)  {
		this.diagnosis = diagnosis;
	}

	/**
	 * Gets the diagnosis certainty.
	 * 
	 * @return certainty the ConditionVerificationStatus of this Diagnosis
	 */
	public ConditionVerificationStatus getCertainty() {
		return certainty;
	}

	/**
	 * Sets the diagnosis certainty
	 * 
	 * @param certainty the ConditionVerificationStatus to set for this Diagnosis
	 */
	public void setCertainty(ConditionVerificationStatus certainty)  {
		this.certainty = certainty;
	}

	/**
	 * Gets the diagnosis rank.
	 * 
	 * @return the rank of the diagnosis
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * Sets diagnosis rank
	 * 
	 * @param rank the Integer to set as the rank for this diagnosis.
	 */
	public void setRank(Integer rank)   {
		this.rank = rank;
	}

	/**
	 * Gets the diagnosis condition.
	 * 
	 * @return condition that this Diagnosis is associated with.
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * Sets diagnosis condition
	 * 
	 * @param condition the Condition to set associated with this Diagnosis.
	 */
	public void setCondition(Condition condition)   {
		this.condition = condition;
	}
}
