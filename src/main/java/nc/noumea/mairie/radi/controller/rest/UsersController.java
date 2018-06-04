package nc.noumea.mairie.radi.controller.rest;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.noumea.mairie.radi.dto.LightGroup;
import nc.noumea.mairie.radi.dto.LightUser;
import nc.noumea.mairie.radi.dto.User;
import nc.noumea.mairie.radi.service.IAdService;

@Controller
@RequestMapping("/users")
public class UsersController {

	private Logger logger = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	private IAdService _adService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<LightUser> searchUsers(HttpServletRequest request) {

		if (request.getParameterMap().size() != 1)
			throw new BadRequestException();

		String filterProperty = request.getParameterMap().keySet().toArray()[0].toString();
		String filterValue = request.getParameter(filterProperty);

		List<LightUser> result = null;
		
		try {
			result = _adService.searchUsers(filterProperty, filterValue);
		} catch (Exception e) {
			logger.error("An error occured while querying AD...", e);
			throw new ServerErrorException();
		}

		if (result == null || result.size() == 0)
			throw new NoContentException();

		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{sAMAccountName}")
	@ResponseBody
	public User getUser(@PathVariable String sAMAccountName) {

		User result = null;

		try {
			result = _adService.getSingleUserBySAMAccountname(sAMAccountName);
		} catch (Exception e) {
			logger.error("An error occured while querying AD...", e);
			throw new ServerErrorException();
		}

		if (result == null)
			throw new ResourceNotFoundException();

		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getListAgents", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public List<LightUser> getListUsers(@RequestBody List<Integer> listIdsAgent) throws ParseException {

		if (null == listIdsAgent || listIdsAgent.isEmpty()) {
			throw new NoContentException();
		}

		List<LightUser> result = null;

		try {
			result = _adService.searchListUsers("employeenumber", listIdsAgent);
		} catch (Exception e) {
			logger.error("An error occured while querying AD...", e);
			throw new ServerErrorException();
		}

		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{sAMAccountName}/groups")
	@ResponseBody
	public List<LightGroup> getUserGroups(@PathVariable String sAMAccountName) {

		List<LightGroup> result = null;

		try {
			result = _adService
					.getSingleUserGroupsBySAMAccountname(sAMAccountName);
		} catch (Exception e) {
			logger.error("An error occured while querying AD...", e);
			throw new ServerErrorException();
		}

		if (result == null)
			throw new ResourceNotFoundException();

		return result;
	}
}
