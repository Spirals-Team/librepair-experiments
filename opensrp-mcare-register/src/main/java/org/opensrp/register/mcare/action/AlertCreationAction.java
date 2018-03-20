package org.opensrp.register.mcare.action;

import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.SCHEDULE_BNF_IME;
import static org.opensrp.dto.BeneficiaryType.child;
import static org.opensrp.dto.BeneficiaryType.elco;
import static org.opensrp.dto.BeneficiaryType.household;
import static org.opensrp.dto.BeneficiaryType.mother;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.opensrp.common.AllConstants.BnfFollowUpVisitFields;
import org.opensrp.common.AllConstants.ELCOSchedulesConstantsImediate;
import org.opensrp.domain.Multimedia;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.MultimediaRegisterService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.HookedEvent;
import org.opensrp.scheduler.MilestoneEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("AlertCreationAction")
public class AlertCreationAction implements HookedEvent {
	
	private HealthSchedulerService scheduler;
	
	private AllHouseHolds allHouseHolds;
	
	private AllElcos allElcos;
	
	private AllMothers allMothers;
	
	private AllChilds allChilds;
	
	private ScheduleLogService scheduleLogService;
	
	private MultimediaRegisterService multimediaRegisterService;
	
	private static Logger logger = LoggerFactory.getLogger(AlertCreationAction.class.toString());
	
	@Autowired
	public AlertCreationAction(HealthSchedulerService scheduler, AllHouseHolds allHouseHolds, AllElcos allElcos,
	    AllMothers allMothers, AllChilds allChilds, ScheduleLogService scheduleLogService,
	    MultimediaRegisterService multimediaRegisterService) {
		this.scheduler = scheduler;
		this.allHouseHolds = allHouseHolds;
		this.allElcos = allElcos;
		this.allMothers = allMothers;
		this.allChilds = allChilds;
		this.scheduleLogService = scheduleLogService;
		this.multimediaRegisterService = multimediaRegisterService;
	}
	
	@Override
	public void invoke(MilestoneEvent event, Map<String, String> extraData) {
		BeneficiaryType beneficiaryType = BeneficiaryType.from(extraData.get("beneficiaryType"));
		
		// TODO: Get rid of this horrible if-else after Motech-Platform fixes
		// the bug related to metadata in motech-schedule-tracking.
		logger.info(
		    "receving motech event in invoke, event: " + event.externalId() + " milestoneName:" + event.milestoneName()
		            + " windowName: " + event.windowName() + " startOfDueWindow :" + " startOfDueWindow : "
		            + event.startOfDueWindow() + " startOfLateWindow: " + event.startOfLateWindow(),
		    event.startOfMaxWindow());
		String instanceId = null;
		String providerId = null;
		String caseID = event.externalId();
		DateTime startOfEarliestWindow = new DateTime();
		String doo = "";
		
		String DateString = "";
		if (household.equals(beneficiaryType)) {
			HouseHold houseHold = allHouseHolds.findByCaseId(caseID);
			if (houseHold != null) {
				instanceId = houseHold.INSTANCEID();
				providerId = houseHold.PROVIDERID();
				startOfEarliestWindow = parseDate(houseHold.TODAY());
			}
		} else if (elco.equals(beneficiaryType)) {
			
			Elco elco = allElcos.findByCaseId(caseID);
			
			if (elco != null) {
				instanceId = elco.INSTANCEID();
				providerId = elco.PROVIDERID();
				startOfEarliestWindow = parseDate(elco.TODAY());
			}
		} else if (mother.equals(beneficiaryType)) {
			Mother mother = allMothers.findByCaseId(caseID);
			
			if (mother != null) {
				instanceId = mother.INSTANCEID();
				providerId = mother.PROVIDERID();
				startOfEarliestWindow = parseDate(mother.TODAY());
				List<Map<String, String>> bnfs = mother.bnfVisitDetails();
				if (!bnfs.isEmpty() && bnfs != null) {
					int psrfsCount = bnfs.size() - 1;
					Map<String, String> bnf = bnfs.get(psrfsCount);
					doo = bnf.get("FWBNFDTOO");
				}
			}
		} else if (child.equals(beneficiaryType)) {
			Child child = allChilds.findByCaseId(caseID);
			doo = child.details().get("FWBNFDOB");
			if (child != null) {
				instanceId = child.INSTANCEID();
				providerId = child.PROVIDERID();
				startOfEarliestWindow = parseDate(child.TODAY());
			}
		} else {
			throw new IllegalArgumentException("Beneficiary Type : " + beneficiaryType + " is of unknown type");
		}
		
		scheduler.alertFor(event.windowName(), beneficiaryType, caseID, instanceId, providerId,
		    parseScheduleName(event.scheduleName()), parseScheduleName(event.milestoneName()), startOfEarliestWindow,
		    event.startOfDueWindow(), event.startOfLateWindow(), event.startOfMaxWindow(), doo);
		
	}
	
	public String parseScheduleName(String scheduleName) {
		if (scheduleName.equalsIgnoreCase(ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF)) {
			return scheduleName.replace(ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF,
			    ELCOSchedulesConstantsImediate.ELCO_SCHEDULE_PSRF);
		} else {
			return scheduleName.replace(SCHEDULE_BNF_IME, BnfFollowUpVisitFields.SCHEDULE_BNF);
		}
		
	}
	
	@Override
	public void scheduleSaveToOpenMRSMilestone(Enrollment el, List<Action> alertActions) {
		
		try {
			scheduleLogService.saveActionDataToOpenMrsMilestoneTrack(el, alertActions);
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.info("From scheduleSaveToOpenMRSMilestone :" + e.getMessage());
		}
		
	}
	
	@Override
	public void saveMultimediaToRegistry(Multimedia multimediaFile) {
		multimediaRegisterService.saveMultimediaFileToRegistry(multimediaFile);
	}
	
	private DateTime parseDate(String DateAsString) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime startOfEarliestWindow = new DateTime();
		try {
			startOfEarliestWindow = DateTime.parse(DateAsString, formatter);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return startOfEarliestWindow;
		
	}
	
}
