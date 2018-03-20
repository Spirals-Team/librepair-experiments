package org.opensrp.register.mcare.repository.it;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.service.AllEnrollmentWrapper;
import org.springframework.beans.factory.annotation.Autowired;

public class MotherIntegrationTest {
	
	@Autowired
	private AllHouseHolds allHouseHolds;
	
	@Autowired
	private AllElcos allElcos;
	
	private AllMothers allMothers;
	
	private CouchDbInstance dbInstance;
	
	private StdCouchDbConnector stdCouchDbConnector;
	
	private StdCouchDbConnector stdCouchDbConnectorMothech;
	
	private AllChilds allChilds;
	
	private AllActions allActions;
	
	private AllEnrollmentWrapper allEnrollmentWrapper;
	
	@Before
	public void setUp() throws Exception {
		
		HttpClient httpClient = new StdHttpClient.Builder()
		
		.host("192.168.19.97").port(5984).socketTimeout(1000000).username("Admin").password("mPower@1234").build();
		dbInstance = new StdCouchDbInstance(httpClient);
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		stdCouchDbConnectorMothech = new StdCouchDbConnector("motech-scheduletracking-api", dbInstance,
		        new StdObjectMapperFactory());
		//stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
		allElcos = new AllElcos(2, stdCouchDbConnector);
		allMothers = new AllMothers(2, stdCouchDbConnector);
		allChilds = new AllChilds(2, stdCouchDbConnector);
		allActions = new AllActions(stdCouchDbConnector);
		allEnrollmentWrapper = new AllEnrollmentWrapper(stdCouchDbConnectorMothech);
		
	}
	
