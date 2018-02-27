/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.api.db.hibernate;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openmrs.CodedOrFreeText;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.Condition;
import org.openmrs.api.db.ConditionDAO;
import org.openmrs.ConditionClinicalStatus;
import org.openmrs.ConditionVerificationStatus;
import org.openmrs.Patient;
import org.openmrs.test.BaseContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateConditionDAOTest extends BaseContextSensitiveTest {
	
	private static final String CONDITIONS_XML = "org/openmrs/api/db/hibernate/include/HibernateConditionDAOTestDataSet.xml";
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	ConditionDAO dao;
	
	
	@Before
	public void setUp() {
		executeDataSet(CONDITIONS_XML);
		
		updateSearchIndex();
	}
	
	@Test
	public void shouldSaveCondition() {
		CodedOrFreeText codedOrFreeText = new CodedOrFreeText(new Concept(79008778), new ConceptName(590009), "non coded");
		ConditionClinicalStatus clinicalStatus = ConditionClinicalStatus.ACTIVE;
		ConditionVerificationStatus verificationStaus = ConditionVerificationStatus.CONFIRMED;
		Condition previousCondition = new Condition();
		Patient patient = new Patient(48970);
		Date onsetDate = new Date();
		Date endDate = new Date();
		Condition condition = new Condition(codedOrFreeText, clinicalStatus, verificationStaus, previousCondition, "additional detail",
				onsetDate, endDate, patient);
		Condition savedCondition = dao.saveCondition(condition);	
		assertEquals(savedCondition.getClinicalStatus(), clinicalStatus);
		assertEquals(savedCondition.getVerificationStatus(), verificationStaus);
	}
	
	@Test
	public void shouldGetConditionByUuid() {
		String uuid = "2cc6880e-2c46-15e4-9038-a6c5e4d22fb7";
		ConditionClinicalStatus expectedClinicalStatus = ConditionClinicalStatus.INACTIVE;
		ConditionVerificationStatus expectedVerificationStatus = ConditionVerificationStatus.PROVISIONAL;
		
		Condition condition = dao.getConditionByUuid(uuid);
		assertEquals(condition.getClinicalStatus(), expectedClinicalStatus);
		assertEquals(condition.getVerificationStatus(), expectedVerificationStatus);
		assertEquals(condition.getUuid(), uuid);
		
	}
	
	@Test
	public void shouldGetConditionHistory() {
		Patient patient = new Patient(2);
		
		List<Condition> history = dao.getConditionHistory(patient);
		
		assertEquals(history.size(), 2);
		
		assertEquals(history.get(0).getClinicalStatus(), ConditionClinicalStatus.INACTIVE);
		assertEquals(history.get(0).getVerificationStatus(), ConditionVerificationStatus.PROVISIONAL);
		
		assertEquals(history.get(1).getClinicalStatus(), ConditionClinicalStatus.ACTIVE);
		assertEquals(history.get(1).getVerificationStatus(), ConditionVerificationStatus.CONFIRMED);
	}
	
	@Test
	public void shouldGetActiveConditions() {
		Patient patient = new Patient(2);
		
		List<Condition> active = dao.getActiveConditions(patient);
		
		assertEquals(active.size(), 2);
		assertEquals(active.get(0).getClinicalStatus(), ConditionClinicalStatus.INACTIVE);
		assertEquals(active.get(0).getVerificationStatus(), ConditionVerificationStatus.PROVISIONAL);	
		assertEquals(active.get(1).getClinicalStatus(), ConditionClinicalStatus.ACTIVE);
		assertEquals(active.get(1).getVerificationStatus(), ConditionVerificationStatus.CONFIRMED);
		
		Patient secondPatient = new Patient(8);
		
		List<Condition> active2 = dao.getActiveConditions(secondPatient);
		
		assertEquals(active2.size(), 1);
		assertEquals(active2.get(0).getClinicalStatus(), ConditionClinicalStatus.ACTIVE);
		assertEquals(active2.get(0).getVerificationStatus(), ConditionVerificationStatus.CONFIRMED);	
	}
	
	
	
}