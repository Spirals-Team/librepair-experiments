package com.omer.iyzico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omer.iyzico.model.Ticket;
import com.omer.iyzico.service.TicketService;

@RestController
@RequestMapping("/")
public class TicketController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	TicketService ticketService;

	@RequestMapping(path = "tickets", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createTicket(@RequestBody Ticket ticket) {
		Ticket savedTicket = ticketService.save(ticket);
		return gson.toJson(savedTicket);
	}

	@RequestMapping(path = "tickets/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public String editTicket(@PathVariable("id") Integer id, @RequestBody Ticket ticket) {
		ticket.setId(id);
		Ticket savedTicket = ticketService.save(ticket);
		return gson.toJson(savedTicket);
	}

	@RequestMapping(path = "tickets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String listTickets() {
		List<Ticket> tickets = ticketService.findAll();
		return gson.toJson(tickets);
	}

	@RequestMapping(path = "tickets/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTicket(@PathVariable("id") Integer id) {
		Ticket ticket = ticketService.findById(id);
		return gson.toJson(ticket);
	}

	@RequestMapping(path = "tickets/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteTicket(@PathVariable("id") Integer id) {
		ticketService.deleteById(id);
		return gson.toJson("Ticket Deleted");
	}

}