	///http://localhost:5984/_utils/database.html?motech-scheduletracking-api/_design/Enrollment/_view/by_external_id
	//function(doc) { if(doc.type === 'Enrollment' && doc.scheduleName=='Ante Natal Care Reminder Visit') emit(doc.externalId); }
	//http://localhost:5984/_utils/database.html?opensrp/_design/Action/_view/all#
	//function(doc) { if(doc.type === 'Action' && doc.data.scheduleName=='Ante Natal Care Reminder Visit' && doc.data.alertStatus!='expired') {emit(null, doc._id)} }
	/*@Ignore
	@Test
	public void ancScheduleTest() {
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.findActionByScheduleName("Ante Natal Care Reminder Visit");
		System.err.println("" + actions.size());
		String lmp = "";
		
		int m = 0;
		int acn4 = 0;
		int unenroll = 0;
		int currentVisitiCodec = 0;
		int notCurrentVisitiCodec = 0;
		int expired = 0;
		int enroll = 0;
		int notsubSame = 0;
		int notsubNotSame = 0;
		boolean isEnrolled = false;
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Mother mother = allMothers.findByCaseId(action.caseId());
			//System.err.println("Mother;" + mother);
			
			if (mother != null && mother.PROVIDERID() != null) {
				lmp = mother.details().get("LMP");
				List<Enrollment> enrollments = allEnrollmentWrapper.findByExternalIdAndScheduleName(action.caseId(),
				    "Ante Natal Care Reminder Visit");
				
				Map<String, String> ancParam = checkANC(LocalDate.parse(lmp), lmp);
				
				boolean ancStatus = isANCSubmited(mother, visitCode);
				if (ancStatus) {// if submitted //780
				
					if (ancParam.get("alert").equalsIgnoreCase(action.data().get("alertStatus"))
					        && ancParam.get("milestone").equalsIgnoreCase(visitCode) && isActive(enrollments)) {
						action.markAsInActive();
						//allActions.update(action);
						currentVisitiCodec++; //416
					} else {
						
					}
					
				}
			} else {
				m++;
			}
			
		}
		System.err.println("currentVisitiCodec:" + currentVisitiCodec);
	}
	
	@Ignore
	@Test
	public void pncScheduleTest() {
		String pattern = "yyyy-MM-dd";
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.findActionByScheduleName("Post Natal Care Reminder Visit");
		System.err.println("" + actions.size());
		String doo = "";
		String currentVisitiCode = "";
		int m = 0;
		int acn4 = 0;
		int unenroll = 0;
		int currentVisitiCodec = 0;
		int notCurrentVisitiCodec = 0;
		int expired = 0;
		int enroll = 0;
		int notsubSame = 0;
		int notsubNotSame = 0;
		boolean isEnrolled = false;
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Mother mother = allMothers.findByCaseId(action.caseId());
			//System.err.println("Mother;" + mother);
			
			if (mother != null) {
				
				List<Map<String, String>> bnfVisitDetails = mother.bnfVisitDetails();
				doo = getBnfDate(bnfVisitDetails);
				DateTime dateTime = DateTime.parse(doo);
				DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
				String referenceDate = fmt.print(dateTime);
				currentVisitiCode = checkPNC(LocalDate.parse(referenceDate), referenceDate);
				boolean pncStatus = isPNCSubmited(mother, visitCode);
				List<Enrollment> enrollments = allEnrollmentWrapper.findByExternalIdAndScheduleName(action.caseId(),
				    "Post Natal Care Reminder Visit");
				if (pncStatus) {// if submitted //780
				
					System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());
					if (SCHEDULE_PNC_3.equalsIgnoreCase(visitCode)) {
						//should false all scedule; // problem in tab synced schedule
						// unenroll all schedule
						acn4++;//14
						
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							
							notCurrentVisitiCodec++; //7
							
							// refresh schedule
						} else {
							currentVisitiCodec++; //0
							
							// // false all schedule
						}
					}
					
				} else { // not submitted
				
					if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
						
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							notsubNotSame++; //0
							System.err.println("" + action.caseId());
							// must need to refresh
							
						} else {
							notsubSame++;//6/ may be nothing to do
							
						}
					}
					
				}
			} else {
				m++;
			}
			
		}
		System.err.println("Not Mother:" + m + "acn4:" + acn4 + "currentVisitiCodec:" + currentVisitiCodec
		        + "   notCurrentVisitiCodec:" + notCurrentVisitiCodec + "  expired:" + expired + "unenroll: " + unenroll
		        + "  enroll:" + enroll + " notsubSame  :" + notsubSame + " notsubNotSame;" + notsubNotSame);
	}
	
	@Ignore
	@Test
	public void enncScheduleTest() {
		String pattern = "yyyy-MM-dd";
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.findActionByScheduleName("Essential Newborn Care Checklist");
		System.err.println("" + actions.size());
		String doo = "";
		String currentVisitiCode = "";
		int m = 0;
		int acn4 = 0;
		int unenroll = 0;
		int currentVisitiCodec = 0;
		int notCurrentVisitiCodec = 0;
		int expired = 0;
		int enroll = 0;
		int notsubSame = 0;
		int notsubNotSame = 0;
		boolean isEnrolled = false;
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Child child = allChilds.findByCaseId(action.caseId());
			//System.err.println("Mother;" + mother);
			
			if (child != null) {
				List<Enrollment> enrollments = allEnrollmentWrapper.findByExternalIdAndScheduleName(action.caseId(),
				    "Essential Newborn Care Checklist");
				doo = child.details().get("FWBNFDOB");
				DateTime dateTime = DateTime.parse(doo);
				DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
				String referenceDate = fmt.print(dateTime);
				currentVisitiCode = checkENCC(LocalDate.parse(referenceDate), referenceDate);
				boolean enccStatus = isENNCSubmited(child, visitCode);
				if (enccStatus) {// if submitted //780
				
					System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());
					if (SCHEDULE_ENCC_3.equalsIgnoreCase(visitCode)) {
						//should false all scedule; // problem in tab synced schedule
						// unenroll all schedule
						acn4++;//4
						
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							notCurrentVisitiCodec++; //5
							
							// refresh schedule // schedule interupted due to server off 21 december
						} else {
							currentVisitiCodec++; //0 no data found
							
							//// false all schedule
						}
					}
					
				} else { // not submitted
				
					if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
						
						// refresh schedule
					} else {
						if (!currentVisitiCode.equalsIgnoreCase(visitCode) && isActive(enrollments)) {
							notsubNotSame++; //29
							System.err.println("" + action.caseId());
							// must need to refresh
							
						} else {
							
							notsubSame++;//6/ may be nothing to do
							
						}
					}
					
					System.err.println("ANC CaseId:" + action.caseId() + "visitCode:" + visitCode + " status:"
					        + action.getIsActionActive());
				}
			} else {
				m++;//1
			}
			
		}
		System.err.println("Not Mother:" + m + "acn4:" + acn4 + "currentVisitiCodec:" + currentVisitiCodec
		        + "   notCurrentVisitiCodec:" + notCurrentVisitiCodec + "  expired:" + expired + "unenroll: " + unenroll
		        + "  enroll:" + enroll + " notsubSame  :" + notsubSame + " notsubNotSame;" + notsubNotSame);
	}
	
	private String checkENCC(LocalDate referenceDateForSchedule, String referenceDate) {
		
		String milestone = null;
		
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(referenceDate);
			
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateTime FWBNFDTOO = new DateTime(date);
		long datediff = ScheduleLogService.getDaysDifference(FWBNFDTOO);
		//System.err.println("datediff:" + datediff);
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ONE.toPeriod())) {
			milestone = SCHEDULE_ENCC_1;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.FIVE.toPeriod())) {
			
			milestone = SCHEDULE_ENCC_2;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.plus(2).toPeriod())) {
			
			milestone = SCHEDULE_ENCC_3;
			
		} else {
			milestone = "expired";
			
		}
		return milestone;
		
	}
	
	private boolean isENNCSubmited(Child child, String visitCode) {
		Map<String, String> pnc = null;
		
		if (SCHEDULE_ENCC_1.equalsIgnoreCase(visitCode)) {
			pnc = child.enccVisitOne();
		} else if (SCHEDULE_ENCC_2.equalsIgnoreCase(visitCode)) {
			pnc = child.enccVisitTwo();
		} else if (SCHEDULE_ENCC_3.equalsIgnoreCase(visitCode)) {
			pnc = child.enccVisitThree();
		}
		if (!pnc.isEmpty()) {
			return true;//submitted
		}
		return false;
		
	}
	
	private Map<String, String> checkANC(LocalDate referenceDateForSchedule, String startDate) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		String milestone = null;
		DateTime ancStartDate = null;
		DateTime ancExpireDate = null;
		AlertStatus alertStaus = null;
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(startDate);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		DateTime start = new DateTime(date);
		
		long datediff = ScheduleLogService.getDaysDifference(start);
		
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod())) {
			//161
			milestone = SCHEDULE_ANC_1;
			if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(8).toPeriod())) {
				// til 50- 56
				if (datediff <= -DateTimeDuration.ANC1UPCOMINGSTART) {
					alertStaus = AlertStatus.upcoming;
					
				}
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod()
			        .plusDays(1))) {
				// til 57-162
				alertStaus = AlertStatus.urgent;
				
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod())) {
			//217
			milestone = SCHEDULE_ANC_2;
			if (datediff == -162) {
				System.err.println("from 2 to 1");
				alertStaus = AlertStatus.expired;
				//ancStartDate = new DateTime(start).plusDays(DateTimeDuration.ANC1EXPIREDEND);
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC1EXPIREDEND);
				ancStartDate = ancExpireDate;
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod()
			        .plusDays(1))) {//167
				if (datediff == -DateTimeDuration.LASTDAYOFANC1) {
					alertStaus = AlertStatus.urgent;
					
				} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(24).toPeriod())) {
					// till 168
					alertStaus = AlertStatus.upcoming;
					
				} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod()
				        .plusDays(1))) {
					// till 219
					alertStaus = AlertStatus.urgent;
					
				}
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod()
			        .plusDays(1))) {
				if (datediff == DateTimeDuration.LASTDAYOFANC2) {
					alertStaus = AlertStatus.urgent;
					
				} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(32).toPeriod())) {
					// till 224
					alertStaus = AlertStatus.upcoming;
					
				} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod()
				        .plusDays(1))) {
					// 246
					alertStaus = AlertStatus.urgent;
					
				}
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod())) {
				if (datediff == DateTimeDuration.LASTDAYOFAN3) {
					// till 
					alertStaus = AlertStatus.urgent;
					
				} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(36).toPeriod())) {
					// from  248  to 252
					alertStaus = AlertStatus.upcoming;
					
				} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod())) {
					// 253 - 308
					alertStaus = AlertStatus.urgent;
					
				}
			} else {
				alertStaus = AlertStatus.expired;
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod())) {
			//245
			milestone = SCHEDULE_ANC_3;
			if (datediff == -218) {
				alertStaus = AlertStatus.expired;
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(32).toPeriod()
			        .minusDays(1))) {
				//223
				alertStaus = AlertStatus.upcoming;
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod()
			        .minusDays(1))) {
				//244
				alertStaus = AlertStatus.urgent;
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod())) {
				//245
				alertStaus = AlertStatus.expired;
				
			} else {
				alertStaus = AlertStatus.expired;
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
		        .minusDays(1))) {
			// 307
			
			milestone = SCHEDULE_ANC_4;
			if (datediff == -246) {
				alertStaus = AlertStatus.expired;
				ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.ANC3EXPIREDEND);
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(36).toPeriod()
			        .minusDays(1))) {
				//251
				alertStaus = AlertStatus.upcoming;
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
			        .minusDays(2))) {
				//306
				alertStaus = AlertStatus.urgent;
				
			} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(44).toPeriod()
			        .minusDays(1))) {
				//307
				alertStaus = AlertStatus.expired;
				
			}
			
		} else {
			milestone = SCHEDULE_ANC_4;
			alertStaus = AlertStatus.expired;
			
		}
		
		map.put("alert", alertStaus.name());
		map.put("milestone", milestone);
		return map;
		
	}
	
	private boolean isANCSubmited(Mother mother, String visitCode) {
		Map<String, String> anc = null;
		
		if (SCHEDULE_ANC_1.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitOne();
		} else if (SCHEDULE_ANC_2.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitTwo();
		} else if (SCHEDULE_ANC_3.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitThree();
		} else if (SCHEDULE_ANC_4.equalsIgnoreCase(visitCode)) {
			anc = mother.ancVisitFour();
		}
		if (!anc.isEmpty()) {
			return true;//submitted
		}
		return false;
		
	}
	
	private boolean isPNCSubmited(Mother mother, String visitCode) {
		Map<String, String> pnc = null;
		
		if (SCHEDULE_PNC_1.equalsIgnoreCase(visitCode)) {
			pnc = mother.pncVisitOne();
		} else if (SCHEDULE_PNC_2.equalsIgnoreCase(visitCode)) {
			pnc = mother.pncVisitTwo();
		} else if (SCHEDULE_PNC_3.equalsIgnoreCase(visitCode)) {
			pnc = mother.pncVisitThree();
		}
		if (!pnc.isEmpty()) {
			return true;//submitted
		}
		return false;
		
	}
	
	private String checkPNC(LocalDate referenceDateForSchedule, String referenceDate) {
		String milestone = null;
		
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(referenceDate);
			
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateTime FWBNFDTOO = new DateTime(date);
		long datediff = ScheduleLogService.getDaysDifference(FWBNFDTOO);
		
		if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ONE.toPeriod())) {
			milestone = SCHEDULE_PNC_1;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.FIVE.toPeriod())) {
			
			milestone = SCHEDULE_PNC_2;
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.plus(2).toPeriod())) {
			
			milestone = SCHEDULE_PNC_3;
			
		} else {
			milestone = "expired";
			
		}
		return milestone;
		
	}
	
	private boolean isActive(List<Enrollment> enrollments) {
		boolean completed = false;
		System.err.println("Size:" + enrollments.size());
		for (Enrollment enrollment : enrollments) {
			System.err.println("enrollment:" + enrollment.getStatus());
			if (EnrollmentStatus.COMPLETED.name().equalsIgnoreCase(enrollment.getStatus().name())
			        || EnrollmentStatus.UNENROLLED.name().equalsIgnoreCase(enrollment.getStatus().name())) {
				completed = true;
				
			} else if (EnrollmentStatus.ACTIVE.name().equalsIgnoreCase(enrollment.getStatus().name())
			        || EnrollmentStatus.DEFAULTED.name().equalsIgnoreCase(enrollment.getStatus().name())) {
				
			}
			
		}
		
		if (completed) {
			System.err.println("completed;" + completed);
			return false;
		} else {
			return true;
		}
		
	}*/
	
