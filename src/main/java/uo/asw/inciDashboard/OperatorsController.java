package uo.asw.inciDashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uo.asw.dbManagement.model.Operator;
import uo.asw.dbManagement.services.OperatorsService;
import uo.asw.dbManagement.services.RolesService;
import uo.asw.dbManagement.services.SecurityService;
import uo.asw.validators.SignUpFormValidator;

@Controller
public class OperatorsController {
	
	@Autowired
	private OperatorsService operatorsService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
	@Autowired
	private RolesService rolesService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("operator", new Operator());
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String setUser(@Validated Operator operator, BindingResult result, Model model) {
		signUpFormValidator.validate(operator, result);
		if (result.hasErrors()) {
			return "signup";
		}
		
		operator.setRole(rolesService.getRoles()[0]);
		operatorsService.addOperator(operator);
		securityService.autoLogin(operator.getIdentifier(), operator.getPasswordConfirm());
		return "redirect:home";
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	

}
