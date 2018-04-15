package com.isa.instaticketapi.web.rest.vm.EventResponse;

import com.isa.instaticketapi.domain.Event;

public class EventResponse {
	private Event event;

	public EventResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public EventResponse(Event event) {
		super();
		this.event = event;
	}

}
