package com.isa.instaticketapi.web.rest.vm.EventResponse;

import java.util.ArrayList;

import com.isa.instaticketapi.domain.Event;

public class EventsResponse {

	private ArrayList<Event> events;

	public EventsResponse() {

	}

	public EventsResponse(ArrayList<Event> events) {
		super();
		this.events = events;
	}

	public ArrayList<Event> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

}
