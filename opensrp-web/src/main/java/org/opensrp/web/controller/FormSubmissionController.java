package org.opensrp.web.controller;

import static ch.lambdaj.collection.LambdaCollections.with;
import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.connector.OpenmrsConnector;
import org.opensrp.connector.openmrs.service.EncounterService;
import org.opensrp.connector.openmrs.service.HouseholdService;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.connector.openmrs.service.PatientService;
import org.opensrp.domain.Multimedia;
import org.opensrp.dto.form.FormSubmissionDTO;
import org.opensrp.dto.form.MultimediaDTO;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.service.FormSubmissionConverter;
import org.opensrp.form.service.FormSubmissionService;
import org.opensrp.register.mcare.OpenSRPScheduleConstants.OpenSRPEvent;
import org.opensrp.register.mcare.service.HHService;
import org.opensrp.repository.MultimediaRepository;
import org.opensrp.scheduler.SystemEvent;
import org.opensrp.scheduler.TaskSchedulerService;
import org.opensrp.service.MultimediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ch.lambdaj.function.convert.Converter;

import com.google.gson.Gson;

@Controller
public class FormSubmissionController {
	
	private static Logger logger = LoggerFactory.getLogger(FormSubmissionController.class.toString());
	
	private FormSubmissionService formSubmissionService;
	
	private TaskSchedulerService scheduler;
	
	private EncounterService encounterService;
	
	private OpenmrsConnector openmrsConnector;
	
	private PatientService patientService;
	
	private HouseholdService householdService;
	
	private HHService hhService;
	
	private OpenmrsUserService openmrsUserService;
	
	private MultimediaService multimediaService;
	
	private MultimediaRepository multimediaRepository;
	
	@Autowired
	public FormSubmissionController(FormSubmissionService formSubmissionService, TaskSchedulerService scheduler,
	    EncounterService encounterService, OpenmrsConnector openmrsConnector, PatientService patientService,
	    HouseholdService householdService, MultimediaService multimediaService, OpenmrsUserService openmrsUserService,
	    MultimediaRepository multimediaRepository) {
		this.formSubmissionService = formSubmissionService;
		this.scheduler = scheduler;
		
		this.encounterService = encounterService;
		this.openmrsConnector = openmrsConnector;
		this.patientService = patientService;
		this.householdService = householdService;
		this.hhService = hhService;
		this.openmrsUserService = openmrsUserService;
		this.multimediaService = multimediaService;
		this.multimediaRepository = multimediaRepository;
	}
	
	@RequestMapping(method = GET, value = "/form-submissions")
	@ResponseBody
	private List<FormSubmissionDTO> getNewSubmissionsForANM(@RequestParam("anm-id") String anmIdentifier,
	                                                        @RequestParam("timestamp") Long timeStamp,
	                                                        @RequestParam(value = "batch-size", required = false) Integer batchSize) {
		List<FormSubmission> newSubmissionsForANM = formSubmissionService.getNewSubmissionsForANM(anmIdentifier, timeStamp,
		    batchSize);
		
		logger.info("last sync newform  provider :" + anmIdentifier + " requested timestamp :" + timeStamp + " data size:"
		        + newSubmissionsForANM.size());
		return with(newSubmissionsForANM).convert(new Converter<FormSubmission, FormSubmissionDTO>() {
			
			@Override
			public FormSubmissionDTO convert(FormSubmission submission) {
				return FormSubmissionConverter.from(submission);
			}
		});
	}
	