	// need to apply  in live
	@Ignore
	@Test
	public void expiredScheduleChangeTimestamp() {
		List<Action> actions = allActions.getAll(); //all view should change as only get expired schedule
		for (Action action : actions) {
			//action.markAsInActive();
			action.timestamp(System.currentTimeMillis());
			action.setRevision(action.getRevision());
			allActions.update(action);
		}
	}
	
	// need to apply  in live
	//function(doc) { if(doc.type === 'Action' && doc.data.alertStatus=='expired' && doc.data.scheduleName=='Ante Natal Care Reminder Visit') {emit([doc.data.scheduleName], null)} }
	/*@Ignore
	@Test
	public void updateANCForExpiredSchedule() {
		List<Action> actions = allActions.findActionByScheduleName("Ante Natal Care Reminder Visit");
		System.err.println("actions:" + actions.size());
		int anc1 = 0;
		int anc2 = 0;
		int anc3 = 0;
		int anc4 = 0;
		int e = 0;
		for (Action action : actions) {
			
			if (action.data().get("visitCode").equalsIgnoreCase("ancrv_1")) {
				anc1++;
				
				action.data().put("alertStatus", "urgent");
				action.setRevision(action.getRevision());
				
				allActions.update(action);
			} else if (action.data().get("visitCode").equalsIgnoreCase("ancrv_2")) {
				anc2++;
				action.data().put("alertStatus", "urgent");
				action.setRevision(action.getRevision());
				allActions.update(action);
			} else if (action.data().get("visitCode").equalsIgnoreCase("ancrv_3")) {
				anc3++;
				action.data().put("alertStatus", "urgent");
				action.setRevision(action.getRevision());
				allActions.update(action);
			} else if (action.data().get("visitCode").equalsIgnoreCase("ancrv_4")) {
				anc4++;
				
			} else {
				e++;
			}
			
		}
		System.err.println("anc1:" + anc1 + " anc2:" + anc2 + " anc3:" + anc3 + " anc4:" + anc4 + " e:" + e);
	}*/
	
