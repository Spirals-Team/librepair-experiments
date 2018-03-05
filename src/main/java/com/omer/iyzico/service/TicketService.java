package com.omer.iyzico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omer.iyzico.model.Ticket;
import com.omer.iyzico.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	public Ticket findById(Integer id) {
		return ticketRepository.findById(id);
	}

	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	public void deleteById(Integer id) {
		ticketRepository.deleteById(id);
	}
	public Ticket save(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
}