	@RequestMapping(method = GET, value = "/all-form-submissions")
	@ResponseBody
	private List<FormSubmissionDTO> getAllFormSubmissions(@RequestParam("timestamp") Long timeStamp,
	                                                      @RequestParam(value = "batch-size", required = false) Integer batchSize) {
		List<FormSubmission> allSubmissions = formSubmissionService.getAllSubmissions(timeStamp, batchSize);
		logger.info("last sync form requested timestamp :" + timeStamp + " data size:" + allSubmissions.size());
		return with(allSubmissions).convert(new Converter<FormSubmission, FormSubmissionDTO>() {
			
			@Override
			public FormSubmissionDTO convert(FormSubmission submission) {
				return FormSubmissionConverter.from(submission);
			}
		});
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/form-submissions")
	public ResponseEntity<HttpStatus> submitForms(@RequestBody List<FormSubmissionDTO> formSubmissionsDTO) {
		try {
			if (formSubmissionsDTO.isEmpty()) {
				return new ResponseEntity<>(BAD_REQUEST);
			}
			
			scheduler.notifyEvent(new SystemEvent<>(OpenSRPEvent.FORM_SUBMISSION, formSubmissionsDTO));
			if (formSubmissionsDTO.size() != 0) {
				logger.info("form submission received and registered notify event dtosize: " + formSubmissionsDTO.size());
			}
			
			/*try {
				
				String json = new Gson().toJson(formSubmissionsDTO);
				List<FormSubmissionDTO> formSubmissions = new Gson().fromJson(json,
				    new TypeToken<List<FormSubmissionDTO>>() {}.getType());
				
				List<FormSubmission> fsl = with(formSubmissions).convert(new Converter<FormSubmissionDTO, FormSubmission>() {
					
					@Override
					public FormSubmission convert(FormSubmissionDTO submission) {
						return FormSubmissionConverter.toFormSubmission(submission);
					}
				});
				for (FormSubmission formSubmission : fsl) {
					if (openmrsConnector.isOpenmrsForm(formSubmission)) {
						
						JSONObject p = patientService.getPatientByIdentifier(formSubmission.entityId());
						JSONObject r = patientService.getPatientByIdentifier(formSubmission.getField("relationalid"));
						
						if (p != null || r != null) { // HO           		
							logger.debug("existing patient found into openmrs with id : " + p == null ? formSubmission
							        .getField("relationalid") : formSubmission.entityId());
							Event e;
							Map<String, Map<String, Object>> dep;
							dep = openmrsConnector.getDependentClientsFromFormSubmission(formSubmission);
							if (dep.size() > 0) { //HOW(n)
								logger.info("dependent client exist into formsubmission ");
								for (Map<String, Object> cm : dep.values()) {
									patientService.createPatient((Client) cm.get("client"));
									encounterService.createEncounter((Event) cm.get("event"));
								}
							}
							//HOW(0)
							e = openmrsConnector.getEventFromFormSubmission(formSubmission);
							logger.info("Creates encounter for client id: " + e.getBaseEntityId());
							encounterService.createEncounter(e);
						} else { //Hn
							Map<String, Map<String, Object>> dep;
							dep = openmrsConnector.getDependentClientsFromFormSubmission(formSubmission);
							if (dep.size() > 0) { //HnW(n)
								logger.info("dependent client exist into formsubmission ");
								Client hhhClient = openmrsConnector.getClientFromFormSubmission(formSubmission);
								Event hhhEvent = openmrsConnector.getEventFromFormSubmission(formSubmission);
								OpenmrsHouseHold hh = new OpenmrsHouseHold(hhhClient, hhhEvent);
								for (Map<String, Object> cm : dep.values()) {
									hh.addHHMember((Client) cm.get("client"), (Event) cm.get("event"));
								}
								householdService.saveHH(hh);
							} else {//HnW(0)
								logger.info("patient and dependent client not exist into openmrs  ");
								Client c = openmrsConnector.getClientFromFormSubmission(formSubmission);
								patientService.createPatient(c);
								Event e = openmrsConnector.getEventFromFormSubmission(formSubmission);
								encounterService.createEncounter(e);
							}
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}*/
			
			logger.debug(format("Added Form submissions to queue.\nSubmissions: {0}", formSubmissionsDTO));
		}
		catch (Exception e) {
			logger.error(format("Form submissions processing failed with exception {0}.\nSubmissions: {1}", e,
			    formSubmissionsDTO));
			return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(CREATED);
	}
	
	@RequestMapping(method = GET, value = "/entity-id")
	@ResponseBody
	public ResponseEntity<String> getEntityIdForBRN(@RequestParam("brn-id") List<String> brnIdList) {
		return new ResponseEntity<>(new Gson().toJson(hhService.getEntityIdBybrnId(brnIdList)), OK);
	}
	
	@RequestMapping(method = GET, value = "/user-location")
	@ResponseBody
	public ResponseEntity<String> getUserByLocation(@RequestParam("location-name") String locationName) {
		JSONObject usersAssignedToLocation = null;
		String userName = "";
		try {
			usersAssignedToLocation = openmrsUserService.getTeamMemberByLocation(locationName);
			userName = usersAssignedToLocation.getJSONArray("results").getJSONObject(0).getJSONObject("user")
			        .getString("username");
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(userName, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = GET, value = "/multimedia-file")
	@ResponseBody
	public List<MultimediaDTO> getFiles(@RequestParam("anm-id") String providerId) {
		
		List<Multimedia> allMultimedias = multimediaService.getMultimediaFiles(providerId);
		
		return with(allMultimedias).convert(new Converter<Multimedia, MultimediaDTO>() {
			
			@Override
			public MultimediaDTO convert(Multimedia md) {
				return new MultimediaDTO(md.getCaseId(), md.getProviderId(), md.getContentType(), md.getFilePath(), md
				        .getFileCategory());
			}
		});
	}
	
	@RequestMapping(headers = { "Accept=multipart/form-data" }, method = POST, value = "/multimedia-file")
	public ResponseEntity<String> uploadFiles(@RequestParam("anm-id") String providerId,
	                                          @RequestParam("entity-id") String entityId,
	                                          @RequestParam("content-type") String contentType,
	                                          @RequestParam("file-category") String fileCategory,
	                                          @RequestParam("file") MultipartFile file) throws ClientProtocolException,
	    IOException {
		MultimediaDTO multimediaDTO = new MultimediaDTO(entityId, providerId, contentType, null, fileCategory);
		String status = multimediaService.saveMultimediaFile(multimediaDTO, file);
		
		if (status.equals("success")) {
			Multimedia multimedia = multimediaRepository.findByCaseId(entityId);
			patientService.patientImageUpload(multimedia);
		}
		return new ResponseEntity<>(new Gson().toJson(status), OK);
	}
	
	public ResponseEntity<String> sendToOpmenMRS() {
		
		return new ResponseEntity<>("", OK);
	}
}
