package org.opensrp.scheduler.service;

import static org.opensrp.common.AllConstants.ALERTSTATUS;
import static org.opensrp.common.AllConstants.ScheduleNames.ANC;
import static org.opensrp.common.AllConstants.ScheduleNames.CHILD;
import static org.opensrp.common.AllConstants.ScheduleNames.PNC;
import static org.opensrp.dto.BeneficiaryType.child;
import static org.opensrp.dto.BeneficiaryType.ec;
import static org.opensrp.dto.BeneficiaryType.elco;
import static org.opensrp.dto.BeneficiaryType.household;
import static org.opensrp.dto.BeneficiaryType.mother;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.domain.MilestoneFulfillment;
import org.opensrp.common.AllConstants.Condition;
import org.opensrp.common.AllConstants.ScheduleNames;
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionService {
	
	private AllActions allActions;
	
	private ReportActionService reportActionService;
	
	private final ScheduleService scheduleService;
	
	@Autowired
	private AllEnrollmentWrapper allEnrollmentWrapper;
	
	private static Logger logger = LoggerFactory.getLogger(ActionService.class.toString());
	
	@Autowired
	public ActionService(AllActions allActions, ReportActionService reportActionService, ScheduleService scheduleService) {
		this.allActions = allActions;
		this.reportActionService = reportActionService;
		this.scheduleService = scheduleService;
	}
	
	public List<Action> getNewAlertsForANM(String anmIdentifier, long timeStamp) {
		return allActions.findByANMIDAndTimeStamp(anmIdentifier, timeStamp);
	}
	
	public List<Action> findByCaseIdScheduleAndTimeStamp(String caseId, String schedule, DateTime start, DateTime end) {
		return allActions.findByCaseIdScheduleAndTimeStamp(caseId, schedule, start, end);
	}
	
	public void alertForBeneficiary(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier,
	                                String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate,
	                                DateTime expiryDate, String doo) {
		if (!(mother.equals(beneficiaryType) || child.equals(beneficiaryType) || ec.equals(beneficiaryType)
		        || household.equals(beneficiaryType) || elco.equals(beneficiaryType))) {
			throw new IllegalArgumentException("Beneficiary Type : " + beneficiaryType + " is of unknown type");
		}
		if (scheduleName.equals(ScheduleNames.BNF) || scheduleName.equals(ScheduleNames.CENCUS)
		        || scheduleName.equals(ScheduleNames.ELCO) || scheduleName.equals(ScheduleNames.MIS_ELCO)) {
			this.ActionUpdateOrCreateForOther(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode,
			    alertStatus, startDate, expiryDate);
			
		} else if (scheduleName.equals(ScheduleNames.ANC) || scheduleName.equals(ScheduleNames.PNC)
		        || scheduleName.equals(ScheduleNames.CHILD)) {
			this.ActionUpdateOrCreateForMotherType(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName,
			    visitCode, alertStatus, startDate, expiryDate, doo);
		} else {
			
		}
		
	}
	
	public void markAllAlertsAsInactive(String entityId) {
		allActions.markAllAsInActiveFor(entityId);
	}
	
	public void markAlertAsInactive(String anmId, String entityId, String scheduleName) {
		allActions.markAlertAsInactiveFor(anmId, entityId, scheduleName);
	}
	
	public void markAlertAsClosed(String caseId, String anmIdentifier, String visitCode, String completionDate) {
		allActions.add(new Action(caseId, anmIdentifier, ActionData.markAlertAsClosed(visitCode, completionDate)));
	}
	
	public void closeBeneficiary(BeneficiaryType beneficiary, String caseId, String anmIdentifier, String reasonForClose) {
		allActions.add(new Action(caseId, anmIdentifier, ActionData.closeBeneficiary(beneficiary.name(), reasonForClose)));
	}
	
	public void reportForIndicator(String anmIdentifier, ActionData actionData) {
		allActions.add(new Action("", anmIdentifier, actionData));
	}
	
	public void deleteReportActions() {
		allActions.deleteAllByTarget("report");
	}
	
	public void ActionUpdateOrCreateForOther(BeneficiaryType beneficiaryType, String caseID, String instanceId,
	                                         String anmIdentifier, String scheduleName, String visitCode,
	                                         AlertStatus alertStatus, DateTime startDate, DateTime expiryDate) {
		try {
			List<Action> existingAlerts = allActions.findAlertByANMIdEntityIdScheduleName(anmIdentifier, caseID,
			    scheduleName);
			if (existingAlerts.size() > 0) {
				updateDataAction(visitCode, alertStatus, startDate, expiryDate, existingAlerts);
				logger.info("ActionUpdateOrCreateForOther for motech event caseId: " + existingAlerts.get(0).caseId()
				        + " ,provider: " + existingAlerts.get(0).anmIdentifier() + " ,event visitCode: " + visitCode
				        + " ,event alert status: " + alertStatus.name() + " ,existing visitCode: "
				        + existingAlerts.get(0).data().get("visitCode") + " ,exisiting alert status: "
				        + existingAlerts.get(0).data().get("alertStatus") + " ,isactive: "
				        + existingAlerts.get(0).getIsActionActive());
				
			} else {
				/*allActions.addOrUpdateAlert(new Action(caseID, anmIdentifier, ActionData.createAlert(beneficiaryType,
				    scheduleName, visitCode, alertStatus, startDate, expiryDate)));*/
				logger.info("schedule not found caseId: " + caseID + " ,scheduleName: " + scheduleName);
			}
		}
		catch (Exception e) {
			logger.warn("ActionUpdateOrCreateForOther, error: " + e.getMessage());
			
		}
	}
	
	public void ActionUpdateOrCreateForMotherType(BeneficiaryType beneficiaryType, String caseID, String instanceId,
	                                              String anmIdentifier, String scheduleName, String visitCode,
	                                              AlertStatus alertStatus, DateTime startDate, DateTime expiryDate,
	                                              String doo) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			List<Action> existingAlerts = allActions.findAlertByANMIdEntityIdScheduleName(anmIdentifier, caseID,
			    scheduleName);
			
			if (existingAlerts.size() > 0) {
				if (ANC.equalsIgnoreCase(scheduleName)) {
					checkForUpdate(visitCode, alertStatus, startDate, expiryDate, existingAlerts);
					long dateDiff = DateTimeUtil.getDaysDifference(expiryDate);
					
					if (AlertStatus.urgent.name().equalsIgnoreCase(alertStatus.name()) && dateDiff <= 1) {
						scheduleService.fulfillMilestone(caseID, scheduleName, new LocalDate());
						if (visitCode.equalsIgnoreCase(ScheduleNames.anc4)) {
							checkForUpdate(visitCode, AlertStatus.expired, startDate, expiryDate, existingAlerts);
						}
						
					}
				} else if (PNC.equalsIgnoreCase(scheduleName) || CHILD.equalsIgnoreCase(scheduleName)) {
					String scheduleNameVisitCodeWithoutNumber;
					Date date = null;
					if (doo != null) {
						date = format.parse(doo);
					}
					DateTime FWBNFDTOO = new DateTime(date);
					if (PNC.equalsIgnoreCase(scheduleName)) {
						scheduleNameVisitCodeWithoutNumber = ScheduleNames.PNCRV;
					} else {
						scheduleNameVisitCodeWithoutNumber = ScheduleNames.ENCCRV;
					}
					long dateDifference = DateTimeUtil.getDaysDifference(FWBNFDTOO);
					
					if (dateDifference == Condition.PNCENCCDAYZERO
					        && visitCode.equalsIgnoreCase(scheduleNameVisitCodeWithoutNumber + "_1")) {
						
						checkForUpdate(visitCode, AlertStatus.urgent, new DateTime(FWBNFDTOO).plusDays(1), new DateTime(
						        FWBNFDTOO).plusDays(1), existingAlerts);
						
					} else if (dateDifference == Condition.PNCENCCDAYONE
					        && visitCode.equalsIgnoreCase(scheduleNameVisitCodeWithoutNumber + "_1")) {
						checkForUpdate(scheduleNameVisitCodeWithoutNumber + "_2", AlertStatus.upcoming, new DateTime(
						        FWBNFDTOO).plusDays(2), new DateTime(FWBNFDTOO).plusDays(2), existingAlerts);
						scheduleService.fulfillMilestone(caseID, scheduleName, new LocalDate());
					} else if ((dateDifference == Condition.PNCENCCDAYTWO || dateDifference == Condition.PNCENCCDAYTHREE || dateDifference == Condition.PNCENCCDAYFOUR)
					        && visitCode.equalsIgnoreCase(scheduleNameVisitCodeWithoutNumber + "_2")) {
						checkForUpdate(scheduleNameVisitCodeWithoutNumber + "_2", AlertStatus.urgent,
						    new DateTime(FWBNFDTOO).plusDays(3), new DateTime(FWBNFDTOO).plusDays(5), existingAlerts);
					} else if (dateDifference == Condition.PNCENCCDAYFIVE
					        && visitCode.equalsIgnoreCase(scheduleNameVisitCodeWithoutNumber + "_2")) {
						checkForUpdate(scheduleNameVisitCodeWithoutNumber + "_3", AlertStatus.upcoming, new DateTime(
						        FWBNFDTOO).plusDays(6), new DateTime(FWBNFDTOO).plusDays(7), existingAlerts);
						scheduleService.fulfillMilestone(caseID, scheduleName, new LocalDate());
						
					} else if (dateDifference == Condition.PNCENCCDAYSIX
					        && visitCode.equalsIgnoreCase(scheduleNameVisitCodeWithoutNumber + "_3")) {
						checkForUpdate(scheduleNameVisitCodeWithoutNumber + "_3", AlertStatus.upcoming, new DateTime(
						        FWBNFDTOO).plusDays(6), new DateTime(FWBNFDTOO).plusDays(7), existingAlerts);
						
					} else if (dateDifference == Condition.PNCENCCDAYSEVEN
					        && visitCode.equalsIgnoreCase(scheduleNameVisitCodeWithoutNumber + "_3")) {
						checkForUpdate(scheduleNameVisitCodeWithoutNumber + "_3", AlertStatus.urgent,
						    new DateTime(FWBNFDTOO).plusDays(8), new DateTime(FWBNFDTOO).plusDays(8), existingAlerts);
						
					} else if (dateDifference <= Condition.PNCENCCDAYEIGHT) {
						checkForUpdate(visitCode, AlertStatus.expired, new DateTime(FWBNFDTOO).plusDays(9), new DateTime(
						        FWBNFDTOO).plusDays(9), existingAlerts);
						scheduleService.fulfillMilestone(caseID, scheduleName, new LocalDate());
					}
					
				} else {
					logger.info("expected schedule not found caseId:" + caseID + " ,scheduleName: " + scheduleName);
				}
				
				logger.info("ActionUpdateOrCreateForMotherType for motech event caseId: " + existingAlerts.get(0).caseId()
				        + " ,provider: " + existingAlerts.get(0).anmIdentifier() + " ,event visitCode: " + visitCode
				        + " ,event alert status: " + alertStatus.name() + " ,existing visitCode: "
				        + existingAlerts.get(0).data().get("visitCode") + " ,exisiting alert status: "
				        + existingAlerts.get(0).data().get("alertStatus") + " ,isactive: "
				        + existingAlerts.get(0).getIsActionActive());
				
			} else {
				/*allActions.addOrUpdateAlert(new Action(caseID, anmIdentifier, ActionData.createAlert(beneficiaryType,
				    scheduleName, visitCode, alertStatus, startDate, expiryDate)));*/
				logger.info("schedule not found caseId: " + caseID + " ,scheduleName: " + scheduleName);
			}
			
		}
		catch (Exception e) {
			logger.warn("ActionUpdateOrCreateForMotherType caseId: " + caseID + " ,scheduleName : " + scheduleName
			        + " ,error:" + e.getMessage());
		}
	}
	
	private void checkForUpdate(String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate,
	                            List<Action> existingAlerts) {
		Integer existingAlertStatus = ALERTSTATUS.get(existingAlerts.get(0).data().get("alertStatus"));
		Integer currentAlertStatus = ALERTSTATUS.get(alertStatus.name());
		if (visitCode.equalsIgnoreCase(existingAlerts.get(0).data().get("visitCode"))
		        && currentAlertStatus > existingAlertStatus) {
			updateDataAction(visitCode, alertStatus, startDate, expiryDate, existingAlerts);
			
		} else if (!visitCode.equalsIgnoreCase(existingAlerts.get(0).data().get("visitCode"))) {
			updateDataAction(visitCode, alertStatus, startDate, expiryDate, existingAlerts);
		}
		
	}
	
	public void updateDataAction(String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate,
	                             List<Action> existingAlerts) {
		
		existingAlerts.get(0).setRevision(existingAlerts.get(0).getRevision());
		existingAlerts.get(0).data().put("alertStatus", alertStatus.toString());
		existingAlerts.get(0).data().put("expiryDate", expiryDate.toLocalDate().toString());
		existingAlerts.get(0).data().put("startDate", startDate.toLocalDate().toString());
		existingAlerts.get(0).data().put("visitCode", visitCode);
		existingAlerts.get(0).markAsActive();
		existingAlerts.get(0).timestamp(Calendar.getInstance().getTimeInMillis());
		allActions.update(existingAlerts.get(0));
		
	}
	
	public long getDaysDifference(DateTime expiryDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();
		
		long days = 0;
		try {
			Date expiredDate = format.parse(expiryDate.toString());
			String todayDate = format.format(today);
			Date today_date = format.parse(todayDate);
			long diff = expiredDate.getTime() - today_date.getTime();
			days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		}
		catch (ParseException e) {
			
			e.printStackTrace();
		}
		return days;
	}
	
	public long getActionTimestamp(String anmIdentifier, String caseID, String scheduleName) {
		List<Action> existingAlerts = allActions.findAlertByANMIdEntityIdScheduleName(anmIdentifier, caseID, scheduleName);
		return existingAlerts.get(0).timestamp();
	}
	
	private boolean isFullfillment(String visitCode, String caseID, String scheduleName) {
		List<Enrollment> enrolments = allEnrollmentWrapper.findByEnrollmentByExternalIdAndScheduleName(caseID, scheduleName);
		
		List<MilestoneFulfillment> milestoneFulfillments = enrolments.get(0).getFulfillments();
		boolean status = false;
		for (MilestoneFulfillment milestoneFulfillment : milestoneFulfillments) {
			
			if (visitCode.equalsIgnoreCase(milestoneFulfillment.getMilestoneName())) {
				status = true;
				break;
			}
		}
		
		return status;
		
	}
}
