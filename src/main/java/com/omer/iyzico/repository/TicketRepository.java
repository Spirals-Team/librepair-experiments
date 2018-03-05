package com.omer.iyzico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.omer.iyzico.model.Ticket;

@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	public Ticket findById(Integer id);
	
	public void deleteById(Integer id);
}