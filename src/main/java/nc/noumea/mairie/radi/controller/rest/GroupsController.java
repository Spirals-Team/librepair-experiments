package nc.noumea.mairie.radi.controller.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nc.noumea.mairie.radi.dto.Group;
import nc.noumea.mairie.radi.dto.LightGroup;
import nc.noumea.mairie.radi.dto.LightUser;
import nc.noumea.mairie.radi.service.IAdService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/groups")
public class GroupsController {

	private Logger logger = LoggerFactory.getLogger(GroupsController.class);

	@Autowired
	private IAdService _adService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<LightGroup> searchGroups(HttpServletRequest request) {

		if (request.getParameterMap().size() != 1)
			throw new BadRequestException();

		String filterProperty = request.getParameterMap().keySet().toArray()[0].toString();
		String filterValue = request.getParameter(filterProperty);

		List<LightGroup> result = null;

		try {
			result = _adService.searchGroups(filterProperty, filterValue);
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
	public Group getGroup(@PathVariable String sAMAccountName) {

		Group result = null;

		try {
			result = _adService.getSingleGroupBySAMAccountname(sAMAccountName);
		} catch (Exception e) {
			logger.error("An error occured while querying AD...", e);
			throw new ServerErrorException();
		}

		if (result == null)
			throw new ResourceNotFoundException();

		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{sAMAccountName}/users")
	@ResponseBody
	public List<LightUser> getGroupUsers(@PathVariable String sAMAccountName) {

		List<LightUser> result = null;

		try {
			result = _adService
					.getSingleGroupUsersBySAMAccountname(sAMAccountName);
		} catch (Exception e) {
			logger.error("An error occured while querying AD...", e);
			throw new ServerErrorException();
		}

		if (result == null)
			throw new ResourceNotFoundException();

		return result;
	}
}
