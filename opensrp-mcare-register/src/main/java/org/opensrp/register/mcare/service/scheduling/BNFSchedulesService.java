/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.SCHEDULE_BNF_IME;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration.bnf_duration;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_BNF;

import org.joda.time.LocalDate;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BNFSchedulesService {
	
	private static Logger logger = LoggerFactory.getLogger(BNFSchedulesService.class.toString());
	
	private HealthSchedulerService scheduler;
	
	private AllActions allActions;
	
	private ActionService actionService;
	
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	public BNFSchedulesService(HealthSchedulerService scheduler, AllActions allActions,
	    ScheduleLogService scheduleLogService, ActionService actionService) {
		this.scheduler = scheduler;
		this.allActions = allActions;
		this.scheduleLogService = scheduleLogService;
		this.actionService = actionService;
	}
	
	public void enrollBNF(String caseId, LocalDate referenceDateForSchedule, String provider, String instanceId,
	                      String startDate) {
		logger.info(format("Enrolling Mother into BNF schedule. Id: {0}", caseId));
		this.immediateEnrollIntoMilestoneOfBNF(caseId, startDate, provider, instanceId);
	}
	
	public void unEnrollBNFSchedule(String caseId, String providerId) {
		logger.info(format("Unenrolling Mother from BNF schedule. Id: {0}", caseId));
		try {
			scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, SCHEDULE_BNF, new LocalDate());
			
			actionService.markAlertAsInactive(providerId, caseId, SCHEDULE_BNF);
		}
		catch (Exception e) {
			logger.info(format("Failed to UnEnrollFromSchedule BNF" + e.getMessage()));
		}
		
		try {
			scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, SCHEDULE_BNF_IME, new LocalDate());
			
			actionService.markAlertAsInactive(providerId, caseId, SCHEDULE_BNF);
		}
		catch (Exception e) {
			logger.info(format("Failed to UnEnrollFromSchedule BNF" + e.getMessage()));
		}
	}
	
	public void enrollIntoMilestoneOfBNF(String caseId, String date, String provider, String instanceId) {
		logger.info(format("Enrolling Mother into BNF schedule. Id: {0}", caseId));
		
		scheduler.enrollIntoSchedule(caseId, SCHEDULE_BNF, date);
		
	}
	
	public void immediateEnrollIntoMilestoneOfBNF(String caseId, String date, String provider, String instanceId) {
		logger.info(format("Enrolling Mother into Immediate BNF schedule. Id: {0}", caseId));
		scheduler.enrollIntoSchedule(caseId, SCHEDULE_BNF_IME, date);
		scheduleLogService.createImmediateScheduleAndScheduleLog(caseId, date, provider, instanceId, BeneficiaryType.mother,
		    SCHEDULE_BNF, bnf_duration, SCHEDULE_BNF_IME);
		
	}
	
}
