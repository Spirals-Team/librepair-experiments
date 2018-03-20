package org.opensrp.web.controller;

import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_4;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_3;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.domain.EnrollmentStatus;
import org.opensrp.common.AllConstants.ScheduleNames;
import org.opensrp.common.util.DateUtil;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.dashboard.dto.PrivilegeDTO;
import org.opensrp.dashboard.dto.RoleDTO;
import org.opensrp.dashboard.dto.UserDTO;
import org.opensrp.dashboard.service.PrivilegeService;
import org.opensrp.dashboard.service.RoleService;
import org.opensrp.dashboard.service.UsersService;
import org.opensrp.dto.AclDTO;
import org.opensrp.dto.AlertStatus;
import org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.AclService;
import org.opensrp.register.mcare.service.scheduling.ANCSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ChildSchedulesService;
import org.opensrp.register.mcare.service.scheduling.PNCSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.service.AllEnrollmentWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class AclController {
	
	private RoleService roleService;
	
	private AclService aclService;
	
	private OpenmrsUserService openmrsUserService;
	
	private PrivilegeService privilegeService;
	
	private UsersService userService;
	
	private static Logger logger = LoggerFactory.getLogger(AclController.class);
	
	@Autowired
	private AllEnrollmentWrapper allEnrollmentWrapper;
	
	@Autowired
	private HealthSchedulerService scheduler;
	
	@Autowired
	private AllActions allActions;
	
	@Autowired
	private AllMothers allMothers;
	
	@Autowired
	private AllChilds allChilds;
	
	@Autowired
	private PNCSchedulesService pncSchedulesService;
	
	@Autowired
	private ChildSchedulesService childSchedulesService;
	
	@Autowired
	private ANCSchedulesService ancSchedulesService;
	
	@Autowired
	public AclController(RoleService roleService, AclService aclService, OpenmrsUserService openmrsUserService,
	    PrivilegeService privilegeService, UsersService userService) {
		this.roleService = roleService;
		this.aclService = aclService;
		this.openmrsUserService = openmrsUserService;
		this.privilegeService = privilegeService;
		this.userService = userService;
	}
	
	/*@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-user")
	public ResponseEntity<String> addRole(@RequestBody RoleDTO roleDTO) {
		String message = roleService.addRole(roleDTO);
		return new ResponseEntity<>(message,OK);
	}*/
	
	/*@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-user")
	public ResponseEntity<String> editRole(@RequestBody RoleDTO roleDTO) {
		String message = roleService.editRole(roleDTO);
		return new ResponseEntity<>(message,OK);
	}*/
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-acl")
	public ResponseEntity<String> addAcl(@RequestBody AclDTO aclDTO) {
		String message = aclService.addAcl(aclDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-acl")
	public ResponseEntity<String> editAcl(@RequestBody AclDTO aclDTO) {
		String message = aclService.editAcl(aclDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(method = GET, value = "/all-user-name")
	@ResponseBody
	public ResponseEntity<String> getAllUserName() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(openmrsUserService.getAllUsers()), OK);
	}
	
	/*@RequestMapping(method = GET, value = "/role-access-tokens")
	@ResponseBody
	public AclDTO getRoleAndAccessTokens(@RequestParam String userName) {
		AclDTO tempDTO = new AclDTO();
		tempDTO.withRoleName("Admin");
		tempDTO.withRoleId("2ba3698706c7527a7a4b78546d011f1c");
		tempDTO.withStatus("Active");
		Map<String, String> tokens = new HashMap<String, String>();
		tokens.put("0", "Household");
		tokens.put("1", "Household Details");
		tokens.put("2", "Elco");
		tokens.put("3", "Elco Details");
		tokens.put("4", "PW");
		tokens.put("5", "PW Details");
		tokens.put("6", "Data Export");
		tokens.put("7", "User List");
		tokens.put("8", "User Assign");
		tokens.put("9", "User Assign Edit");
		tokens.put("10", "Role Edit");
		tokens.put("11", "Add Role");
		tokens.put("12", "Acl");
		tempDTO.withAccessTokens(tokens);
		//return aclService.getRoleAndAccessTokens(userName);
		return tempDTO;
	}*/
	
	// new one
	@RequestMapping(method = GET, value = "/role-access-token")
	@ResponseBody
	public List<String> getPrivileges(@RequestParam String userName) {
		return userService.getPrivilegesOfAUser(userName);
	}
	
	@RequestMapping(method = GET, value = "/role-access-tokens-by-name")
	@ResponseBody
	public AclDTO getRoleAndAccessTokensByRoleName(@RequestParam String roleName) {
		return aclService.getRoleAndAccessTokensByName(roleName);
	}
	
	@RequestMapping(method = GET, value = "/all-roles-access-tokens")
	@ResponseBody
	public ArrayList<AclDTO> getRolesAndAccessTokens() {
		return (ArrayList<AclDTO>) aclService.getRolesAndAccessTokens();
	}
	
	@RequestMapping(method = GET, value = "/all-active-roles-access-tokens")
	@ResponseBody
	public ArrayList<AclDTO> getActiveRolesAndAccessTokens() {
		return (ArrayList<AclDTO>) aclService.getActiveRolesAndAccessTokens();
	}
	
	@RequestMapping(method = GET, value = "/all-roles-with-user")
	@ResponseBody
	public ArrayList<RoleDTO> getRolesAndUser() {
		return (ArrayList<RoleDTO>) roleService.getRolesAndUser();
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-privilege")
	public ResponseEntity<String> addPrivilege(@RequestBody PrivilegeDTO privilegeDTO) {
		logger.info("request received for creating privilege with name- " + privilegeDTO.getName());
		String message = privilegeService.addPrivilege(privilegeDTO);
		//return new ResponseEntity<>(message,OK);
		return new ResponseEntity<>(message, OK);
		//return "1";
	}
	
	@RequestMapping(method = GET, value = "/privilege-by-name")
	@ResponseBody
	public PrivilegeDTO getPrivilegeByName(@RequestParam String privilegeName) {
		logger.info("requeset reached with - " + privilegeName);
		return privilegeService.getPrivilegeByName(privilegeName);
	}
	
	@RequestMapping(method = GET, value = "/all-privileges")
	@ResponseBody
	public ArrayList<PrivilegeDTO> getPrivileges() {
		logger.info("getting all privileges ");
		return privilegeService.getAllPrivileges();
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-privilege")
	public ResponseEntity<String> editPrivilege(@RequestBody PrivilegeDTO privilegeDTO) {
		logger.info("received status - " + privilegeDTO.getStatus());
		String message = privilegeService.editPrivilege(privilegeDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-role")
	public ResponseEntity<String> addRole(@RequestBody RoleDTO roleDTO) {
		logger.info("create request received for role - " + roleDTO.getName());
		/*List<PrivilegeDTO> privileges = roleDTO.getPrivileges();
		for(int i = 0 ; i < privileges.size(); i++){
			logger.info("Privilege name - " + privileges.get(i).getName() + " - id - " + privileges.get(i).getId());
		}*/
		String message = roleService.addRole(roleDTO);//privilegeService.addPrivilege(privilegeDTO);		
		return new ResponseEntity<>(message, OK);
		//return new ResponseEntity<>("1",OK);
		//return "1";
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-role")
	public ResponseEntity<String> editRole(@RequestBody RoleDTO roleDTO) {
		logger.info("create request received for role - " + roleDTO.getName());
		String message = roleService.editRole(roleDTO);//privilegeService.addPrivilege(privilegeDTO);		
		return new ResponseEntity<>(message, OK);
		//return new ResponseEntity<>("1",OK);
		//return "1";
	}
	
	@RequestMapping(method = GET, value = "role-by-name")
	@ResponseBody
	public RoleDTO getRoleByName(@RequestParam String roleName) {
		logger.info("requeset reached with - " + roleName);
		return roleService.getRoleByName(roleName);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-user")
	public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
		logger.info("create request received for user - " + userDTO.getName());
		
		String message = userService.addUser(userDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-user")
	public ResponseEntity<String> editUser(@RequestBody UserDTO userDTO) {
		logger.info("update request received for user - " + userDTO.getUserName());
		
		String message = userService.editUser(userDTO);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(method = GET, value = "/valid-username")
	@ResponseBody
	public ResponseEntity<String> isUsernameAvailable(@RequestParam String userName) {
		logger.info("check if user with name -" + userName + " exists.");
		String message = userService.ifUserExists(userName);
		return new ResponseEntity<>(message, OK);
	}
	
	@RequestMapping(method = GET, value = "/pe")
	public void pncencc() {
		scheduler.fullfillMilestoneAndCloseAlert("06072b3a-8fe7-4b44-a9c6-308397b35cf1", "rojina", ScheduleNames.CHILD,
		    new LocalDate());
		scheduler.fullfillMilestoneAndCloseAlert("e85d12d7-cce5-443e-8e18-0a79fe249f8b", "rojina", ScheduleNames.PNC,
		    new LocalDate());
		
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
	
	private Map<String, String> checkENCC(LocalDate referenceDateForSchedule, String referenceDate) {
		
		String milestone = null;
		DateTime startDate = null;
		DateTime expireDate = null;
		Map<String, String> map = new HashMap<String, String>();
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		AlertStatus alertStaus = null;
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
			milestone = SCHEDULE_ENCC_1;
			startDate = new DateTime(FWBNFDTOO);
			expireDate = new DateTime(FWBNFDTOO);
			if (datediff == 0) {
				alertStaus = AlertStatus.upcoming;
				
			} else if (datediff == -1) {
				alertStaus = AlertStatus.urgent;
				
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.FIVE.toPeriod())) {
			
			milestone = SCHEDULE_ENCC_2;
			
			if (datediff == -2) {
				alertStaus = AlertStatus.upcoming;
				
			} else {
				alertStaus = AlertStatus.urgent;
				
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.plus(2).toPeriod())) {
			
			milestone = SCHEDULE_ENCC_3;
			startDate = new DateTime(FWBNFDTOO).plusDays(DateTimeDuration.encc2);
			expireDate = new DateTime(FWBNFDTOO).plusDays(DateTimeDuration.encc3);
			
			if (datediff == -6 || datediff == -7) {
				alertStaus = AlertStatus.upcoming;
				
			} else if (datediff == -8) {
				alertStaus = AlertStatus.urgent;
				
			} else {
				alertStaus = AlertStatus.expired;
				
			}
			
		} else {
			milestone = SCHEDULE_ENCC_3;
			alertStaus = AlertStatus.expired;
			
		}
		map.put("alert", alertStaus.name());
		map.put("milestone", milestone);
		return map;
		
	}
	
	private Map<String, String> checkPNC(LocalDate referenceDateForSchedule, String referenceDate) {
		String milestone = null;
		DateTime startDate = null;
		DateTime expireDate = null;
		AlertStatus alertStaus = null;
		Date date = null;
		Map<String, String> map = new HashMap<String, String>();
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
			
			if (datediff == 0) {
				alertStaus = AlertStatus.upcoming;
				
			} else if (datediff == -1) {
				alertStaus = AlertStatus.urgent;
				
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.FIVE.toPeriod())) {
			
			milestone = SCHEDULE_PNC_2;
			
			if (datediff == -2) {
				alertStaus = AlertStatus.upcoming;
				
			} else {
				alertStaus = AlertStatus.urgent;
				
			}
			
		} else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.plus(2).toPeriod())) {
			
			milestone = SCHEDULE_PNC_3;
			
			if (datediff == -6 || datediff == -7) {
				alertStaus = AlertStatus.upcoming;
				
			} else if (datediff == -8) {
				alertStaus = AlertStatus.urgent;
				
			} else {
				alertStaus = AlertStatus.expired;
				
			}
			
		} else {
			milestone = SCHEDULE_PNC_3;
			alertStaus = AlertStatus.expired;
			
		}
		map.put("alert", alertStaus.name());
		map.put("milestone", milestone);
		return map;
		
	}
	
	@RequestMapping(method = GET, value = "/schedule-refresh-anc")
	public void ancScheduleRefresh() {
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.findActionByScheduleName(ScheduleNames.ANC);
		logger.info("anc correction started action size:" + actions.size());
		String lmp = "";
		
		int mtherCount = 0;
		int acn4submitted = 0;
		int submittedButMilestoneSame = 0;
		int expired = 0;
		
		int corrected = 0;
		int sameAlertStatus = 0;
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Mother mother = allMothers.findByCaseId(action.caseId());
			
			if (mother != null && mother.PROVIDERID() != null) {
				
				try {
					lmp = mother.details().get("LMP");
					Map<String, String> ancParam = ancSchedulesService.scheduleCheckAndSaveOrNot("", LocalDate.parse(lmp),
					    "", "", lmp, false);
					
					boolean ancStatus = isANCSubmited(mother, visitCode);
					List<Action> pncs = allActions.findAlertByANMIdEntityIdScheduleName(action.anmIdentifier(),
					    action.caseId(), "Post Natal Care Reminder Visit");
					
					if (ancStatus) {
						if (SCHEDULE_ANC_4.equalsIgnoreCase(visitCode)) {
							acn4submitted++;
							makeScheduleFalse(action);
							logger.info("no correction required case: " + visitCode + " submitted  caseid:"
							        + action.caseId() + " ,provider: " + action.anmIdentifier() + " ,LMP:" + lmp
							        + " ,expected visitcode:" + ancParam.get("milestone") + " ,expected alert status:"
							        + ancParam.get("alert") + " ,existing visitCode : " + action.data().get("visitCode")
							        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
							        + action.getIsActionActive());
							
						} else {
							if (!ancParam.get("milestone").equalsIgnoreCase(visitCode) && pncs.size() == 0) {
								
								corrected++;
								scheduleRefreshANC(action, lmp, action.anmIdentifier(), "", ScheduleNames.ANC);
								logger.info("correction required case: " + visitCode + " submitted  caseid:"
								        + action.caseId() + " ,provider: " + action.anmIdentifier() + " ,LMP:" + lmp
								        + " ,expected visitcode:" + ancParam.get("milestone") + " ,expected alert status:"
								        + ancParam.get("alert") + " ,existing visitCode : " + action.data().get("visitCode")
								        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
								        + action.getIsActionActive());
								
							} else {
								submittedButMilestoneSame++;
								logger.info("no correction required case: " + visitCode
								        + " submitted, milestone same  caseid:" + action.caseId() + " ,provider: "
								        + action.anmIdentifier() + " ,LMP:" + lmp + " ,expected visitcode:"
								        + ancParam.get("milestone") + " ,expected alert status:" + ancParam.get("alert")
								        + " ,existing visitCode : " + action.data().get("visitCode")
								        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
								        + action.getIsActionActive());
							}
							
						}
						
					} else { // not submitted
						i++; //1875
						if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
							expired++;
							logger.info("no correction required case: " + visitCode
							        + " not submitted, schedule expired  caseid:" + action.caseId() + " ,provider: "
							        + action.anmIdentifier() + " ,LMP:" + lmp + " ,expected visitcode:"
							        + ancParam.get("milestone") + " ,expected alert status:" + ancParam.get("alert")
							        + " ,existing visitCode : " + action.data().get("visitCode")
							        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
							        + action.getIsActionActive());
							
						} else {
							
							if (!ancParam.get("milestone").equalsIgnoreCase(visitCode) && pncs.size() == 0) {
								corrected++; //203
								// must need to refresh
								scheduleRefreshANC(action, lmp, action.anmIdentifier(), "", ScheduleNames.ANC);
								logger.info("correction required case: " + visitCode
								        + " not submitted, milestone is not same  caseid:" + action.caseId()
								        + " ,provider: " + action.anmIdentifier() + " ,LMP:" + lmp + " ,expected visitcode:"
								        + ancParam.get("milestone") + " ,expected alert status:" + ancParam.get("alert")
								        + " ,existing visitCode : " + action.data().get("visitCode")
								        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
								        + action.getIsActionActive());
							} else if (!ancParam.get("alert").equalsIgnoreCase(action.data().get("alertStatus"))
							        && ancParam.get("milestone").equalsIgnoreCase(visitCode) && pncs.size() == 0) {
								corrected++;
								scheduleRefreshANC(action, lmp, action.anmIdentifier(), "", ScheduleNames.ANC);
								logger.info("correction required case: " + visitCode
								        + " not submitted, milestone same alert status different caseid:" + action.caseId()
								        + " ,provider: " + action.anmIdentifier() + " ,LMP:" + lmp + " ,expected visitcode:"
								        + ancParam.get("milestone") + " ,expected alert status:" + ancParam.get("alert")
								        + " ,existing visitCode : " + action.data().get("visitCode")
								        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
								        + action.getIsActionActive());
							} else {
								sameAlertStatus++;//1038/ may be nothing to do
								logger.info("no correction required case: " + visitCode
								        + " not submitted, milestone same  & alert status same or pnc scheduled caseid:"
								        + action.caseId() + " ,provider: " + action.anmIdentifier() + " ,LMP:" + lmp
								        + " ,expected visitcode:" + ancParam.get("milestone") + " ,expected alert status:"
								        + ancParam.get("alert") + " ,existing visitCode : " + action.data().get("visitCode")
								        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
								        + action.getIsActionActive());
								
							}
						}
						
					}
					
				}
				catch (Exception e) {
					logger.warn("error in anc correction caseId: " + action.caseId() + " , error:" + e.getMessage());
				}
				
			} else {
				mtherCount++;
			}
			
		}
		logger.info("total corrected: " + corrected + " ,anc4 submitted:" + acn4submitted
		        + " ,submitted and same alert status:" + sameAlertStatus + " ,total expired:" + expired
		        + " ,submitted and milestone same:" + submittedButMilestoneSame + " ,empty mthercount:" + mtherCount
		        + " ,total action size:" + actions.size());
	}
	
	@RequestMapping(method = GET, value = "/schedule-refresh-pnc")
	public void pncScheduleRefresh() {
		String pattern = "yyyy-MM-dd";
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.findActionByScheduleName(ScheduleNames.PNC);
		logger.info("pnc correction started action size:" + actions.size());
		String doo = "";
		
		int mtherCount = 0;
		int pnc3submitted = 0;
		int corrected = 0;
		
		int expired = 0;
		int sameAlertStatus = 0;
		
		int submittedButMilestoneSame = 0;
		
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Mother mother = allMothers.findByCaseId(action.caseId());
			//System.err.println("Mother;" + mother);
			
			if (mother != null && mother.PROVIDERID() != null) {
				
				try {
					List<Enrollment> enrollments = allEnrollmentWrapper.findByExternalIdAndScheduleName(action.caseId(),
					    ScheduleNames.PNC);
					List<Map<String, String>> bnfVisitDetails = mother.bnfVisitDetails();
					doo = getBnfDate(bnfVisitDetails);
					if (doo != null) {
						DateTime dateTime = DateTime.parse(doo);
						DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
						String referenceDate = fmt.print(dateTime);
						
						Map<String, String> ancParam = checkPNC(LocalDate.parse(referenceDate), referenceDate);
						
						boolean pncStatus = isPNCSubmited(mother, visitCode);
						if (pncStatus) {// if submitted //780
						
							if (SCHEDULE_PNC_3.equalsIgnoreCase(visitCode)) {
								makeScheduleFalse(action);
								pnc3submitted++;
								logger.info("no correction required case: " + visitCode + " submitted  caseid:"
								        + action.caseId() + " ,provider: " + action.anmIdentifier() + " ,doo:" + doo
								        + " ,expected visitcode:" + ancParam.get("milestone") + " ,expected alert status:"
								        + ancParam.get("alert") + " ,existing visitCode : " + action.data().get("visitCode")
								        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
								        + action.getIsActionActive());
							} else {
								if (!ancParam.get("milestone").equalsIgnoreCase(visitCode)) {
									
									corrected++; //7
									scheduleRefreshPNC(enrollments, action, referenceDate, action.anmIdentifier(), "",
									    ScheduleNames.PNC);
									
									logger.info("correction required case: " + visitCode + " submitted  caseid:"
									        + action.caseId() + " ,provider: " + action.anmIdentifier() + " ,doo:" + doo
									        + " ,expected visitcode:" + ancParam.get("milestone")
									        + " ,expected alert status:" + ancParam.get("alert") + " ,existing visitCode : "
									        + action.data().get("visitCode") + " ,existing alert status:"
									        + action.data().get("alertStatus") + " ,isactive:" + action.getIsActionActive());
								} else {
									
									submittedButMilestoneSame++;
									
									logger.info("no correction required case: " + visitCode
									        + " submitted, milestone same  caseid:" + action.caseId() + " ,provider: "
									        + action.anmIdentifier() + " ,lmp:" + doo + " ,expected visitcode:"
									        + ancParam.get("milestone") + " ,expected alert status:" + ancParam.get("alert")
									        + " ,existing visitCode : " + action.data().get("visitCode")
									        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
									        + action.getIsActionActive());
								}
							}
							
						} else { // not submitted
						
							if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
								expired++;
								logger.info("no correction required case: " + visitCode
								        + " not submitted, schedule expired  caseid:" + action.caseId() + " ,provider: "
								        + action.anmIdentifier() + " ,doo:" + doo + " ,expected visitcode:"
								        + ancParam.get("milestone") + " ,expected alert status:" + ancParam.get("alert")
								        + " ,existing visitCode : " + action.data().get("visitCode")
								        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
								        + action.getIsActionActive());
							} else {
								if (!ancParam.get("milestone").equalsIgnoreCase(visitCode)) {
									corrected++; //0
									scheduleRefreshPNC(enrollments, action, referenceDate, action.anmIdentifier(), "",
									    ScheduleNames.PNC);
									
									logger.info("correction required case: " + visitCode
									        + " not submitted, milestone is not same  caseid:" + action.caseId()
									        + " ,provider: " + action.anmIdentifier() + " ,doo:" + doo
									        + " ,expected visitcode:" + ancParam.get("milestone")
									        + " ,expected alert status:" + ancParam.get("alert") + " ,existing visitCode : "
									        + action.data().get("visitCode") + " ,existing alert status:"
									        + action.data().get("alertStatus") + " ,isactive:" + action.getIsActionActive());
									
								} else if (!ancParam.get("alert").equalsIgnoreCase(action.data().get("alertStatus"))
								        && ancParam.get("milestone").equalsIgnoreCase(visitCode)) {
									scheduleRefreshPNC(enrollments, action, referenceDate, action.anmIdentifier(), "",
									    ScheduleNames.PNC);
									corrected++;
									logger.info("correction required case: " + visitCode
									        + " not submitted, milestone same alert status different caseid:"
									        + action.caseId() + " ,provider: " + action.anmIdentifier() + " ,doo:" + doo
									        + " ,expected visitcode:" + ancParam.get("milestone")
									        + " ,expected alert status:" + ancParam.get("alert") + " ,existing visitCode : "
									        + action.data().get("visitCode") + " ,existing alert status:"
									        + action.data().get("alertStatus") + " ,isactive:" + action.getIsActionActive());
								} else {
									sameAlertStatus++;//1038/ may be nothing to do
									
									logger.info("no correction required case: " + visitCode
									        + " not submitted, milestone same  & alert status same or pnc scheduled caseid:"
									        + action.caseId() + " ,provider: " + action.anmIdentifier() + " ,doo:" + doo
									        + " ,expected visitcode:" + ancParam.get("milestone")
									        + " ,expected alert status:" + ancParam.get("alert") + " ,existing visitCode : "
									        + action.data().get("visitCode") + " ,existing alert status:"
									        + action.data().get("alertStatus") + " ,isactive:" + action.getIsActionActive());
									
								}
							}
							
						}
					}
				}
				catch (Exception e) {
					logger.warn("error in pnc correction caseId: " + action.caseId() + " , error:" + e.getMessage());
				}
				
			} else {
				mtherCount++;
			}
			
		}
		
		logger.info("total corrected: " + corrected + " ,pnc3 submitted:" + pnc3submitted
		        + " ,submitted and same alert status:" + sameAlertStatus + " ,total expired:" + expired
		        + " ,submitted and milestone same:" + submittedButMilestoneSame + " ,empty mthercount:" + mtherCount
		        + " ,total action size:" + actions.size());
	}
	
	@RequestMapping(method = GET, value = "/schedule-refresh-encc")
	public void enncScheduleRefresh() {
		String pattern = "yyyy-MM-dd";
		int i = 0;
		String visitCode = "";
		List<Action> actions = allActions.findActionByScheduleName(ScheduleNames.CHILD);
		logger.info("encc correction started action size:" + actions.size());
		String doo = "";
		
		int mtherCount = 0;
		int submittedButMilestoneSame = 0;
		
		int encc3submitted = 0;
		int corrected = 0;
		int expired = 0;
		
		int sameAlertStatus = 0;
		
		for (Action action : actions) {
			visitCode = action.data().get("visitCode");
			Child child = allChilds.findByCaseId(action.caseId());
			
			if (child != null && child.PROVIDERID() != null) {
				try {
					doo = child.details().get("FWBNFDOB");
					DateTime dateTime = DateTime.parse(doo);
					DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
					String referenceDate = fmt.print(dateTime);
					Map<String, String> enccParam = checkENCC(LocalDate.parse(referenceDate), referenceDate);
					boolean enccStatus = isENNCSubmited(child, visitCode);
					List<Enrollment> enrollments = allEnrollmentWrapper.findByExternalIdAndScheduleName(action.caseId(),
					    ScheduleNames.CHILD);
					if (enccStatus) {// if submitted //780
					
						if (SCHEDULE_ENCC_3.equalsIgnoreCase(visitCode)) {
							
							makeScheduleFalse(action);
							encc3submitted++;
							logger.info("no correction required case: " + visitCode + " submitted  caseid:"
							        + action.caseId() + " ,provider: " + action.anmIdentifier() + " ,doo:" + doo
							        + " ,expected visitcode:" + enccParam.get("milestone") + " ,expected alert status:"
							        + enccParam.get("alert") + " ,existing visitCode : " + action.data().get("visitCode")
							        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
							        + action.getIsActionActive());
						} else {
							if (!enccParam.get("milestone").equalsIgnoreCase(visitCode)) {
								corrected++;
								scheduleRefreshENCC(enrollments, action, referenceDate, action.anmIdentifier(), "",
								    ScheduleNames.CHILD);
								
								logger.info("correction required case: " + visitCode + " submitted  caseid:"
								        + action.caseId() + " ,provider: " + action.anmIdentifier() + " ,doo:" + doo
								        + " ,expected visitcode:" + enccParam.get("milestone") + " ,expected alert status:"
								        + enccParam.get("alert") + " ,existing visitCode : "
								        + action.data().get("visitCode") + " ,existing alert status:"
								        + action.data().get("alertStatus") + " ,isactive:" + action.getIsActionActive());
							} else {
								
								submittedButMilestoneSame++;
								
								logger.info("no correction required case: " + visitCode
								        + " submitted, milestone same  caseid:" + action.caseId() + " ,provider: "
								        + action.anmIdentifier() + " ,lmp:" + doo + " ,expected visitcode:"
								        + enccParam.get("milestone") + " ,expected alert status:" + enccParam.get("alert")
								        + " ,existing visitCode : " + action.data().get("visitCode")
								        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
								        + action.getIsActionActive());
							}
						}
						
					} else { // not submitted
					
						if (action.data().get("alertStatus").equalsIgnoreCase("expired")) {
							
						} else {
							if (!enccParam.get("milestone").equalsIgnoreCase(visitCode)) {
								corrected++; //0
								// must need to refresh
								scheduleRefreshENCC(enrollments, action, referenceDate, action.anmIdentifier(), "",
								    ScheduleNames.CHILD);
								
								logger.info("correction required case: " + visitCode
								        + " not submitted, milestone is not same  caseid:" + action.caseId()
								        + " ,provider: " + action.anmIdentifier() + " ,doo:" + doo + " ,expected visitcode:"
								        + enccParam.get("milestone") + " ,expected alert status:" + enccParam.get("alert")
								        + " ,existing visitCode : " + action.data().get("visitCode")
								        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
								        + action.getIsActionActive());
								
							} else if (!enccParam.get("alert").equalsIgnoreCase(action.data().get("alertStatus"))
							        && enccParam.get("milestone").equalsIgnoreCase(visitCode)) {
								scheduleRefreshENCC(enrollments, action, referenceDate, action.anmIdentifier(), "",
								    ScheduleNames.CHILD);
								
								corrected++;
								logger.info("correction required case: " + visitCode
								        + " not submitted, milestone same alert status different caseid:" + action.caseId()
								        + " ,provider: " + action.anmIdentifier() + " ,doo:" + doo + " ,expected visitcode:"
								        + enccParam.get("milestone") + " ,expected alert status:" + enccParam.get("alert")
								        + " ,existing visitCode : " + action.data().get("visitCode")
								        + " ,existing alert status:" + action.data().get("alertStatus") + " ,isactive:"
								        + action.getIsActionActive());
							} else {
								sameAlertStatus++;//1038/ may be nothing to do
								
								logger.info("no correction required case: " + visitCode
								        + " not submitted, milestone same  & alert status same or pnc scheduled caseid:"
								        + action.caseId() + " ,provider: " + action.anmIdentifier() + " ,doo:" + doo
								        + " ,expected visitcode:" + enccParam.get("milestone") + " ,expected alert status:"
								        + enccParam.get("alert") + " ,existing visitCode : "
								        + action.data().get("visitCode") + " ,existing alert status:"
								        + action.data().get("alertStatus") + " ,isactive:" + action.getIsActionActive());
								
							}
						}
						
					}
				}
				catch (Exception e) {
					logger.warn("error in encc correction caseId: " + action.caseId() + " , error:" + e.getMessage());
				}
			} else {
				mtherCount++;
			}
			
		}
		
		logger.info("total corrected: " + corrected + " ,encc3 submitted:" + encc3submitted
		        + " ,submitted and same alert status:" + sameAlertStatus + " ,total expired:" + expired
		        + " ,submitted and milestone same:" + submittedButMilestoneSame + " ,empty mthercount:" + mtherCount
		        + " ,total action size:" + actions.size());
	}
	
	private void scheduleRefreshANC(Action action, String refDate, String provider, String instance, String scheduleName) {
		
		try {
			ancSchedulesService.enrollMother(action.caseId(), LocalDate.parse(refDate), provider, instance, refDate);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void scheduleRefreshPNC(List<Enrollment> enrollments, Action action, String refDate, String provider,
	                                String instance, String scheduleName) {
		
		try {
			
			pncSchedulesService.enrollPNCRVForMother(action.caseId(), "", provider, LocalDate.parse(refDate), refDate);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void scheduleRefreshENCC(List<Enrollment> enrollments, Action action, String refDate, String provider,
	                                 String instance, String scheduleName) {
		
		try {
			childSchedulesService.enrollENCCForChild(action.caseId(), "", provider, LocalDate.parse(refDate), refDate);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void makeScheduleFalse(Action action) {
		action.markAsInActive();
		action.timestamp(System.currentTimeMillis());
		action.setRevision(action.getRevision());
		allActions.update(action);
	}
	
	private String getBnfDate(List<Map<String, String>> bnfVisitDetails) {
		String user_type = "";
		String date = "";
		for (Map<String, String> map : bnfVisitDetails) {
			user_type = map.get("user_type");
			date = map.get("FWBNFDTOO");
			if ("FD".equalsIgnoreCase(user_type)) {
				if (!date.isEmpty()) {
					
					return date;
				}
			}
			
		}
		
		return null;
		
	}
	
	private boolean isUnenrolled(List<Enrollment> enrollments) {
		
		for (Enrollment enrollment : enrollments) {
			//System.err.println("enrollment:" + enrollment.getStatus());
			if (EnrollmentStatus.UNENROLLED.name().equalsIgnoreCase(enrollment.getStatus().name())) {
				return true;
			}
		}
		
		return false;
		
	}
	
	private boolean isActiveOrDefaulted(List<Enrollment> enrollments) {
		boolean completed = false;
		
		for (Enrollment enrollment : enrollments) {
			//System.err.println("enrollment:" + enrollment.getStatus());
			if (EnrollmentStatus.COMPLETED.name().equalsIgnoreCase(enrollment.getStatus().name())
			        || EnrollmentStatus.UNENROLLED.name().equalsIgnoreCase(enrollment.getStatus().name())) {
				completed = true;
				
			} else if (EnrollmentStatus.ACTIVE.name().equalsIgnoreCase(enrollment.getStatus().name())
			        || EnrollmentStatus.DEFAULTED.name().equalsIgnoreCase(enrollment.getStatus().name())) {
				
			}
			
		}
		
		if (completed) {
			return false;
		} else {
			return true;
		}
		
	}
}
