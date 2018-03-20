package org.opensrp.web.listener;

import org.opensrp.web.controller.AclController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduleListener {
	
	private AclController aclController;
	
	private static Logger logger = LoggerFactory.getLogger(ScheduleListener.class.toString());
	
	public ScheduleListener() {
		
	}
	
	@Autowired
	public ScheduleListener(AclController aclController) {
		this.aclController = aclController;
		
	}
	
	public void refreshSchedule() {
		aclController.pncScheduleRefresh();
		aclController.enncScheduleRefresh();
		aclController.ancScheduleRefresh();
	}
}
