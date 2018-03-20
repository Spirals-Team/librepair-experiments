package org.opensrp.web.controller;

import static ch.lambdaj.collection.LambdaCollections.with;

import java.util.List;

import org.opensrp.dto.Action;
import org.opensrp.scheduler.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.lambdaj.function.convert.Converter;

@Controller
public class ActionController {
	
	private ActionService actionService;
	
	private static Logger logger = LoggerFactory.getLogger(FormSubmissionController.class.toString());
	
	@Autowired
	public ActionController(ActionService actionService) {
		this.actionService = actionService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/actions")
	@ResponseBody
	public List<Action> getNewActionForANM(@RequestParam("anmIdentifier") String anmIdentifier,
	                                       @RequestParam("timeStamp") Long timeStamp) {
		List<org.opensrp.scheduler.Action> actions = actionService.getNewAlertsForANM(anmIdentifier, timeStamp);
		logger.info("last sync action  provider :" + anmIdentifier + " requested timestamp :" + timeStamp + " data size:"
		        + actions.size());
		return with(actions).convert(new Converter<org.opensrp.scheduler.Action, Action>() {
			
			@Override
			public Action convert(org.opensrp.scheduler.Action action) {
				return ActionConvertor.from(action);
			}
		});
	}
}
