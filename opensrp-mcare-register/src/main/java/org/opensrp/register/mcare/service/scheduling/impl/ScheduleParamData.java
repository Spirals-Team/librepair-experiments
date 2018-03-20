package org.opensrp.register.mcare.service.scheduling.impl;

import org.joda.time.LocalDate;

public class ScheduleParamData {
	
	private String alertStaus;
	
	private LocalDate start;
	
	public ScheduleParamData() {
		
	}
	
	public String getAlertStaus() {
		return alertStaus;
	}
	
	public void setAlertStaus(String alertStaus) {
		this.alertStaus = alertStaus;
	}
	
	public LocalDate getStart() {
		return start;
	}
	
	public void setStart(LocalDate start) {
		this.start = start;
	}
	
}