	//function(doc) { if(doc.type === 'Action' && doc.data.visitCode=='ancrv_4' && doc.data.scheduleName=='Ante Natal Care Reminder Visit') {emit([doc.data.scheduleName], null)} }
	
	// need to apply  in live
	/*@Ignore
	@Test
	public void expiredAndSubmittedScheduleCorrection() {
		String visitCode = "";
		int acn4 = 0;
		int c = 0;
		List<Action> actions = allActions.findActionByScheduleName("Ante Natal Care Reminder Visit");
		for (Action action : actions) {
			try {
				visitCode = action.data().get("visitCode");
				Mother mother = allMothers.findByCaseId(action.caseId());
				//System.err.println("Mother;" + mother);
				boolean ancStatus = isANCSubmited(mother, visitCode);
				if (mother != null && mother.PROVIDERID() != null) {
					if (ancStatus) {
						
						if (SCHEDULE_ANC_4.equalsIgnoreCase(visitCode)) {
							
							action.markAsInActive();
							allActions.update(action);
							acn4++;//196
							System.err.println("visitCode" + visitCode + "" + action.getIsActionActive());
						}
					}
					
				}
			}
			catch (Exception e) {
				c++;
				System.err.println("" + e.getMessage() + " caseId:" + action.caseId());
			}
			
		}
		System.err.println("acn4:" + acn4 + "c:" + c);
	}*/
	
