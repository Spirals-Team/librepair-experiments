package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.opensrp.common.util.DateUtil;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.register.mcare.OpenSRPScheduleConstants;
import org.opensrp.register.mcare.service.scheduling.impl.ANCScheduleImplementation;
import org.opensrp.register.mcare.service.scheduling.impl.ScheduleParamData;
import org.opensrp.scheduler.HealthSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ANCSchedulesService extends ANCScheduleImplementation {
	
	private static Logger logger = LoggerFactory.getLogger(ANCSchedulesService.class.toString());
	
	private HealthSchedulerService scheduler;
	
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	public ANCSchedulesService(HealthSchedulerService scheduler, ScheduleLogService scheduleLogService) {
		this.scheduler = scheduler;
		this.scheduleLogService = scheduleLogService;
	}
	
	public void enrollMother(String caseId, LocalDate referenceDateForSchedule, String provider, String instanceId,
	                         String startDate) {
		/*for (String schedule : NON_ANC_SCHEDULES) {
			scheduler.enrollIntoSchedule(caseId, schedule, referenceDateForSchedule);
		}*/
		scheduleCheckAndSaveOrNot(caseId, referenceDateForSchedule, provider, instanceId, startDate, true);
	}
	
	/**
	 * Create ANC Schedule depends on LMP Date
	 * 
	 * @param entityId form entity Id
	 * @param referenceDateForSchedule LMP Date convert to Local Date
	 * @param provider FW user name
	 * @param instanceId form instance id
	 * @param startDate LMP Date as String format
	 */
	public Map<String, String> scheduleCheckAndSaveOrNot(String entityId, LocalDate referenceDateForSchedule,
	                                                     String provider, String instanceId, String startDate, boolean isSave) {
		
		String milestone = null;
		Map<String, String> map = new HashMap<String, String>();
		String alertStaus = null;
		Date date = null;
		ScheduleParamData scheduleParamData = new ScheduleParamData();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(startDate);
		}
		catch (ParseException e) {
			logger.info("Date parse exception:" + e.getMessage());
		}
		
		DateTime start = new DateTime(date);
		
		long datediff = ScheduleLogService.getDaysDifference(start);
		
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod().plusDays(1))) {
			
			milestone = SCHEDULE_ANC_1;
			scheduleParamData = saveANCC1Schedule(datediff, start, milestone, entityId, referenceDateForSchedule, provider,
			    instanceId, startDate, isSave);
			map.put("alert", scheduleParamData.getAlertStaus());
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod()
		        .plusDays(1))) {
			milestone = SCHEDULE_ANC_2;
			scheduleParamData = saveANC2Schedule(datediff, start, milestone, entityId, referenceDateForSchedule, provider,
			    instanceId, startDate, isSave);
			map.put("alert", scheduleParamData.getAlertStaus());
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod()
		        .plusDays(1))) {
			milestone = SCHEDULE_ANC_3;
			scheduleParamData = saveANC3Schedule(datediff, start, milestone, entityId, referenceDateForSchedule, provider,
			    instanceId, startDate, isSave);
			map.put("alert", scheduleParamData.getAlertStaus());
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod())) {
			milestone = SCHEDULE_ANC_4;
			scheduleParamData = saveANC4Schedule(datediff, start, milestone, entityId, referenceDateForSchedule, provider,
			    instanceId, startDate, isSave);
			map.put("alert", scheduleParamData.getAlertStaus());
		} else {
			milestone = SCHEDULE_ANC_4;
			
			if (isSave) {
				logger.info("anc submission received at end of anc4 expire caseId:" + entityId + " ,provider:" + provider);
				scheduleLogService.saveAction(entityId, instanceId, provider, SCHEDULE_ANC, SCHEDULE_ANC_4,
				    BeneficiaryType.mother, AlertStatus.expired, new DateTime(start)
				            .plusDays(OpenSRPScheduleConstants.DateTimeDuration.ANC4EXPIREDSTART), new DateTime(start)
				            .plusDays(OpenSRPScheduleConstants.DateTimeDuration.ANC4EXPIREDEND));
			}
			map.put("alert", AlertStatus.expired.name());
		}
		
		if (isSave) {
			logger.info(format(
			    "enrolling anc with entityId:{0} , milestone: {1} referenceDateForSchedule{2}. SCHEDULE_ANC {3}. ",
			    entityId, milestone, referenceDateForSchedule.toString(), SCHEDULE_ANC));
			scheduler.enrollIntoSchedule(entityId, SCHEDULE_ANC, milestone, referenceDateForSchedule.toString());
			
		}
		map.put("milestone", milestone);
		return map;
	}
	
	public void fullfillSchedule(String caseID, String scheduleName, String instanceId, long timestamp) {
		try {
			scheduleLogService.fullfillSchedule(caseID, scheduleName, instanceId, timestamp);
			logger.info("fullfillSchedule a Schedule with caseId : " + caseID);
		}
		catch (Exception e) {
			logger.warn("fullfillSchedule failed error:" + e.getMessage());
		}
	}
	
	public void unEnrollFromAllSchedules(String entityId) {
		scheduler.unEnrollFromAllSchedules(entityId);
	}
	
	public void fullfillMilestone(String entityId, String providerId, String scheduleName, LocalDate completionDate) {
		try {
			scheduler.fullfillMilestone(entityId, providerId, scheduleName, completionDate);
			logger.info("fullfillMilestone with id: :" + entityId);
		}
		catch (Exception e) {
			logger.info("Does not a fullfillMilestone :" + e.getMessage());
		}
	}
	
	public void enrollANCSchedule() {
		
	}
	
}
