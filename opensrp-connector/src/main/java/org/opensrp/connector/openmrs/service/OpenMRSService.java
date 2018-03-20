package org.opensrp.connector.openmrs.service;

import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.Client;
import org.opensrp.api.domain.Event;
import org.opensrp.connector.OpenmrsConnector;
import org.opensrp.connector.openmrs.constants.OpenmrsHouseHold;
import org.opensrp.form.domain.FormSubmission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenMRSService {
	
	private static Logger logger = LoggerFactory.getLogger(OpenMRSService.class.toString());
	
	@Autowired
	private EncounterService encounterService;
	
	@Autowired
	private OpenmrsConnector openmrsConnector;
	
	@Autowired
	private HouseholdService householdService;
	
	@Autowired
	private PatientService patientService;
	
	public OpenMRSService() {
		
	}
	
	public void sendDataToOpenMRS(FormSubmission formSubmission) {
		
		try {
			if (openmrsConnector.isOpenmrsForm(formSubmission)) {
				
				JSONObject p = patientService.getPatientByIdentifier(formSubmission.entityId());
				JSONObject r = patientService.getPatientByIdentifier(formSubmission.getField("relationalid"));
				
				if (p != null || r != null) {
					logger.debug("existing patient found into openmrs with id : " + p == null ? formSubmission
					        .getField("relationalid") : formSubmission.entityId());
					Event e;
					Map<String, Map<String, Object>> dep;
					dep = openmrsConnector.getDependentClientsFromFormSubmission(formSubmission);
					if (dep.size() > 0) {
						logger.info("dependent client exist into formsubmission ");
						for (Map<String, Object> cm : dep.values()) {
							patientService.createPatient((Client) cm.get("client"));
							encounterService.createEncounter((Event) cm.get("event"));
						}
					}
					e = openmrsConnector.getEventFromFormSubmission(formSubmission);
					logger.info("Creates encounter for client id: " + e.getBaseEntityId());
					encounterService.createEncounter(e);
				} else {
					Map<String, Map<String, Object>> dep;
					dep = openmrsConnector.getDependentClientsFromFormSubmission(formSubmission);
					if (dep.size() > 0) {
						logger.info("dependent client exist into formsubmission ");
						Client hhhClient = openmrsConnector.getClientFromFormSubmission(formSubmission);
						Event hhhEvent = openmrsConnector.getEventFromFormSubmission(formSubmission);
						OpenmrsHouseHold hh = new OpenmrsHouseHold(hhhClient, hhhEvent);
						for (Map<String, Object> cm : dep.values()) {
							hh.addHHMember((Client) cm.get("client"), (Event) cm.get("event"));
						}
						householdService.saveHH(hh);
					} else {
						logger.info("patient and dependent client not exist into openmrs  ");
						Client c = openmrsConnector.getClientFromFormSubmission(formSubmission);
						patientService.createPatient(c);
						Event e = openmrsConnector.getEventFromFormSubmission(formSubmission);
						encounterService.createEncounter(e);
					}
				}
			}
		}
		catch (JSONException e) {
			logger.error(MessageFormat.format("{0} occurred while trying to fetch forms. Message: {1} with stack trace {2}",
			    e.getCause(), e.getMessage(), getFullStackTrace(e)));
		}
		catch (ParseException e) {
			logger.error(MessageFormat.format("{0} occurred while trying to fetch forms. Message: {1} with stack trace {2}",
			    e.getCause(), e.getMessage(), getFullStackTrace(e)));
		}
		
	}
}