	@Ignore
	@Test
	public void createPNCExpiredSchedule() {
		List<Mother> mothers = allMothers.getAll();
		String doo = "";
		String pattern = "yyyy-MM-dd";
		int notINpnc = 0;
		int iNpnc = 0;
		int exceptiion = 0;
		
		for (Mother mother : mothers) {
			
			List<Map<String, String>> bnfVisitDetails = mother.bnfVisitDetails();
			try {
				if (bnfVisitDetails.size() != 0) {
					System.err.println("CaseId:" + mother.caseId());
					doo = getBnfDate(bnfVisitDetails);
					/*if (doo != null) {
						DateTime dateTime = DateTime.parse(doo);
						DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
						String referenceDate = fmt.print(dateTime);
						long datediff = ScheduleLogService.getDaysDifference(dateTime);
						
						if (datediff <= -9) {
							
							notINpnc++;
						} else {
							
							iNpnc++;
						}
					}*/
					
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				exceptiion++;
				
			}
			
		}
		
		System.err.println("notINpnc:" + notINpnc + " iNpnc:" + iNpnc + " exceptiion:" + exceptiion);
	}
	
	public String getBnfDate(List<Map<String, String>> bnfVisitDetails) {
		String user_type = "";
		String date = "";
		for (Map<String, String> map : bnfVisitDetails) {
			user_type = map.get("user_type");
			date = map.get("FWBNFDTOO");
			if ("FD".equalsIgnoreCase(user_type)) {
				if (!date.isEmpty()) {
					/*System.err.println("FWBNFDTOO:" + date + "  received_time: " + map.get("received_time")
					        + " FWBNFWOMVITSTS:" + map.get("FWBNFWOMVITSTS"));*/
					return date;
				}
			}
			
		}
		
		return null;
		
	}
	
	// e3ff91cb-6106-47b3-b672-cf020c539f1f
	//c1a8d55c-e49b-4f12-859f-ed684477a9eb
	//75629717-b9f9-4be8-86bd-f7b0e817a013
	//e275ea2d-6b90-46eb-b860-055d654a57f9
	// ab357e49-0f11-4a41-8f8a-06354dda100c
	
	// 7ffe724e-46d3-4a21-828a-9767294104ce
	@Ignore
	@Test
	public void craeteANC() {
		String csvFile = "/opt/multimedia/export/Final/Update.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] csvData = line.split(cvsSplitBy);
				String entityId = csvData[0].trim();
				DateTime ancStartDate = null;
				DateTime ancExpireDate = null;
				Mother mother = allMothers.findByCaseId(entityId);
				Date date = null;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date = format.parse(mother.details().get("LMP"));
				}
				catch (ParseException e) {
					e.printStackTrace();
				}
				ancStartDate = new DateTime(date);
				ancExpireDate = new DateTime(date).plusDays(DateTimeDuration.ANC1NORMALEND);
				allActions.addOrUpdateAlert(new Action(entityId, mother.PROVIDERID(), ActionData.createAlert(
				    BeneficiaryType.mother, SCHEDULE_ANC, SCHEDULE_ANC_1, AlertStatus.normal, ancStartDate, ancExpireDate)));
			}
		}
		catch (Exception e) {
			
		}
		String entityId = "7ffe724e-46d3-4a21-828a-9767294104ce";
		
	}
	
	@Ignore
	@Test
	public void csheduleCheck() {
		List<Action> actions = allActions.findActionByScheduleName("Ante Natal Care Reminder Visit");
		System.err.println("Total:" + actions.size());
		int cnt = 0;
		for (Action action : actions) {
			Date date = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (action.getIsActionActive()) {
					date = format.parse(action.data().get("expiryDate"));
					DateTime start = new DateTime(date);
					long datediff = getDaysDifference(start);
					
					System.err.println(action.data().get("expiryDate") + " : " + datediff + " : "
					        + action.getIsActionActive() + " : " + action.data().get("visitCode") + " : " + action.caseId());
				} else {
					cnt++;
				}
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
		System.err.println("CNT:" + cnt);
	}
	
	@Ignore
	@Test
	public void calculateLiveANC() {
		List<Action> actions = allActions.findActionByScheduleName("Ante Natal Care Reminder Visit");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();
		List<Mother> mothers = allMothers.getAll();
		int cnt = 0;
		int ecnt = 0;
		for (Mother mother : mothers) {
			try {
				String lmp = mother.details().get("LMP");
				DateTime start = new DateTime(lmp);
				long datediff = getDaysDifference(start);
				if (datediff > -308) {
					System.err.println("datediff:" + datediff + " :" + lmp);
					cnt++;//1838
				}
			}
			catch (Exception e) {
				ecnt++;
			}
		}
		System.err.println("Total:" + mothers.size() + " actions: " + actions.size() + " : CNT:" + cnt + " ECNT:" + ecnt);
	}
	
	public static long getDaysDifference(DateTime expiryDate) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return days;
	}
	
	@Test
	public void checkMotherForProperSchedule() {
		String csvFile = "/opt/multimedia/gestational.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int properBNFCount = 0;
		int notBNFReceived = 0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] csvData = line.split(cvsSplitBy);
				String entityId = csvData[0].trim();
				Mother mother = allMothers.findByCaseId(entityId);
				List<Map<String, String>> bnfVisitDetails = mother.bnfVisitDetails();
				if (bnfVisitDetails.size() != 0) {
					
					String date = getBnfDate(bnfVisitDetails);
					if (date != null) {
						//System.err.println("entityId:" + entityId);
						properBNFCount++;
					} else {
						System.err.println(entityId + ",");
					}
					
				} else {
					System.err.println(entityId + ",");
					notBNFReceived++;
				}
				/*DateTime ancStartDate = null;
				DateTime ancExpireDate = null;
				Mother mother = allMothers.findByCaseId(entityId);
				Date date = null;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date = format.parse(mother.details().get("LMP"));
				}
				catch (ParseException e) {
					e.printStackTrace();
				}
				ancStartDate = new DateTime(date);
				ancExpireDate = new DateTime(date).plusDays(DateTimeDuration.ANC1NORMALEND);
				allActions.addOrUpdateAlert(new Action(entityId, mother.PROVIDERID(), ActionData.createAlert(
				    BeneficiaryType.mother, SCHEDULE_ANC, SCHEDULE_ANC_1, AlertStatus.normal, ancStartDate, ancExpireDate)));*/
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("properBNFCount:" + properBNFCount + " notBNFReceived:" + notBNFReceived);
	}
}
