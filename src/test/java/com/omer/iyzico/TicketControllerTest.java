package com.omer.iyzico;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omer.iyzico.controller.TicketController;
import com.omer.iyzico.model.Ticket;
import com.omer.iyzico.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TicketService ticketService;

//	@Test
	public void create_ticket_succes() throws Exception {

		Ticket ticket = new Ticket();
		ticket.setPrice(new BigDecimal("250"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ticket.setStartDate(sdf.parse("2017-06-01"));
		ticket.setEndDate(sdf.parse("2018-01-15"));
		ticket.setType("Blind Bird");
		ticket.setCurrency("TL");

		ObjectMapper mapper = new ObjectMapper();
		String ticketJson = mapper.writeValueAsString(ticket);
		when(ticketService.save(ticket)).thenReturn(ticket);

		mockMvc.perform(post("/tickets").contentType(MediaType.APPLICATION_JSON).content(ticketJson))
				.andExpect(status().isOk()).andExpect(content().string("aa"));
	}

	@Test
	public void get_ticket_succes() throws Exception {

		Ticket ticket = new Ticket();
		ticket.setPrice(new BigDecimal("250"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ticket.setStartDate(sdf.parse("2017-06-01"));
		ticket.setEndDate(sdf.parse("2018-01-15"));
		ticket.setType("Blind Bird");
		ticket.setCurrency("TL");

		when(ticketService.findById(1)).thenReturn(ticket);

		mockMvc.perform(get("/tickets/1")).andExpect(status().isOk()).andExpect(jsonPath("$..price").value(250));
	}

	@Test
	public void get_all_ticket_succes() throws Exception {

		Ticket ticket = new Ticket();
		ticket.setPrice(new BigDecimal("250"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ticket.setStartDate(sdf.parse("2017-06-01"));
		ticket.setEndDate(sdf.parse("2018-01-15"));
		ticket.setType("Blind Bird");
		ticket.setCurrency("TL");

		when(ticketService.findAll()).thenReturn(Arrays.asList(ticket));

		mockMvc.perform(get("/tickets"))
		.andExpect(status().isOk()).andExpect(jsonPath("$..price").value(250));
	}

}